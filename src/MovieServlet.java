

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.annotation.Resource;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class MovieServlet
 */
@WebServlet("/MovieServlet")
public class MovieServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//@Resource(name = "jdbc/moviedb")
    //private DataSource dataSource;   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MovieServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
            // set response mime type
            response.setContentType("text/html"); 

            // get the printwriter for writing response
            PrintWriter out = response.getWriter();

            out.println("<html>");
            out.println("<head><title>Fabflix</title></head>");
            out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"index.css\"/>");
            out.println("<link href=\"https://fonts.googleapis.com/css?family=Gugi\" rel=\"stylesheet\">");
            out.println("<link href=\"https://fonts.googleapis.com/css?family=Oswald\" rel=\"stylesheet\">");
            
            
            try {
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

            		//Connection dbCon = dataSource.getConnection();

                    // Declare a new statement
                    Statement statement = dbCon.createStatement();
            		String query = "SELECT DISTINCT\n" + 
            				"m.id, m.title, m.year, m.director, r.rating \n" + 
            				"FROM\n" + 
            				"movies AS m,  ratings AS r\n" + 
            				"WHERE\n" + 
            				"m.id = r.movieId\n" + 
            				"ORDER BY r.rating DESC, m.title ASC limit 20;";

            		ResultSet resultSet = statement.executeQuery(query);

            		out.println("<body>");
            		out.println("<h1>TOP 20 MOVIES BY RATING</h1>");
            		out.println("<table align=\"center\" border>");
            		
            		// add table header row
            		out.println("<tr class=\"header\">");
            		out.println("<td class=\"header\">Title</td>");
            		out.println("<td class=\"header\">Year</td>");
            		out.println("<td class=\"header\">Director</td>");
            		out.println("<td class=\"header\">List of Genres</td>");
            		out.println("<td class=\"header\">List of Stars</td>");
            		out.println("<td class=\"header\">Rating</td>");
            		out.println("</tr>");
            		
            		// add a row for every star result
            		while (resultSet.next()) {
            			// get columns from original query
            			
            			String movieId = resultSet.getString("m.id");
            			String movieTitle = resultSet.getString("m.title");
            			String movieYear = resultSet.getString("m.year");
            			String movieDirector = resultSet.getString("m.director");
            			String movieRating = resultSet.getString("rating");
            			
            		
            			//statement and query to select the list of genres
                		Statement statement2 = dbCon.createStatement();
            			
                		String query2 = "SELECT DISTINCT\n" + 
            					"GROUP_CONCAT(g.name SEPARATOR ', ') AS genre\n" + 
            					"from genres_in_movies as i, genres as g\n" + 
            					"WHERE\n" + 
            					"i.movieId = '" + movieId + "' AND i.genreId = g.id;";
            			
            			ResultSet resultSet2 = statement2.executeQuery(query2);
            			
            			resultSet2.next();
            			
            			String genreNames = resultSet2.getString("genre");
            			//close the second statement and result set
            			resultSet2.close();
                		statement2.close();
                		
                		//statement and query to select the list of actors
            			Statement statement3 = dbCon.createStatement();
            			
            			String query3 = "SELECT DISTINCT\n" + 
            					"GROUP_CONCAT(DISTINCT s.name SEPARATOR ', ') AS star\n" + 
            					"from stars_in_movies as t, stars as s\n" + 
            					"WHERE\n" + 
            					"t.movieId = '" + movieId + "' AND t.starId = s.id;";
            			
            			ResultSet resultSet3 = statement3.executeQuery(query3);
            			
            			resultSet3.next();
            			
            			String starNames = resultSet3.getString("star");
            			//close the third statement and result set
            			resultSet3.close();
                		statement3.close();
            			
            			
            			out.println("<tr>");
            			out.println("<td>" + movieTitle + "</td>");
            			out.println("<td>" + movieYear + "</td>");
            			out.println("<td>" + movieDirector + "</td>");
            			out.println("<td>" + genreNames + "</td>");
            			out.println("<td>" + starNames + "</td>");
            			out.println("<td>" + movieRating + "</td>");
            			out.println("</tr>");
            			
            			
            		}
            		
            		out.println("</table>");
            		
            		out.println("</body>");
            		
            		resultSet.close();
            		statement.close();
            		dbCon.close();
            		
            } catch (Exception e) {
            		/*
            		 * After you deploy the WAR file through tomcat manager webpage,
            		 *   there's no console to see the print messages.
            		 * Tomcat append all the print messages to the file: tomcat_directory/logs/catalina.out
            		 * 
            		 * To view the last n lines (for example, 100 lines) of messages you can use:
            		 *   tail -100 catalina.out
            		 * This can help you debug your program after deploying it on AWS.
            		 */
            		e.printStackTrace();
            		
            		out.println("<body>");
            		out.println("<p>");
            		out.println("Exception in doGet: " + e.getMessage());
            		out.println("</p>");
            		out.print("</body>");
            }
            
            out.println("</html>");
            out.close();
            
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}
}
