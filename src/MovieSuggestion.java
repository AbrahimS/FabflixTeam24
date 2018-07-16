
import java.sql.*;
import javax.annotation.Resource;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * Servlet implementation class MovieSuggestion
 */
@WebServlet("/movie-suggestion")
public class MovieSuggestion extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MovieSuggestion() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		PrintWriter out = response.getWriter();
		try {
	
			// setup the response json arrray
			JsonArray jsonArray = new JsonArray();
			
			// get the query string from parameter
			String query = request.getParameter("query");
			
			// return the empty json array if query is null or empty
			if (query == null || query.trim().isEmpty()) {
				response.getWriter().write(jsonArray.toString());
				return;
			}	
			
			// search on marvel heros and DC heros and add the results to JSON Array
			// this example only does a substring match
			// TODO: in project 4, you should do full text search with MySQL to find the matches on movies and stars

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

		    String[] token = query.split(" ");
		    
		    //build the prepared statement
		    String psquery = String.format("SELECT DISTINCT m.id, m.title, m.year, m.director FROM movies AS m WHERE m.title LIKE ?");
	    	for(int i = 1; i < token.length; i++)
	    	{
		    	psquery += " and m.title like ?";
			}
	    	psquery += " LIMIT 10;";
	    	
	    	// add items to prepared statements
	    	PreparedStatement ps = dbCon.prepareStatement(psquery);
    		ps.setString(1, token[0]+"%");
		    if(token.length > 1)
		    {
		    	for(int i = 1; i < token.length; i++)
		    	{
		    		ps.setString(i + 1, "%" + " "+ token[i] + "%");
				}
		    }
		 
		    // execute query
    		ResultSet resultSet = ps.executeQuery();
    	    while (resultSet.next()) 
    	    {
    	    	String movieId = resultSet.getString("m.id");
    			String movieTitle = resultSet.getString("m.title");
    			String movieYear = resultSet.getString("m.year");
    			String movieDirector = resultSet.getString("m.director");
				
				jsonArray.add(generateJsonObject(movieId,  movieTitle, movieYear, movieDirector));
					
    	    }
    	    ps.close();
	    	resultSet.close();
			
	    	response.getWriter().write(jsonArray.toString());
			return;
			
		} catch (Exception e) {
			System.out.println(e);
			response.sendError(500, e.getMessage());
		}
	}
	
	/*
	 * Generate the JSON Object from hero and category to be like this format:
	 * {
	 *   "value": "Iron Man",
	 *   "data": { "category": "marvel", "heroID": 11 }
	 * }
	 * 
	 */
	private static JsonObject generateJsonObject(String id, String title, String year, String director) {
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("value", title);
		
		JsonObject additionalDataJsonObject = new JsonObject();
		additionalDataJsonObject.addProperty("id", id);
		additionalDataJsonObject.addProperty("year", year);
		additionalDataJsonObject.addProperty("director", director);
		
		jsonObject.add("data", additionalDataJsonObject);
		return jsonObject;
	}


}
