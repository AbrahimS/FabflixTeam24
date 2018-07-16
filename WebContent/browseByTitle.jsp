<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.sql.*" %>
<%@page import="javax.annotation.Resource" %>
<%@page import="javax.sql.DataSource" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css" integrity="sha384-9gVQ4dYFwwWSjIDZnLEWnxCjeSWFphJiwGPXr1jddIhOegiu1FwO5qRGvFXOdJZ4" crossorigin="anonymous">
	<link rel="stylesheet" type="text/css" href="browseByTitle.css"/>
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
							
								<h1>Browse By Title</h1>
								<img class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-xs-12 text-left"src="imgs/ABC.png" width="180" height="300" class="d-inline-block align-top" alt="">
			    				<br>
			    				<%
			    					String AlaphaNumerical = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
			    					for(int i =0; i < AlaphaNumerical.length(); i++)
			    					{
			    						 out.println(" | ");
			    						 out.println("<a class=\"link-margin\" href=\"movieListBrowse.jsp?Title="+ AlaphaNumerical.charAt(i) +"\">" + AlaphaNumerical.charAt(i) + "</a>");
			    					}
			    					out.println(" | ");
			    				%>
						 </div>
				</div>
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js" integrity="sha384-cs/chFZiN24E4KMATLdqdvsezGxaGsi4hLGOzlXwp5UZB1LY//20VyM2taTB4QvJ" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js" integrity="sha384-uefMccjFJAIv6A+rW+L4AHf99KvxDjWSu1z9VI8SKNVmz4sk7buKt/6v9KI65qnm" crossorigin="anonymous"></script>

</body>
</html>