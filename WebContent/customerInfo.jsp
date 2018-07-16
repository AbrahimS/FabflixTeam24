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
	<link rel="stylesheet" type="text/css" href="customerInfo.css"/>
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
		
		<div class="container text-center">
		<div class="row text-center"> 
			<div class="jumbotron col-xl-6 col-lg-6 col-md-8 col-sm-10 col-xs-10 text-center">
				<form method="post" action="checkoutValidation">
					<h1>Customer Information</h1>
		    		<label class="col-xl-3 col-lg-3 col-md-3 col-sm-4 col-xs-4 text-left"><b>First Name</b></label>
		    		<input class="col-xl-5 col-lg-5 col-md-5 col-sm-6 col-xs-6" type="text" placeholder="Enter First Name" name="firstName" required>
		    		<br>
		    		<label class="col-xl-3 col-lg-3 col-md-3 col-sm-4 col-xs-4  text-left"><b>Last Name</b></label>
		    		<input class="col-xl-5 col-lg-5 col-md-5 col-sm-6 col-xs-6" type="text" placeholder="Enter Last Name" name="lastName" required>
		    		<br>
		    		<label class="col-xl-3 col-lg-3 col-md-3 col-sm-4 col-xs-4 text-left"><b>Credit Card</b></label>
		    		<input class="col-xl-5 col-lg-5 col-md-5 col-sm-6 col-xs-6" type="text" placeholder="Enter Credit Card" name="creditCard" required>
		    		<br>
		    		<label class="col-xl-3 col-lg-3 col-md-3 col-sm-4 col-xs-4  text-left"><b>Expiration</b></label>
		    		<input class="col-xl-5 col-lg-5 col-md-5 col-sm-6 col-xs-6" type="date" placeholder="Expiration Date" name="expireDate" required>
		    		<br>
		    		<input class="fabBlue" type="submit" value="Checkout">
				</form>
				<div>
					<%if(request.getAttribute("error") != null)
					{
						
						String message = (String)request.getAttribute("error");
						out.println("<p class=\"red-text\">");
						out.println(message);
						out.println("</p>");
						
					}
					%>
				</div>
			  </div>
			 </div>
	</div>

</body>
</html>