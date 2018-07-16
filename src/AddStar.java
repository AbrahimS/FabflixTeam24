

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
 * Servlet implementation class AddStar
 */
@WebServlet("/AddStar")
public class AddStar extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	//@Resource(name = "jdbc/moviedb")
    //private DataSource dataSource;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddStar() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Output stream to STDOUT
        PrintWriter out = response.getWriter();
       

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
            // Declare a new statement
            Statement statement = dbCon.createStatement();

            String name = request.getParameter("name");
            String year = request.getParameter("year");
            
            String starIdFind = "SELECT CONCAT('nm', max(cast(substring(id, 3) AS UNSIGNED)) + 1) AS maxId FROM stars;";
            ResultSet rs = statement.executeQuery(starIdFind);
            rs.next();
            String starId = rs.getString("maxId");
            statement.close();
            
            String starInsert = "INSERT INTO stars (id, name, birthYear) values(?, ?, ?);";
			PreparedStatement ps = dbCon2.prepareStatement(starInsert);
			ps.setString(1, starId);
			ps.setString(2, name);
			ps.setString(3, year);
			ps.executeUpdate();

			String returnString = name  + " was added to star table";
			HttpSession session = request.getSession();
			session.setAttribute("addStar", returnString);
        	RequestDispatcher dispatcher = request.getRequestDispatcher("/addStar.jsp");
        	dispatcher.forward(request, response);
            
        out.close();
        }
        catch (Exception ex) {

        	String returnString =  "unable to add star to star table";
        	HttpSession session = request.getSession();
        	session.setAttribute("addStar", returnString);
        	RequestDispatcher dispatcher = request.getRequestDispatcher("/addStar.jsp");
        	dispatcher.forward(request, response);
        }
        out.close();
     
	}
}


