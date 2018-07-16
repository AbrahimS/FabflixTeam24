<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.sql.*" %>
<%@page import="javax.annotation.Resource" %>
<%@page import="javax.sql.DataSource" %>
<%@page import="java.util.ArrayList" %>
<%@page import="java.util.HashMap" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css" integrity="sha384-9gVQ4dYFwwWSjIDZnLEWnxCjeSWFphJiwGPXr1jddIhOegiu1FwO5qRGvFXOdJZ4" crossorigin="anonymous">
	<link rel="stylesheet" type="text/css" href="cart.css"/>
	<link rel="shortcut icon" type="image/x-icon" href="imgs/favicon.ico">
	
<title>Fabflix</title>
</head>
<body>
	<%
		if(session.getAttribute("firstname") == null)
		{
			response.sendRedirect("login.jsp");
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
		        <a class="nav-link" href="main.jsp">Main</a>
		      </li>
		    <li class="nav-item">
		        <a class="nav-link" href="search.jsp">Search</a>
		      </li>
		    <li class="nav-item">
		    	<a class="nav-link" href="browse.jsp">Browse</a>
		    </li>
		    <li class="nav-item">
		    	<a class="nav-link" href="cart.jsp">Cart</a>
		    </li>
		    </ul>
		  </div>
		  
		  <div>
			  <div>
			  	
			  </div>
			  <div>
				<form action="Logout">
				<p>Welcome ${firstname}   <input class="button-black" type="submit" value="Logout"></p>
				
				</form>
			  </div>
		  </div>
		</nav>
		
		<h1>CART</h1>
		<div class="container">
			<div class="row text-center">
				
			<%
				out.println("<table align=\"center\" border>");
			
				// add table header row
				out.println("<tr class=\"header\">");
				out.println("<td class=\"header\">Title</td>");
				out.println("<td class=\"header\">Quantity</td>");
				out.println("<td class=\"header\">Update Quantity</td>");
				out.println("<td class=\"header\">Remove Items</td>");
				out.println("</tr>");
			
				if(session.getAttribute("cart")== null)
				{
					
				}
				else
				{
					HashMap<String,Integer> myCart = (HashMap<String,Integer>)session.getAttribute("cart");
					for(HashMap.Entry<String,Integer> entry: myCart.entrySet())
					{
						out.println("<tr>");
						out.println("<form class=\"fabGrey CENTER\" method=\"GET\" action=\"Cart\">");
						out.println("<td>" + entry.getKey() + "</td>");
						out.println("<input type=\"hidden\" name=\"itemName\" value=\""+ entry.getKey() + "\">");
						out.println("<td>");
						out.println("<input class=\"col-xl-4 col-lg-4 col-md-4 col-sm-4 col-xs-4\" type=\"number\" name=\"quantity\" value=\""+ entry.getValue() + "\">");
						out.println("</td>");
						out.println("<td>");
				    	out.println("<input class=\"col-xl-12 col-lg-12 col-md-12 col-sm-12 col-xs-12 button-fabBlue\" type=\"submit\" value=\"Add To Cart\">");
						out.println("</td>");
						out.println("</form>");
						out.println("<form class=\"fabGrey CENTER\" method=\"GET\" action=\"Cart\">");
						out.println("<input type=\"hidden\" name=\"itemName\" value=\""+ entry.getKey() + "\">");
						out.println("<input type=\"hidden\" name=\"quantity\" value=\"0\">");
						out.println("<td>");
						out.println("<input class=\"col-xl-12 col-lg-12 col-md-12 col-sm-12 col-xs-12 btn-danger\" type=\"submit\" value=\"Remove\">");
						out.println("</td>");
						out.println("</form>");
						out.println("</tr>");
					}
					out.println("</table>");
				}
			
			
			%>
			 </div>
		  </div>
		  <div class="container checkoutButton">
				<div class="row text-center col-xl-12 col-lg-12 col-md-12 col-sm-12 col-xs-12">
				
					<% 
						if(session.getAttribute("cart") != null)
						{
							HashMap<String,Integer> myCart = (HashMap<String,Integer>)session.getAttribute("cart");
							if(myCart.size() > 0)
							{
								out.println("<div align=\"right \" class=\"col-xl-11 col-lg-11 col-md-11 col-sm-12 col-xs-12\">");
								out.println("<form method=\"post\" action=\"CustomerInfo\">");
								out.println("<input type=\"hidden\" name=\"itemName\" value=\"hiddenstuff\">");
								out.println("<input class=\"col-xl-2 col-lg-2 col-md-2 col-sm-12 col-xs-12 button-fabBlue\" type=\"submit\" value=\"Checkout\">");
								out.println("</form>");
								out.println("</div>");
							}
								
						}
								
					%>
			 </div>
		  </div>
	



<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js" integrity="sha384-cs/chFZiN24E4KMATLdqdvsezGxaGsi4hLGOzlXwp5UZB1LY//20VyM2taTB4QvJ" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js" integrity="sha384-uefMccjFJAIv6A+rW+L4AHf99KvxDjWSu1z9VI8SKNVmz4sk7buKt/6v9KI65qnm" crossorigin="anonymous"></script>

</body>
</html>