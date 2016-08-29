package edu.ap.jaxrs;

import java.io.*;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.time.Year;
import java.util.Date;

import javax.ws.rs.*;
import javax.json.*;

@Path("/examens")
public class ExamenResource {
	
	static final String FILE = "/Users/Achraf/Desktop/Examen.json";

	@GET
	@Produces({"text/html"})
	public String getExamensHTML() {
		String htmlString = "<html><body>";
		try {
			JsonReader reader = Json.createReader(new StringReader(getExamensJSON()));
			JsonObject rootObj = reader.readObject();
			JsonArray array = rootObj.getJsonArray("examens");
			
			
			for(int i = 0 ; i < array.size(); i++) {
				JsonObject obj = array.getJsonObject(i);
				htmlString += "<b>Naam : " + obj.getString("naam") + "</b><br>";
				htmlString += "Examen : " + obj.getString("examen") + "<br>";
				htmlString += "Reden : " + obj.getString("reden") + "<br>";
				htmlString += "Datum : " + obj.getString("datum") + "<br>";
				htmlString += "<br><br>";
			}
		}
		catch(Exception ex) {
			htmlString = "<html><body>" + ex.getMessage();
		}
		
		return htmlString + "</body></html>";
	}
	
	@GET
	@Produces({"application/json"})
	public String getExamensJSON() {
		String jsonString = "";
		try {
			InputStream fis = new FileInputStream(FILE);
	        JsonReader reader = Json.createReader(fis);
	        JsonObject obj = reader.readObject();
	        reader.close();
	        fis.close();
	        
	        jsonString = obj.toString();
		} 
		catch (Exception ex) {
			jsonString = ex.getMessage();
		}
		
		return jsonString;
	}
	
	@POST
	@Consumes({"application/json"})
	public String addExamen(String examenJSON) {
		String returnCode = "";
		try {
			// read existing exams
			InputStream fis = new FileInputStream(FILE);
	        JsonReader jsonReader1 = Json.createReader(fis);
	        JsonObject jsonObject = jsonReader1.readObject();
	        jsonReader1.close();
	        fis.close();
	        
	        JsonReader jsonReader2 = Json.createReader(new StringReader(examenJSON));
	        JsonObject newObject = jsonReader2.readObject();
	        jsonReader2.close();
	        
	        JsonArray array = jsonObject.getJsonArray("examens");
	        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
	        
	        for(int i = 0; i < array.size(); i++){
	        	// add existing exams
	        	JsonObject obj = array.getJsonObject(i);
	        	arrBuilder.add(obj);
	        }
	        
	        //Date date = format.parse(new Date());
	        //var today = new Date();
	        //today = today.getDate() + '-' + today.getMonth()+'-'+today.getYear();
	        //SimpleDateFormat format = new SimpleDateFormat("DD-mm-YYYY");
	        
	        //newObject.put("datum", format.parse(today));
	        
	        
	        // add new product
	        arrBuilder.add(newObject);
	        
	        
	        // now wrap it in a JSON object
	        JsonArray newArray = arrBuilder.build();
	        JsonObjectBuilder builder = Json.createObjectBuilder();
	        builder.add("examens", newArray);
	        JsonObject newJSON = builder.build();

	        // write to file
	        OutputStream os = new FileOutputStream(FILE);
	        JsonWriter writer = Json.createWriter(os);
	        writer.writeObject(newJSON);
	        writer.close();
		} 
		catch (Exception ex) {
			returnCode = ex.getMessage();
		}
		
		return returnCode;
	}
}
