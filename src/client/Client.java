package client;

import org.json.simple.parser.ParseException;

public class Client {

	public static void main(String[] args) throws ParseException {
		GetRecentTracks getRecentTracks = new GetRecentTracks();
		
		String username = "LASTFM-USERNAME";
		
		String artist = getRecentTracks.getArtist(username);
				
		GetGigs getGigs = new GetGigs();
		
		getGigs.getGigs(artist);
	}
}
