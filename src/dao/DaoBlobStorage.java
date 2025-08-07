package dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.Comparator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.json.JSONArray;
import org.json.JSONObject;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;
import com.microsoft.azure.storage.blob.ListBlobItem;

public class DaoBlobStorage {
    private static final String storageConnectionString = "DefaultEndpointsProtocol=http;"
            + "AccountName=boletastorage;"
            + "AccountKey=P2Qw8tgP0O8jPEHvv6QKgJHxLKsT48befHfBzBWO6HUlkqOSZLP+8k0RgPzwj87GTd4cNpZzh+vZzIuIwz3UHA==";

    public static JSONObject subirArchivo(JSONObject obj) throws Exception {
        String base64EncodedFile = obj.getString("base64File");
        String fileName = obj.getString("blobFileName");

        boolean contieneWord = obj.has("base64Word") && obj.has("blobFileNameWord");
        String base64EncodedWord = contieneWord ? obj.getString("base64Word") : null;
        String wordFileName = contieneWord ? obj.getString("blobFileNameWord") : null;

        CloudStorageAccount storageAccount = CloudStorageAccount.parse(storageConnectionString);
        CloudBlobClient blobClient = storageAccount.createCloudBlobClient();
        CloudBlobContainer container = blobClient.getContainerReference("plantillascontratos");

        JSONObject result = new JSONObject();

        // Subir JSON
        byte[] decodedJsonBytes = Base64.getDecoder().decode(base64EncodedFile);
        File tempJson = File.createTempFile("tempJson", ".tmp");
        try (FileOutputStream fos = new FileOutputStream(tempJson)) {
            fos.write(decodedJsonBytes);
        }
        CloudBlockBlob jsonBlob = container.getBlockBlobReference(fileName);
        jsonBlob.upload(new FileInputStream(tempJson), tempJson.length());
        tempJson.delete();

        result.put("fileName", fileName);
        result.put("filePath", "https://boletastorage.blob.core.windows.net/plantillascontratos/" + fileName);

        // Subir Word si existe
        if (contieneWord) {
            byte[] decodedWordBytes = Base64.getDecoder().decode(base64EncodedWord);
            File tempWord = File.createTempFile("tempWord", ".docx");
            try (FileOutputStream fosWord = new FileOutputStream(tempWord)) {
                fosWord.write(decodedWordBytes);
            }
            CloudBlockBlob wordBlob = container.getBlockBlobReference(wordFileName);
            wordBlob.upload(new FileInputStream(tempWord), tempWord.length());
            tempWord.delete();

            result.put("wordFileName", wordFileName);
            result.put("wordFilePath", "https://boletastorage.blob.core.windows.net/plantillascontratos/" + wordFileName);
        }

        return result;
    }
    public static JSONObject subirContrato(JSONObject obj) throws Exception {
        String base64EncodedFile = obj.getString("base64File");
        String fileName = obj.getString("blobFileName");

        boolean contieneWord = obj.has("base64Word") && obj.has("blobFileNameWord");
        String base64EncodedWord = contieneWord ? obj.getString("base64Word") : null;
        String wordFileName = contieneWord ? obj.getString("blobFileNameWord") : null;

        CloudStorageAccount storageAccount = CloudStorageAccount.parse(storageConnectionString);
        CloudBlobClient blobClient = storageAccount.createCloudBlobClient();
        CloudBlobContainer container = blobClient.getContainerReference("contratos");

        JSONObject result = new JSONObject();

        // Subir JSON
        byte[] decodedJsonBytes = Base64.getDecoder().decode(base64EncodedFile);
        File tempJson = File.createTempFile("tempJson", ".tmp");
        try (FileOutputStream fos = new FileOutputStream(tempJson)) {
            fos.write(decodedJsonBytes);
        }
        CloudBlockBlob jsonBlob = container.getBlockBlobReference(fileName);
        jsonBlob.upload(new FileInputStream(tempJson), tempJson.length());
        tempJson.delete();

        result.put("fileName", fileName);
        result.put("filePath", "https://boletastorage.blob.core.windows.net/contratos/" + fileName);

        // Subir Word si existe
        if (contieneWord) {
            byte[] decodedWordBytes = Base64.getDecoder().decode(base64EncodedWord);
            File tempWord = File.createTempFile("tempWord", ".docx");
            try (FileOutputStream fosWord = new FileOutputStream(tempWord)) {
                fosWord.write(decodedWordBytes);
            }
            CloudBlockBlob wordBlob = container.getBlockBlobReference(wordFileName);
            wordBlob.upload(new FileInputStream(tempWord), tempWord.length());
            tempWord.delete();

            result.put("wordFileName", wordFileName);
            result.put("wordFilePath", "https://boletastorage.blob.core.windows.net/contratos/" + wordFileName);
        }

        return result;
    }
    public static JSONObject subirActualizarContrato(JSONObject obj) throws Exception {
        String base64EncodedFile = obj.getString("base64File");
        String fileName = obj.getString("blobFileName");

        boolean contieneWord = obj.has("base64Word") && obj.has("blobFileNameWord");
        String base64EncodedWord = contieneWord ? obj.getString("base64Word") : null;
        String wordFileName = contieneWord ? obj.getString("blobFileNameWord") : null;

        // Recuperar los campos de urlBlobJson y urlBlobWord
        String urlBlobJson = obj.has("urlBlobJson") ? obj.getString("urlBlobJson") : null;
        String urlBlobWord = obj.has("urlBlobWord") ? obj.getString("urlBlobWord") : null;

        CloudStorageAccount storageAccount = CloudStorageAccount.parse(storageConnectionString);
        CloudBlobClient blobClient = storageAccount.createCloudBlobClient();
        CloudBlobContainer container = blobClient.getContainerReference("contratos");

        JSONObject result = new JSONObject();

        // Eliminar archivos antiguos del Blob Storage si existen
        if (urlBlobJson != null && !urlBlobJson.isEmpty()) {
            String oldJsonFileName = urlBlobJson.substring(urlBlobJson.lastIndexOf("/") + 1);
            CloudBlockBlob oldJsonBlob = container.getBlockBlobReference(oldJsonFileName);
            if (oldJsonBlob.exists()) {
                oldJsonBlob.deleteIfExists();
            }
        }

        if (urlBlobWord != null && !urlBlobWord.isEmpty()) {
            String oldWordFileName = urlBlobWord.substring(urlBlobWord.lastIndexOf("/") + 1);
            CloudBlockBlob oldWordBlob = container.getBlockBlobReference(oldWordFileName);
            if (oldWordBlob.exists()) {
                oldWordBlob.deleteIfExists();
            }
        }

        // Subir JSON
        byte[] decodedJsonBytes = Base64.getDecoder().decode(base64EncodedFile);
        File tempJson = File.createTempFile("tempJson", ".tmp");
        try (FileOutputStream fos = new FileOutputStream(tempJson)) {
            fos.write(decodedJsonBytes);
        }
        CloudBlockBlob jsonBlob = container.getBlockBlobReference(fileName);
        jsonBlob.upload(new FileInputStream(tempJson), tempJson.length());
        tempJson.delete();

        result.put("fileName", fileName);
        result.put("filePath", "https://boletastorage.blob.core.windows.net/contratos/" + fileName);

        // Subir Word si existe
        if (contieneWord) {
            byte[] decodedWordBytes = Base64.getDecoder().decode(base64EncodedWord);
            File tempWord = File.createTempFile("tempWord", ".docx");
            try (FileOutputStream fosWord = new FileOutputStream(tempWord)) {
                fosWord.write(decodedWordBytes);
            }
            CloudBlockBlob wordBlob = container.getBlockBlobReference(wordFileName);
            wordBlob.upload(new FileInputStream(tempWord), tempWord.length());
            tempWord.delete();

            result.put("wordFileName", wordFileName);
            result.put("wordFilePath", "https://boletastorage.blob.core.windows.net/contratos/" + wordFileName);
        }

        return result;
    }

