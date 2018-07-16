

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;

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

import org.jasypt.util.password.StrongPasswordEncryptor;

import com.google.gson.JsonObject;

/**
 * Servlet implementation class AndroidLoginServlet
 */
@WebServlet("/AndroidLoginServlet")
public class AndroidLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//@Resource(name = "jdbc/moviedb")
    //private DataSource dataSource;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AndroidLoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
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
	    	String email = request.getParameter("email");
            String password = request.getParameter("password");
            Map<String, String[]> map = request.getParameterMap();
            for (String key: map.keySet()) {
                System.out.println(key);
                System.out.println(map.get(key)[0]);
            }

            // Generate a SQL query
            String query = "SELECT firstName, email, password FROM customers WHERE email = ?;";
            PreparedStatement ps = dbCon.prepareStatement(query);

            ps.setString(1, email);

            // Perform the query
            ResultSet rs = ps.executeQuery();
            
            boolean success = false;
            
            if(rs.next())
            {
            	String rsPassword = rs.getString("password");
	            String rsEmail = rs.getString("email");
	            String firstName = rs. getString("firstName");
	            
	            success = new StrongPasswordEncryptor().checkPassword(password, rsPassword);
	            
	            if(rsEmail.equals(email) && success)
	            {
	            	HttpSession session = request.getSession();
	            	session.setAttribute("firstname", firstName);
	            	response.getWriter().write("success");
	            }
	            
	            else 
	            {
	            	//response.sendRedirect("login.jsp");
	            	response.getWriter().write("User typed in invalid email or password");
	            	//RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
	            	//dispatcher.forward(request, response);
	            }
            }
            else
            {
            	//response.sendRedirect("login.jsp");
            	response.getWriter().write("User typed in invalid email or password");
            	//RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
            	//dispatcher.forward(request, response);
            }
            // Close all structures
            rs.close();
            ps.close();
            dbCon.close();

	    } 
	    catch (Exception e) 
	    {
	    	JsonObject responseJsonObject = new JsonObject();
	        responseJsonObject.addProperty("status", "fail");
	        responseJsonObject.addProperty("message", e.getMessage());
	        response.getWriter().write(responseJsonObject.toString());
	        return;
	    }

	}

}
