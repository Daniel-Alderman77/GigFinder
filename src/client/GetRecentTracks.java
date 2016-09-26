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

public class GetRecentTracks {	
	
    private static String getOutputAsJson(WebResource service) {
        return service.accept(MediaType.APPLICATION_JSON).get(String.class);
    }
    
    // Suppress type safety warning for JSONArray
    @SuppressWarnings("unchecked")
	public String getArtist(String username) throws ParseException {
    	
    	String REST_URI = "http://ws.audioscrobbler.com/2.0/?method=user.getRecentTracks"
    			+ "&user=" + username + "&api_key=API-KEY&format=json&limit=1";
    	
    	ClientConfig config = new DefaultClientConfig();
        Client client = Client.create(config);
        WebResource getRecentTracks = client.resource(REST_URI);
 
        String jsonResult = getOutputAsJson(getRecentTracks);
        
        System.out.println("Track output as Json: " + jsonResult);  
       
        JSONParser jsonParser = new JSONParser();
        
        JSONObject result = (JSONObject) jsonParser.parse(jsonResult);
        JSONObject response = (JSONObject) result.get("recenttracks");
        JSONArray resultArray = new JSONArray();
        resultArray.add(response);
                
        String trackStr = resultArray.get(0).toString();
        JSONObject trackObj = (JSONObject) jsonParser.parse(trackStr);  
        JSONArray trackArray = (JSONArray) trackObj.get("track");
                        
        String artistStr = trackArray.get(0).toString();
        JSONObject artistObj = (JSONObject) jsonParser.parse(artistStr);
        
        JSONObject artistName = (JSONObject)artistObj.get("artist");
        String artist = (String) artistName.get("#text");
        
        System.out.println("Artist: " + artist);
        
        return artist;
    }
}
