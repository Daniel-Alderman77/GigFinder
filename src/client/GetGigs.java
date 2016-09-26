package client;

import javax.ws.rs.core.MediaType;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class GetGigs {
	
    private static String getOutputAsJson(WebResource service) {
        return service.accept(MediaType.APPLICATION_JSON).get(String.class);
    }
    
    // Suppress type safety warning for JSONArray
    @SuppressWarnings("unchecked")
	public
	String getGigs(String artist) throws ParseException {
    	// Remove any spaces between words in the artist's name
        artist = artist.replaceAll("\\s","");
            	
    	String REST_URI = "http://api.songkick.com/api/3.0/events.json?"
    			+ "apikey=j8ZDtVtjzZDWXqyT&artist_name=" + artist + "&page=1&per_page=1";
    	
    	ClientConfig config = new DefaultClientConfig();
        Client client = Client.create(config);
        WebResource getRecentTracks = client.resource(REST_URI);
        
        String jsonResult = getOutputAsJson(getRecentTracks);
        
        System.out.println("Gig ouput as Json: " + jsonResult);  
       
        JSONParser jsonParser = new JSONParser();
        
        JSONObject result = (JSONObject) jsonParser.parse(jsonResult);
        JSONObject response = (JSONObject) result.get("resultsPage");
        JSONArray responseArray = new JSONArray();
        responseArray.add(response);
                
        String responseStr = responseArray.get(0).toString();
                
        JSONObject responseObj = (JSONObject) jsonParser.parse(responseStr);  
        JSONObject resultObj = (JSONObject) responseObj.get("results");
                
        JSONArray eventArray = (JSONArray) resultObj.get("event");
        
        String eventDescription = "";
                
    	try {
    		String eventStr = eventArray.get(0).toString();
            
            // Get Event Description
            JSONObject eventObj = (JSONObject) jsonParser.parse(eventStr);    
            eventDescription = (String) eventObj.get("displayName");    	
            } 
    	catch (NullPointerException ex) { 
        	eventDescription = "No events could be found";
        }
          
        System.out.println("Event Descrition: " + eventDescription); 
        
        return eventDescription;
    }
}
