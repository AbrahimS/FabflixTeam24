

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.annotation.Resource;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * Servlet implementation class AndroidSearch
 */
@WebServlet("/AndroidSearch")
public class AndroidSearch extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Resource(name = "jdbc/moviedb")
    private DataSource dataSource;      
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AndroidSearch() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		final ArrayList<Movie> moviesArrayList = new ArrayList<>();
		PrintWriter out = response.getWriter();
		
		try
		{
			Context initCtx = new InitialContext();

            Context envCtx = (Context) initCtx.lookup("java:comp/env");
            if (envCtx == null)
                out.println("envCtx is NULL");

            // Look up our data source
            DataSource ds = (DataSource) envCtx.lookup("jdbc/TestDB");

            // the following commented lines are direct connections without pooling
            //Class.forName("org.gjt.mm.mysql.Driver");
            //Class.forName("com.mysql.jdbc.Driver").newInstance();
            //Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);

            if (ds == null)
                out.println("ds is null.");

            Connection dbCon = ds.getConnection();
            if (dbCon == null)
                out.println("dbcon is null.");
	    	String movieTitle = request.getParameter("movieTitle");
	    	String query = "SELECT DISTINCT m.id, m.title, m.year, m.director FROM movies AS m WHERE m.title LIKE ?;";
	    	PreparedStatement ps = dbCon.prepareStatement(query);
	    	ps.setString(1, "%"+movieTitle+"%");
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
            	String rsMovieId = rs.getString("m.id");
				String rsMovieTitle = rs.getString("m.title");
				String rsMovieYear = rs.getString("m.year");
				String rsMovieDirector = rs.getString("m.director");
				
				String query2 = "SELECT DISTINCT rating from ratings WHERE movieId =?;";
				PreparedStatement ps2 = dbCon.prepareStatement(query2);
				ps2.setString(1, rsMovieId);
	            ResultSet rs2 = ps2.executeQuery();
	            
	            String query3 = "SELECT DISTINCT g.name from genres_in_movies as i, genres as g WHERE i.movieId=? AND i.genreId = g.id;";
    			PreparedStatement ps3 = dbCon.prepareStatement(query3);
        		ps3.setString(1, rsMovieId);
        		ResultSet rs3 = ps3.executeQuery();
				
				String query4 = "SELECT DISTINCT s.name from stars_in_movies as t, stars as s WHERE t.movieId =? AND t.starId = s.id;";
				PreparedStatement ps4 = dbCon.prepareStatement(query4);
		    	ps4.setString(1, rsMovieId);
		    	ResultSet rs4 = ps4.executeQuery();
		    	
		    	String rsGenres = "";
		    	while(rs3.next())
		    	{
		    		String rs3Genres = rs3.getString("g.name");
		    		rsGenres = rsGenres + rs3Genres + ", ";
		    	}
		    	if(rsGenres.length() > 2)
		    	{
		    		rsGenres = rsGenres.substring(0, rsGenres.length()-2);
		    	}
		    	
		    	String rsStars = "";
		    	while(rs4.next())
		    	{
		    		String rs4Stars = rs4.getString("s.name");
		    		rsStars = rsStars + rs4Stars + ", ";
		    	}
		    	if(rsStars.length() > 2)
		    	{
		    		rsStars = rsStars.substring(0, rsStars.length()-2);
		    	}
		    	
	            if(rs2.next())
	            {
	            	String rs2MovieRating = rs2.getString("rating");
					moviesArrayList.add(new Movie(rsMovieId, rsMovieTitle, Integer.parseInt(rsMovieYear), rsMovieDirector, rsGenres, rsStars, rs2MovieRating));	
	            }
	            else
	            {
	            	moviesArrayList.add(new Movie(rsMovieId, rsMovieTitle, Integer.parseInt(rsMovieYear), rsMovieDirector, rsGenres, rsStars, "NULL"));
	            }
	            ps2.close();
	            ps3.close();
	            ps4.close();
	            rs2.close();
	            rs3.close();
	            rs4.close();
					
            }
            ps.close();
            rs.close();
            
            String jsonMoviesArrayList = new Gson().toJson(moviesArrayList);

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(jsonMoviesArrayList);
		}
		catch(Exception e)
		{
			JsonObject responseJsonObject = new JsonObject();
	        responseJsonObject.addProperty("status", "fail");
	        responseJsonObject.addProperty("message", e.getMessage());
	        response.getWriter().write(responseJsonObject.toString());
	        return;
		}
	}

}
