

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

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

/**
 * Servlet implementation class checkoutValidation
 */
@WebServlet("/checkoutValidation")
public class checkoutValidation extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//@Resource(name = "jdbc/moviedb")
    //private DataSource dataSource;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public checkoutValidation() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		response.setContentType("text/html");    // Response mime type
        
        // Output stream to STDOUT
        PrintWriter out = response.getWriter();


        try {
        	HttpSession session = request.getSession();
    		// create database connection

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
            
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        	LocalDate localDate = LocalDate.now();
        	String dateFormatted = dtf.format(localDate);
        	
            // Retrieve parameter "name" from the http request, which refers to the value of <input name="name"> in index.html
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String creditCard = request.getParameter("creditCard");
            String expireDate = request.getParameter("expireDate");
            String sessionName = (String)session.getAttribute("firstname");
           
            // Generate a SQL query
            String query = "SELECT id, firstName, lastName, expiration FROM creditcards WHERE id=?;";
            PreparedStatement ps = dbCon.prepareStatement(query);
            ps.setString(1, creditCard);
            ResultSet rs = ps.executeQuery();
            
            String custQuery = "SELECT id FROM customers WHERE firstName=?;";
            PreparedStatement ps2 = dbCon.prepareStatement(custQuery);
            ps2.setString(1, sessionName);
            ResultSet rs2 = ps2.executeQuery();
            
            if(rs.next())
            {
            	String rsFirstName = rs.getString("firstName");
            
	            String rsLastName = rs.getString("lastName");
	            
	            String rsCreditCard = rs.getString("id");
	            
	            String rsExpireDate = rs.getString("expiration");
	            
	            rs2.next();
	            String rsCustId = rs2.getString("id");
	            
	            if(rsFirstName.equals(firstName) && rsLastName.equals(lastName) && rsCreditCard.equals(creditCard) && rsExpireDate.equals(expireDate))
	            {
	            	
	            	HashMap<String,Integer> myCart = (HashMap<String,Integer>)session.getAttribute("cart");
					for(HashMap.Entry<String,Integer> entry: myCart.entrySet())
					{
						
						String itemName = entry.getKey();
						
						String itemQuery = "SELECT id FROM movies WHERE title=?;";
						PreparedStatement ps3 = dbCon.prepareStatement(itemQuery);
						ps3.setString(1, itemName);
						ResultSet rs3 = ps3.executeQuery();
						
						rs3.next();
						String rsItemId = rs3.getString("id");
						ps3.close();
						rs3.close();
						
						Statement statement4 = dbCon2.createStatement();
						String saleInsert = String.format("INSERT INTO sales (customerId, movieId, saleDate) values('%s', '%s', '%s');", rsCustId, rsItemId, dateFormatted);
						statement4.executeUpdate(saleInsert);
						statement4.close();
					}
					session.setAttribute("custId", rsCustId);
					session.setAttribute("date", dateFormatted);
	            	response.sendRedirect("confirmation.jsp");
	            }
	            else 
	            {
	            	//response.sendRedirect("login.jsp");
	            	request.setAttribute("error", "User typed in invalid information");
	            	RequestDispatcher dispatcher = request.getRequestDispatcher("/customerInfo.jsp");
	            	dispatcher.forward(request, response);
	            }
            }
            else
            {
            	//response.sendRedirect("login.jsp");
            	request.setAttribute("error", "User typed in invalid information");
            	RequestDispatcher dispatcher = request.getRequestDispatcher("/customerInfo.jsp");
            	dispatcher.forward(request, response);
            }
            
            // Close all structures
            rs.close();
            //rs2.close();
            
            ps.close();
            //statement2.close();
            dbCon.close();
            dbCon2.close();

        } catch (Exception ex) {

            // Output Error Massage to html
            out.println(String.format("<html><head><title>MovieDB: Error</title></head>\n<body><p>SQL error in doPost: %s</p></body></html>", ex.getMessage()));
            return;
        }
        out.close();
    }

}


