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
	<link rel="stylesheet" type="text/css" href="singleMovie.css"/>
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
					<div class="jumbotron col-xl-8 col-lg-8 col-md-8 col-sm-10 col-xs-10 text-center">
							<%
								String Title = (String)request.getParameter("Title");
								out.println("<h1>" + Title + "</h1>");
							
							    // setup the connection and execute the queryContext initCtx = new InitialContext();
								Context initCtx = new InitialContext();
            					Context envCtx = (Context) initCtx.lookup("java:comp/env");
            					if (envCtx == null)
            						out.println("envCtx is NULL");

            					// Look up our data source
            					DataSource ds = (DataSource) envCtx.lookup("jdbc/TestDB");

        
        					    if (ds == null)
        					    	out.println("ds is null.");

            					Connection dbCon = ds.getConnection();
            					if (dbCon == null)
            						out.println("dbcon is null.");
							    String query = "SELECT DISTINCT m.id, m.title, m.year, m.director, r.rating FROM movies AS m LEFT JOIN ratings AS r ON m.id = r.movieId WHERE m.title=?;";
							    PreparedStatement ps = dbCon.prepareStatement(query);
								ps.setString(1, Title);
							    ResultSet resultSet = ps.executeQuery();
							
							    while (resultSet.next()) {
									// get columns from original query
									
									String movieId = resultSet.getString("m.id");
									String movieTitle = resultSet.getString("m.title");
									String movieYear = resultSet.getString("m.year");
									String movieDirector = resultSet.getString("m.director");
									String movieRating = resultSet.getString("r.rating");
									
									String query2 = "SELECT DISTINCT g.name from genres_in_movies as i, genres as g WHERE i.movieId=? AND i.genreId = g.id;";
									PreparedStatement ps2 = dbCon.prepareStatement(query2);
						    		ps2.setString(1, movieId);
						    		ResultSet resultSet2 = ps2.executeQuery();
								
									String query3 = "SELECT DISTINCT s.name from stars_in_movies as t, stars as s WHERE t.movieId =? AND t.starId = s.id;";
									PreparedStatement ps3 = dbCon.prepareStatement(query3);
						    		ps3.setString(1, movieId);
									ResultSet resultSet3 = ps3.executeQuery();
									
							    	out.println("<div align=\"left\" class=\"col-xl-12 col-lg-12 col-md-12 col-sm-12 col-xs-12\">");							    	
							    	out.println("<p class=\"card-text cardBody noMargin\">ID Number: " + movieId + "</p>");
							    	out.println("<p class=\"card-text cardBody noMargin\">Directed: " + movieDirector + "</p>");
							    	out.println("<p class=\"card-text cardBody noMargin\">Year: " + movieYear + "</p>");
							    	out.println("<p class=\"card-text cardBody noMargin\">Genres: ");
							    	while(resultSet2.next())
							    	{
							    		String genreNames = resultSet2.getString("g.name");
							    		out.println("<a class=\"linkBlack\" href=\"movieListBrowse.jsp?Genre=" + genreNames + "\">" + genreNames + "</a>");
							    	}
							    	resultSet2.close();
									ps2.close();
									out.println("<p class=\"card-text cardBody noMargin\">Stars: ");
									while(resultSet3.next())
									{
										String starNames = resultSet3.getString("s.name");
										out.println("<a href=\"singleStar.jsp?Star=" + starNames + "\">" + starNames + "</a>");
									}
									resultSet3.close();
									ps3.close();
							    	out.println("<p class=\"card-text cardBody noMargin\">Rating: " + movieRating + "</p>");
							    	out.println("<div class=\"container text-center\">");
									out.println("<div class=\"row text-center CENTER\">");
							    	out.println("<form class=\"fabGrey CENTER\" method=\"GET\" action=\"Cart\">");
							    	out.println("<label class=\"col-xl-6 col-lg-6 col-md-6 col-sm-6 col-xs-6 text-center\"><b>Quantity:</b></label>");
							    	
							    	Integer quantity= 0;
									if(session.getAttribute("cart")== null)
									{
										
									}
									else
									{
										HashMap<String,Integer> myCart = (HashMap<String,Integer>)session.getAttribute("cart");
										
										if(myCart.get(movieTitle) != null)
										{
											quantity = myCart.get(movieTitle);
										}
									}
									
							    	out.println("<input class=\"col-xl-3 col-lg-3 col-md-3 col-sm-3 col-xs-3\" type=\"number\" name=\"quantity\" value=\""+ quantity + "\">");
							   		out.println("</div>");
							   		out.println("</div>");
							    	out.println("<input type=\"hidden\" name=\"itemName\" value=\""+ movieTitle + "\">");
							    	out.println("<div class=\"container text-center fabGrey\">");
							    	out.println("<div class=\"row col-xl-12 col-lg-12 col-md-12 col-sm-12 col-xs-12 CENTER fabGrey\">");
							    	out.println("<input class=\"col-xl-6 col-lg-6 col-md-6 col-sm-6 col-xs-6 button-fabBlue\" type=\"submit\" value=\"Add To Cart\">");
							    	out.println("</div>");
							    	out.println("</div>");
							    	out.println("</form>");
							    	out.println("</div>");
							    }
							%>
							 

					  </div>
					 </div>
			</div>
		

<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js" integrity="sha384-cs/chFZiN24E4KMATLdqdvsezGxaGsi4hLGOzlXwp5UZB1LY//20VyM2taTB4QvJ" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js" integrity="sha384-uefMccjFJAIv6A+rW+L4AHf99KvxDjWSu1z9VI8SKNVmz4sk7buKt/6v9KI65qnm" crossorigin="anonymous"></script>

</body>
</html>

