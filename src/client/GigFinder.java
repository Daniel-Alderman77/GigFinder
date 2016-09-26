package client;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.parser.ParseException;

public class GigFinder extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		GetRecentTracks getRecentTracks = new GetRecentTracks();
		GetGigs getGigs = new GetGigs();
		
	    String username = request.getParameter("username");
		
		String artist = null;
		String gig = null;
		
		try {
			artist = getRecentTracks.getArtist(username);
			gig = getGigs.getGigs(artist);
			
		} 
		catch (ParseException e) {
			e.printStackTrace();
		}
		
		// Construct a response in HTML
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		out.println("<html>");
		out.println("<head><title>Gig Finder</title></head>");
		out.println("<body><h1>" + artist + "'s next gig is, " + gig + "</h1></body>");
		out.println("</html>");
	}
}
