<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.io.PrintWriter"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css" integrity="sha384-9gVQ4dYFwwWSjIDZnLEWnxCjeSWFphJiwGPXr1jddIhOegiu1FwO5qRGvFXOdJZ4" crossorigin="anonymous">
	<link rel="stylesheet" type="text/css" href="index.css"/>
	<link rel="shortcut icon" type="image/x-icon" href="imgs/favicon.ico">
<title>Fabflix</title>
</head>
<body>
	<div class="container">
		<div class="row text-center">
			<div class="text-center">
				<img src="imgs/FabflixLogo.png" class="img-responsive col-xl-8 col-lg-8 col-md-8 col-xs-8 text-center">
			</div>
		</div>
	</div>
	<hr>
	<div class="container text-center">
		<div class="row text-center"> 
			<div class="jumbotron col-xl-6 col-lg-6 col-md-8 col-sm-10 col-xs-10 text-center">
				<form method="post" action="login">
					<h1>Login</h1>
		    		<label class="col-xl-3 col-lg-3 col-md-3 col-sm-4 col-xs-4 text-left"><b>Email</b></label>
		    		<input class="col-xl-5 col-lg-5 col-md-5 col-sm-6 col-xs-6" type="text" placeholder="Enter Email" name="email" required>
		    		<br>
		    		<label class="col-xl-3 col-lg-3 col-md-3 col-sm-4 col-xs-4  text-left"><b>Password</b></label>
		    		<input class="col-xl-5 col-lg-5 col-md-5 col-sm-6 col-xs-6" type="password" placeholder="Enter Password" name="password" required>
		    		<br>
		    		<div class="container text-center">
		    		<div class="g-recaptcha" align="center" data-sitekey="6Lc9IFkUAAAAAGmABE3m7KnaixjbQSntQXt0WB81"></div>
		    		<br>
		    		</div>
		    		<input type="submit" value="Login">
				</form>
				<div>
					<%if(request.getAttribute("error") != null)
					{
						
						String message = (String)request.getAttribute("error");
						out.println("<p>");
						out.println(message);
						out.println("</p>");
						
					}
					%>
				</div>
			  </div>
			 </div>
	</div>

<script src='https://www.google.com/recaptcha/api.js'></script>	
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js" integrity="sha384-cs/chFZiN24E4KMATLdqdvsezGxaGsi4hLGOzlXwp5UZB1LY//20VyM2taTB4QvJ" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js" integrity="sha384-uefMccjFJAIv6A+rW+L4AHf99KvxDjWSu1z9VI8SKNVmz4sk7buKt/6v9KI65qnm" crossorigin="anonymous"></script>
</body>
</html>