    public static boolean eliminarArchivo(String fileName) throws Exception {
        boolean result = false;
        CloudStorageAccount storageAccount = CloudStorageAccount.parse(storageConnectionString);
        CloudBlobClient blobClient = storageAccount.createCloudBlobClient();
        CloudBlobContainer container = blobClient.getContainerReference("plantillascontratos");

        CloudBlockBlob blob = container.getBlockBlobReference(fileName);
        if (blob.exists()) {
            try {
                result = blob.deleteIfExists();
                if (result) {
                    System.out.println("Archivo eliminado: " + fileName);
                }
            } catch (StorageException e) {
                System.err.println("Error al eliminar el archivo: " + e.getMessage());
                throw e;
            }
        } else {
            System.err.println("El archivo no existe en el Blob Storage: " + fileName);
        }

        return result;
    }

    public static JSONArray listarArchivos() throws Exception {
        JSONArray archivosListados = new JSONArray();

        CloudStorageAccount storageAccount = CloudStorageAccount.parse(storageConnectionString);
        CloudBlobClient blobClient = storageAccount.createCloudBlobClient();
        CloudBlobContainer container = blobClient.getContainerReference("plantillascontratos");

        for (ListBlobItem blobItem : container.listBlobs()) {
            if (blobItem instanceof CloudBlockBlob) {
                CloudBlockBlob blob = (CloudBlockBlob) blobItem;
                String fileName = blob.getName();

                JSONObject archivo = new JSONObject();
                archivo.put("fileName", fileName);
                archivo.put("fileUrl", blob.getUri().toString());

                archivosListados.put(archivo);
            }
        }

        return archivosListados;
    }

