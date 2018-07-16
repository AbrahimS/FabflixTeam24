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
	<link rel="stylesheet" type="text/css" href="addStar.css"/>
	<link rel="shortcut icon" type="image/x-icon" href="imgs/favicon.ico">
	
<title>Fabflix</title>
</head>
<body>
	<%
		if(session.getAttribute("employeeFirstname") == null)
		{
			response.sendRedirect("_dashboard.jsp");
		}
	 
		if(session.getAttribute("addStar") != null)
		{
			String message = (String)session.getAttribute("addStar");
			out.println("<script type=\"text/javascript\">");
			out.println("alert('" + message + "');");
			out.println("</script>");
			session.removeAttribute("addStar");
		
		}
		if(session.getAttribute("addStarInMovie") != null)
		{
			String message = (String)session.getAttribute("addStarInMovie");
			out.println("<script type=\"text/javascript\">");
			out.println("alert('" + message + "');");
			out.println("</script>");
			session.removeAttribute("addStarInMovie");
		
		}
		if(session.getAttribute("addGenreInMovie") != null)
		{
			String message = (String)session.getAttribute("addGenreInMovie");
			out.println("<script type=\"text/javascript\">");
			out.println("alert('" + message + "');");
			out.println("</script>");
			session.removeAttribute("addGenreInMovie");
		
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
	<div class="container text-center">
		<div class="row text-center"> 
			<div class="jumbotron col-xl-6 col-lg-6 col-md-8 col-sm-10 col-xs-10 text-center">
				<form method="post" action="AddStar">
					<h1>Add Star</h1>
		    		<label class="col-xl-3 col-lg-3 col-md-3 col-sm-4 col-xs-4  text-left"><b>Full Name:</b></label>
		    		<input class="col-xl-5 col-lg-5 col-md-5 col-sm-6 col-xs-6" type="text" placeholder="Enter Fullname" name="name" required>
		    		<br>
		    		<label class="col-xl-3 col-lg-3 col-md-3 col-sm-4 col-xs-4  text-left"><b>Birth Date:</b></label>
		    		<input class="col-xl-5 col-lg-5 col-md-5 col-sm-6 col-xs-6" type="number" placeholder="Enter Birth Year" name="year">
		    		<br>
		    		<input type="submit" value="Add Star">
				</form>
			  </div>
			 </div>
	</div>
	
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js" integrity="sha384-cs/chFZiN24E4KMATLdqdvsezGxaGsi4hLGOzlXwp5UZB1LY//20VyM2taTB4QvJ" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js" integrity="sha384-uefMccjFJAIv6A+rW+L4AHf99KvxDjWSu1z9VI8SKNVmz4sk7buKt/6v9KI65qnm" crossorigin="anonymous"></script>

</body>
</html>