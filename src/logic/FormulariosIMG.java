package logic;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import dao.DaoBlobStorage;
import dao.DataConnectionMYSQL;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@javax.ws.rs.Path("/formulariosIMG")

public class FormulariosIMG {

	@javax.ws.rs.Path("/zipFile")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	public Response zipFile(String Data) {
	    ZipOutputStream zos = null;
	    java.nio.file.Path zipPath = null;

	    try {
	        // 1) Parámetros
	        JSONObject in = new JSONObject(Data == null ? "{}" : Data);
	        String fechaInicio = in.optString("fechaInicio", "").trim();
	        String fechaFin    = in.optString("fechaFin", "").trim();
	        if (fechaInicio.isEmpty() || fechaFin.isEmpty()) {
	            return bad("Parámetros requeridos: fechaInicio y fechaFin.");
	        }

	        // 2) Datos (rutas + urls)
	        JSONArray imagenes = DataConnectionMYSQL.getImagenesPorRango(fechaInicio, fechaFin);
	        JSONArray arbol    = DataConnectionMYSQL.getArbolCarpetasConRuta(imagenes);

	        // 3) Ruta local fija: Response/form.zip (dentro del proyecto)
	        zipPath = java.nio.file.Paths.get(System.getProperty("user.dir"), "Response", "form.zip");
	        java.nio.file.Files.createDirectories(zipPath.getParent());

	        // 4) Crear/reescribir el ZIP (stream directo, sin temp por imagen)
	        zos = new ZipOutputStream(java.nio.file.Files.newOutputStream(
	                zipPath, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING));

	        Contador c = new Contador();
	        for (int i = 0; i < arbol.length(); i++) {
	            streamNodoToZip(arbol.getJSONObject(i), "", zos, c);
	        }
	        zos.flush();
	        zos.close(); zos = null;

	        // 5) Subir a Azure (contenedor 'respuestas' con nombre fijo 'form.zip')
	        JSONObject subida = DaoBlobStorage.subirZipARespuestas(zipPath, "form.zip");

	        // 6) Respuesta OK
	        JSONObject out = new JSONObject()
	                .put("status", "success")
	                .put("zipPath", zipPath.toString())
	                .put("fileName", subida.optString("fileName"))
	                .put("fileUrl",  subida.optString("fileUrl"))
	                .put("totalFotos", c.fotos)
	                .put("totalCarpetas", c.carpetas);

	        return Response.ok(out.toString()).build();

	    } catch (Exception e) {
	        e.printStackTrace();
	        // Si el ZIP quedó a medio crear, intenta borrarlo
	        try {
	            if (zipPath != null) java.nio.file.Files.deleteIfExists(zipPath);
	        } catch (IOException ignore) {}
	        return serverError("Error al generar y subir ZIP de imágenes.");
	    } finally {
	        if (zos != null) {
	            try { zos.close(); } catch (IOException ignore) {}
	        }
	    }
	}


    /* ===== Helpers ===== */

    /** Recorre el árbol y agrega entradas al ZIP respetando la jerarquía. */
    private static void streamNodoToZip(JSONObject nodo, String parentPath,
                                        ZipOutputStream zos, Contador c) throws IOException {
        String nombre = nodo.optString("nombre", "").trim();
        if (nombre.isEmpty()) return;

        // Ruta dentro del ZIP (usa / siempre)
        String carpetaPath = parentPath.isEmpty() ? sanitizeSeg(nombre)
                                                  : parentPath + "/" + sanitizeSeg(nombre);
        c.carpetas++;

        // 1) Archivos del nodo (si hay)
        JSONArray urls = nodo.optJSONArray("urls");
        if (urls != null && urls.length() > 0) {
            for (int i = 0; i < urls.length(); i++) {
                String url = urls.optString(i, null);
                if (url == null || url.isEmpty()) continue;
                String fileName = uniqueNameFromUrl(url);
                String entryPath = carpetaPath + "/" + fileName;

                ZipEntry entry = new ZipEntry(entryPath);
                zos.putNextEntry(entry);
                streamDownload(url, zos);  // escribe directo al ZIP
                zos.closeEntry();
                c.fotos++;
            }
        }

        // 2) Subcarpetas
        JSONArray hijos = nodo.optJSONArray("carpetas");
        if (hijos != null) {
            // Entrada explícita de carpeta (opcional pero mejora compatibilidad)
            if ((urls == null || urls.length() == 0)) {
                String dirEntry = carpetaPath.endsWith("/") ? carpetaPath : carpetaPath + "/";
                ZipEntry dir = new ZipEntry(dirEntry);
                zos.putNextEntry(dir);
                zos.closeEntry();
            }
            for (int j = 0; j < hijos.length(); j++) {
                streamNodoToZip(hijos.getJSONObject(j), carpetaPath, zos, c);
            }
        }
    }

    /** Descarga y copia al ZipOutputStream (sin tocar disco). */
    private static void streamDownload(String urlStr, OutputStream target) throws IOException {
        HttpURLConnection con = null;
        try {
            URL url = new URL(urlStr);
            con = (HttpURLConnection) url.openConnection();
            con.setConnectTimeout(10000);
            con.setReadTimeout(30000);
            con.setInstanceFollowRedirects(true);

            try (InputStream in = new BufferedInputStream(con.getInputStream())) {
                byte[] buf = new byte[8192];
                int r;
                while ((r = in.read(buf)) != -1) target.write(buf, 0, r);
            }
        } finally {
            if (con != null) con.disconnect();
        }
    }

    /** Obtiene nombre de archivo desde URL y limpia caracteres inválidos. */
    private static String uniqueNameFromUrl(String urlStr) {
        try {
            URL u = new URL(urlStr);
            String path = u.getPath();
            int idx = path.lastIndexOf('/');
            String file = (idx >= 0 ? path.substring(idx + 1) : path);
            if (file == null || file.isEmpty()) return "archivo";
            return file.replaceAll("[\\\\/:*?\"<>|]", "_");
        } catch (Exception e) {
            return "archivo";
        }
    }

    /** Limpia caracteres inseguros en el nombre de carpeta/segmento. */
    private static String sanitizeSeg(String seg) {
        return seg.replaceAll("[\\\\/:*?\"<>|]", "_");
    }

    private static Response bad(String msg) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(new JSONObject().put("status","error").put("message", msg).toString())
                .build();
    }
    private static Response serverError(String msg) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(new JSONObject().put("status","error").put("message", msg).toString())
                .build();
    }

    /** Contadores simples para métricas. */
    static class Contador { int fotos = 0; int carpetas = 0; }
}
