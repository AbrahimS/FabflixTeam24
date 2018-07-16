

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.annotation.Resource;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import com.mysql.jdbc.ResultSetMetaData;

/**
 * Servlet implementation class AddMovie
 */
@WebServlet("/AddMovie")
public class AddMovie extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//@Resource(name = "jdbc/moviedb")
    //private DataSource dataSource;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddMovie() {
        super();
        // TODO Auto-generated constructor stub
    }


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Output stream to STDOUT
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
            
            Context initCtx2 = new InitialContext();

            Context envCtx2 = (Context) initCtx2.lookup("java:comp/env");
            if (envCtx2 == null)
                out.println("envCtx2 is NULL");
            
            DataSource ds2 = (DataSource) envCtx.lookup("jdbc/InsertDB");
            
            if (ds2 == null)
                out.println("ds2 is null.");
            
            Connection dbCon2 = ds2.getConnection();
            if (dbCon2 == null)
                out.println("dbcon2 is null.");
            
            String title = request.getParameter("title");
            String year = request.getParameter("year");
            String director = request.getParameter("director");
            String starname = request.getParameter("starname");
            String genrename = request.getParameter("genrename");

            // for adding movie to database
			String movieInsert = "CALL add_movie(?, ?, ?, ?, ?);";
			PreparedStatement ps = dbCon2.prepareStatement(movieInsert);
			ps.setString(1, title);
			ps.setString(2, year);
			ps.setString(3, director);
			ps.setString(4, starname);
			ps.setString(5, genrename);
			boolean rs = ps.execute();
			
			String returnString = "";
			
			while(rs)
			{
				ResultSet resultSet = ps.getResultSet();
				while(resultSet.next())
				{
					String ret = resultSet.getString("ret");
					returnString = returnString + ret + " ";
				}
				rs = ps.getMoreResults();
			}
			ps.close();
        	
			HttpSession session = request.getSession();
			session.setAttribute("addMovie", returnString);
        	RequestDispatcher dispatcher = request.getRequestDispatcher("/addMovie.jsp");
        	dispatcher.forward(request, response);
        	out.close();
        }
        catch (Exception ex) {

        	String returnString =  "unable to add movie to movies table";
        	HttpSession session = request.getSession();
        	session.setAttribute("addMovie", returnString);
        	System.out.println(ex);
        	RequestDispatcher dispatcher = request.getRequestDispatcher("/addMovie.jsp");
        	dispatcher.forward(request, response);
        }
        out.close();
     
	}

}
