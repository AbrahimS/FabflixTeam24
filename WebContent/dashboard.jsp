<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.sql.*" %>
<%@page import="javax.annotation.Resource" %>
<%@page import="javax.sql.DataSource" %>
<%@page import="java.util.ArrayList" %>
<%@page import="java.util.HashMap" %>
<%@page import="javax.naming.Context" %>
<%@page import="javax.naming.InitialContext" %>
<%@page import="javax.servlet.annotation.WebServlet" %>
<%@page import="javax.servlet.ServletException" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css" integrity="sha384-9gVQ4dYFwwWSjIDZnLEWnxCjeSWFphJiwGPXr1jddIhOegiu1FwO5qRGvFXOdJZ4" crossorigin="anonymous">
	<link rel="stylesheet" type="text/css" href="dashboard.css"/>
	<link rel="shortcut icon" type="image/x-icon" href="imgs/favicon.ico">
	
<title>Fabflix</title>
</head>
<body>
	<%
		if(session.getAttribute("employeeFirstname") == null)
		{
			response.sendRedirect("_dashboard.jsp");
		}
	%>
	<div class="container">
		<div class="row text-center">
			<div class="text-center">
				<img src="imgs/FabflixLogo.png" class="img-responsive col-xl-8 col-lg-8 col-md-8 col-xs-8 text-center">
			</div>
		</div>
	</div>
	
	<nav class="navbar navbar-expand-lg navbar-light fabBlue">
		  <a class="navbar-brand" href="#">
		  	<img src="imgs/favicon.png" width="30" height="30" class="d-inline-block align-top" alt="">
		  	Fabflix</a>
		  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
		    <span class="navbar-toggler-icon"></span>
		  </button>
		  <div class="collapse navbar-collapse" id="navbarNav">
		    <ul class="navbar-nav">
		    <li class="nav-item">
		        <a class="nav-link" href="dashboard.jsp">Dashboard</a>
		      </li>
		    <li class="nav-item">
		        <a class="nav-link" href="addStar.jsp">Add Star</a>
		      </li>
		    <li class="nav-item">
		    	<a class="nav-link" href="addMovie.jsp">Add Movie</a>
		    </li>
		    </ul>
		  </div>
		  
		  <div>
			  <div>
			  	
			  </div>
			  <div>
				<form action="Logout">
				<p>Welcome ${employeeFirstname}   <input class="button-black" type="submit" value="Logout"></p>
				
				</form>
			  </div>
		  </div>
		</nav>
	

		<h1>DashBoard</h1>
				<%
				    // setup the connection and execute the query
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
				    Statement select = dbCon.createStatement();
				    String query = "SHOW TABLES;";

				    ResultSet resultSet = select.executeQuery(query);
				    ResultSetMetaData metadata = resultSet.getMetaData();
				    int cols = 10;
				    for(int j = 1; j <= cols; j++)
				    {	
				    	resultSet.next();
						for(int i = 1; i <= metadata.getColumnCount(); i++)
						{
							String table= resultSet.getString(i);
							out.println("<div class=\"container col-lg-4\">");
							out.println("<div class=\"jumbotron col-xl-12 col-lg-12 col-md-12 col-sm-12 col-xs-12 text-center\">");
		            		out.println("<table class=\"col-lg-12\" align=\"center\" border>");
		            		
		            		// add table header row
		            		out.println("<tr class=\"header col-lg-12\">");
		            		out.println("<td class=\"header col-lg-12\" colspan=\"2\"><h2>"+ table + "</h2></td>");
		            		out.println("</tr>");
		            		
		            		
		            		
		            		Statement select2 = dbCon.createStatement();
		            		String query2 = "describe " + table + ";";
		            		ResultSet resultSet2 = select2.executeQuery(query2);
		            		resultSet2.next();
		            		int next = 1;
		            		while(resultSet2.next())
		            		{
		            			System.out.println(resultSet2);
		            			out.println("<tr>");
	            				out.println("<td>" + resultSet2.getString(i) + "</td>");
	            				out.println("<td>" + resultSet2.getString(i + 1)+ "</td>");
	            				out.println("</tr>");
		            		}
		            		select2.close();
		            		resultSet2.close();
	            			
		            		
		            		out.println("</table>");
		            		out.println("</div>");
		            		out.println("</div>");
		            		
		            		
						}
				    }

				    %>
	
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js" integrity="sha384-cs/chFZiN24E4KMATLdqdvsezGxaGsi4hLGOzlXwp5UZB1LY//20VyM2taTB4QvJ" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js" integrity="sha384-uefMccjFJAIv6A+rW+L4AHf99KvxDjWSu1z9VI8SKNVmz4sk7buKt/6v9KI65qnm" crossorigin="anonymous"></script>

</body>
</html>