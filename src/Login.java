

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



import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")
public class Login extends HttpServlet {
	
	//@Resource(name = "jdbc/moviedb")
    //private DataSource dataSource;


	/**
	 * @throws ServletException 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException 
	{
        response.setContentType("text/html");    // Response mime type
        
        // Output stream to STDOUT
        PrintWriter out = response.getWriter();
        String gRecaptchaResponse = request.getParameter("g-recaptcha-response");
        System.out.println("gRecaptchaReaponse" + gRecaptchaResponse);
        
        try {
            RecaptchaVerifyUtils.verify(gRecaptchaResponse);
        } catch (Exception e) {
        	request.setAttribute("error", "gRecaptchaResponse is null or empty");
        	RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
        	dispatcher.forward(request, response);
           
        }


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

            // Declare a new statement
            


            // Retrieve parameter "name" from the http request, which refers to the value of <input name="name"> in index.html
            String email = request.getParameter("email");
            String password = request.getParameter("password");

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
	            
	            //success = new StrongPasswordEncryptor().checkPassword(password, rsPassword);
	            
	            if(rsEmail.equals(email) && rsPassword.equals(password))
	            {
	            	HttpSession session = request.getSession();
	            	session.setAttribute("firstname", firstName);
	            	response.sendRedirect("main.jsp");
	            }
	            
	            else 
	            {
	            	//response.sendRedirect("login.jsp");
	            	request.setAttribute("error", "User typed in invalid email or password");
	            	RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
	            	dispatcher.forward(request, response);
	            }
            }
            else
            {
            	//response.sendRedirect("login.jsp");
            	request.setAttribute("error", "User typed in invalid email or password");
            	RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
            	dispatcher.forward(request, response);
            }
            
           


            // Close all structures
            rs.close();
            ps.close();
            dbCon.close();

        } catch (Exception ex) {

            // Output Error Massage to html
            out.println(String.format("<html><head><title>MovieDB: Error</title></head>\n<body><p>SQL error in doPost: %s</p></body></html>", ex.getMessage()));
            return;
        }
        out.close();
    }
}