    public static String updateBlob(String base64EncodedFile, String fileName) {
        String resultMessage = "Archivo cargado exitosamente.";
        JSONObject result = new JSONObject();

        try {
            // Conectar a la cuenta de almacenamiento de Azure
            CloudStorageAccount storageAccount = CloudStorageAccount.parse(storageConnectionString);
            CloudBlobClient blobClient = storageAccount.createCloudBlobClient();
            CloudBlobContainer container = blobClient.getContainerReference("plantillascontratos");

            // Decodificar el archivo base64
            byte[] decodedBytes = Base64.getDecoder().decode(base64EncodedFile);
            File tempFile = File.createTempFile("tempFile", ".tmp");
            try (FileOutputStream fos = new FileOutputStream(tempFile)) {
                fos.write(decodedBytes);
            }

            // Eliminar el archivo existente si ya está en el contenedor
            CloudBlockBlob blob = container.getBlockBlobReference(fileName);
            if (blob.exists()) {
                blob.delete();  // Eliminar el archivo anterior
            }

            // Subir el nuevo archivo
            blob.upload(new FileInputStream(tempFile), tempFile.length());

            // Devolver el nombre del archivo y la ruta
            result.put("fileName", fileName);
            result.put("filePath", "https://boletastorage.blob.core.windows.net/plantillascontratos/" + fileName);
            tempFile.delete();
        } catch (Exception e) {
            resultMessage = "Error al cargar el archivo: " + e.toString();
            result.put("error", resultMessage);
        }

        return result.toString();
    }

    // 🔧 Método adicional para actualizar .json y .docx
    public static String updateBlobMultiple(JSONObject obj) {
        JSONObject result = new JSONObject();
        try {
            if (obj.has("base64File") && obj.has("blobFileName")) {
                result.put("jsonUpdate", new JSONObject(updateBlob(obj.getString("base64File"), obj.getString("blobFileName"))));
            }
            if (obj.has("base64Word") && obj.has("blobFileNameWord")) {
                result.put("wordUpdate", new JSONObject(updateBlob(obj.getString("base64Word"), obj.getString("blobFileNameWord"))));
            }
        } catch (Exception e) {
            result.put("error", "Error al actualizar archivos múltiples: " + e.toString());
        }
        return result.toString();
    }
    
    
    public static JSONObject generarZipPlantillasYSubir() throws Exception {
        CloudStorageAccount storageAccount = CloudStorageAccount.parse(storageConnectionString);
        CloudBlobClient blobClient = storageAccount.createCloudBlobClient();
        CloudBlobContainer sourceContainer = blobClient.getContainerReference("plantillascontratos");
        CloudBlobContainer zipContainer = blobClient.getContainerReference("respuestas");
        zipContainer.createIfNotExists();

        // Crear carpeta temporal y subcarpeta "avenida"
        Path tempDir = Files.createTempDirectory("archivos_plantillas");
        Path avenidaDir = tempDir.resolve("avenida");
        Files.createDirectories(avenidaDir);

        // Descargar archivos dentro de "avenida"
        for (ListBlobItem blobItem : sourceContainer.listBlobs()) {
            if (blobItem instanceof CloudBlockBlob) {
                CloudBlockBlob blob = (CloudBlockBlob) blobItem;
                String fileName = blob.getName();
                Path destination = avenidaDir.resolve(fileName);

                // Crear subdirectorios si existen carpetas
                Files.createDirectories(destination.getParent());
                blob.downloadToFile(destination.toString());
            }
        }

        // Crear archivo ZIP
        Path zipFilePath = Files.createTempFile("plantillas_zip", ".zip");
        try (FileOutputStream fos = new FileOutputStream(zipFilePath.toFile());
             ZipOutputStream zos = new ZipOutputStream(fos)) {

            Files.walk(avenidaDir).filter(path -> !Files.isDirectory(path)).forEach(path -> {
                try {
                    // Incluir "avenida/" como raíz en el ZIP
                    String entryName = tempDir.relativize(path).toString().replace("\\", "/");
                    ZipEntry zipEntry = new ZipEntry(entryName);
                    zos.putNextEntry(zipEntry);
                    Files.copy(path, zos);
                    zos.closeEntry();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }

        // Subir ZIP al contenedor "respuestas"
        String zipBlobName = "plantillas_" + System.currentTimeMillis() + ".zip";
        CloudBlockBlob zipBlob = zipContainer.getBlockBlobReference(zipBlobName);
        zipBlob.upload(new FileInputStream(zipFilePath.toFile()), zipFilePath.toFile().length());

        // Limpiar archivos temporales
        Files.walk(tempDir).sorted(Comparator.reverseOrder()).forEach(path -> path.toFile().delete());
        zipFilePath.toFile().delete();

        // Armar respuesta
        JSONObject result = new JSONObject();
        result.put("fileName", zipBlobName);
        result.put("filePath", "https://boletastorage.blob.core.windows.net/respuestas/" + zipBlobName);

        return result;
    }


}
