package logic;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import dao.DaoBlobStorage;



@Path("/formulariosIMG")
public class FormulariosIMG {

	@Path("/zipFile")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response ZipFile(String Data) {
	    try {
	        JSONObject zipUrl = DaoBlobStorage.generarZipPlantillasYSubir();

	        JSONObject responseJson = new JSONObject();
	        responseJson.put("status", "success");
	        responseJson.put("zipUrl", zipUrl);
	        responseJson.put("message", "ZIP generado y subido con éxito.");

	        return Response.status(Response.Status.OK).entity(responseJson.toString()).build();

	    } catch (Exception e) {
	        e.printStackTrace();
	        JSONObject errorResponse = new JSONObject();
	        errorResponse.put("message", "Error al generar el archivo ZIP.");
	        errorResponse.put("status", "error");
	        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorResponse.toString()).build();
	    }
	}



}
