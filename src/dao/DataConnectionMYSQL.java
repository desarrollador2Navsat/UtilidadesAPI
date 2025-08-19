package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.codehaus.jackson.annotate.JsonAnyGetter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import logic.API;
import main.ATN;
import model.Jobs;
import model.Ticket;
import model.UnidadConductor;
import model.cliente;
import util.DateTimeUtil;
import util.POSTWS;

public class DataConnectionMYSQL {

	private static Connection con;

//	private static String IP = "10.1.0.10";
//	private static String USER = "webresource2";
//	private static String PASSWORD = "PIrIxCPp6#ss";
//	private static String DATABASE = "dbAsignacionConductor";
//	private static String PORT = "3306";

	private static String IP = "40.121.205.224";
	private static String USER = "u_cymex";
	private static String PASSWORD = "kLLoTAA0PHZCk2wlma0G#";
	private static String DATABASE = "dbAsignacionConductor";
	private static String PORT = "3306";

	public static Connection Connection() {
		con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(
					"jdbc:mysql://" + IP + ":" + PORT + "/" + DATABASE + "?autoReconnect=false", USER, PASSWORD);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return con;
	}

	public static void closeConnection(Connection con) {
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static JSONArray getImagenesPorRango(String fechaInicio, String fechaFin) {
	    JSONArray lista = new JSONArray();

	    Connection con = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;

	    try {
	        con = Connection();
	        String sql = "SELECT id, cliente_id, formulario_id, url, ruta, nombre, " +
	                     "fechaFormulario, fechaFormularioCorta, fechaRegistro " +
	                     "FROM dbFormularioIMG.imagenes " +
	                     "WHERE fechaFormularioCorta >= ? AND fechaFormularioCorta <= ?";
	        ps = con.prepareStatement(sql);
	        ps.setString(1, fechaInicio);
	        ps.setString(2, fechaFin);

	        rs = ps.executeQuery();

	        while (rs.next()) {
	            JSONObject fila = new JSONObject();
	            fila.put("id", rs.getLong("id"));
	            fila.put("cliente_id", rs.getInt("cliente_id"));
	            fila.put("formulario_id", rs.getInt("formulario_id"));
	            fila.put("url", rs.getString("url"));
	            fila.put("ruta", rs.getString("ruta").toUpperCase());
	            fila.put("nombre", rs.getString("nombre"));
	            fila.put("fechaFormulario", rs.getTimestamp("fechaFormulario") != null ? rs.getTimestamp("fechaFormulario").toString() : JSONObject.NULL);
	            fila.put("fechaFormularioCorta", rs.getDate("fechaFormularioCorta") != null ? rs.getDate("fechaFormularioCorta").toString() : JSONObject.NULL);
	            fila.put("fechaRegistro", rs.getTimestamp("fechaRegistro") != null ? rs.getTimestamp("fechaRegistro").toString() : JSONObject.NULL);

	            lista.put(fila);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (ps != null) ps.close();
	            if (con != null) closeConnection(con);
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    return lista;
	}
	
	public static JSONArray getCarpetas(final JSONArray listaImagenes) {
	    final JSONArray out = new JSONArray();
	    if (listaImagenes == null || listaImagenes.length() == 0) return out;

	    // Preserva orden de primera aparición de cada ruta
	    final int n = listaImagenes.length();
	    final Map<String, LinkedHashSet<String>> grupos = new LinkedHashMap<>(Math.max(16, n / 2));

	    for (int i = 0; i < n; i++) {
	        final JSONObject fila = listaImagenes.optJSONObject(i);
	        if (fila == null) continue;

	        final String ruta = trimOrEmpty(fila.optString("ruta", null));
	        final String url  = trimOrEmpty(fila.optString("url", null));
	        if (ruta.isEmpty() || url.isEmpty()) continue;

	        grupos.computeIfAbsent(ruta, k -> new LinkedHashSet<>()).add(url);
	    }

	    // Construcción de salida
	    for (Map.Entry<String, LinkedHashSet<String>> e : grupos.entrySet()) {
	        final JSONObject carpeta = new JSONObject().put("ruta", e.getKey());
	        final JSONArray urls = new JSONArray();
	        for (String u : e.getValue()) urls.put(u);
	        carpeta.put("urls", urls);
	        out.put(carpeta);
	    }
	    return out;
	}

	public static JSONArray getArbolCarpetasConRuta(JSONArray listaImagenes) {
	    JSONObject raiz = new JSONObject().put("carpetas", new JSONArray());

	    for (int i = 0; i < (listaImagenes != null ? listaImagenes.length() : 0); i++) {
	        JSONObject img = listaImagenes.optJSONObject(i);
	        if (img == null) continue;

	        String ruta = trimOrEmpty(img.optString("ruta", null));
	        String url  = trimOrEmpty(img.optString("url", null));
	        if (ruta.isEmpty() || url.isEmpty()) continue;

	        String[] partes = ruta.split("/");
	        JSONObject nodoActual = raiz;
	        StringBuilder acumulada = new StringBuilder();

	        for (int p = 0; p < partes.length; p++) {
	            String nombre = partes[p].trim();
	            if (nombre.isEmpty()) continue;

	            if (acumulada.length() > 0) acumulada.append("/");
	            acumulada.append(nombre);

	            JSONArray carpetas = nodoActual.getJSONArray("carpetas");

	            // buscar/crear hijo
	            JSONObject hijo = null;
	            for (int j = 0; j < carpetas.length(); j++) {
	                JSONObject c = carpetas.getJSONObject(j);
	                if (nombre.equals(c.optString("nombre"))) { hijo = c; break; }
	            }
	            if (hijo == null) {
	                hijo = new JSONObject()
	                        .put("nombre", nombre)
	                        .put("etiqueta", "Carpeta " + nombre)
	                        .put("rutaCompleta", acumulada.toString())
	                        .put("carpetas", new JSONArray());
	                carpetas.put(hijo);
	            }

	            nodoActual = hijo;

	            // último nivel -> agregar URL
	            if (p == partes.length - 1) {
	                JSONArray urls = nodoActual.optJSONArray("urls");
	                if (urls == null) {
	                    urls = new JSONArray();
	                    nodoActual.put("urls", urls);
	                }
	                urls.put(url);
	            }
	        }
	    }

	    // Ordena carpetas y limpia 'urls' vacías
	    ordenarYLimpiarRecursivo(raiz);

	    // Propaga conteos a cada nodo
	    propagarConteos(raiz); // setea totalFotos y hasFotos en todos los nodos

	    return raiz.getJSONArray("carpetas");
	}

	private static void ordenarYLimpiarRecursivo(JSONObject nodo) {
	    if (nodo.has("urls")) {
	        JSONArray u = nodo.optJSONArray("urls");
	        if (u == null || u.length() == 0) nodo.remove("urls");
	    }
	    JSONArray carpetas = nodo.optJSONArray("carpetas");
	    if (carpetas == null) return;

	    java.util.List<JSONObject> tmp = new java.util.ArrayList<>();
	    for (int i = 0; i < carpetas.length(); i++) tmp.add(carpetas.getJSONObject(i));
	    tmp.sort((a, b) -> a.optString("nombre","").compareToIgnoreCase(b.optString("nombre","")));

	    JSONArray ordenadas = new JSONArray();
	    for (JSONObject c : tmp) {
	        ordenarYLimpiarRecursivo(c);
	        ordenadas.put(c);
	    }
	    nodo.put("carpetas", ordenadas);
	}

	private static int propagarConteos(JSONObject nodo) {
	    int total = 0;

	    JSONArray urls = nodo.optJSONArray("urls");
	    if (urls != null) total += urls.length();

	    JSONArray carpetas = nodo.optJSONArray("carpetas");
	    if (carpetas != null) {
	        for (int i = 0; i < carpetas.length(); i++) {
	            total += propagarConteos(carpetas.getJSONObject(i));
	        }
	    }

	    if (nodo.has("carpetas") || nodo.has("urls")) {
	        nodo.put("totalFotos", total);
	        nodo.put("hasFotos", total > 0);
	    }
	    return total;
	}

	private static String trimOrEmpty(String s) { return s == null ? "" : s.trim(); }






}
