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
	<link rel="stylesheet" type="text/css" href="movieListSearch.css"/>
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

<!-- everything below this line is below navbar -->
<div class="container text-center">
	<div class="row text-center"> 
		<h1 class="text-center">MOVIE LIST</h1>
		
	</div>
</div>
<div class="container col-xl-12 col-lg-12 col-md-12 col-sm-12 col-xs-12">
		<div class="row col-xl-12 col-lg-12 col-md-12 col-sm-12 col-xs-12">
			<div class="container  littleMargin col-xl-3 col-lg-3 col-md-3 col-sm-3 col-xs-3 childFill">
					<div class="row col-xl-12 col-lg-12 col-md-12 col-sm-12 col-xs-12"> 
						<div class="container fabGrey noMargin col-xl-12 col-lg-12 col-md-12 col-sm-12 col-xs-12">
							<form class="noPadding col-xl-12 col-lg-12 col-md-12 col-sm-12 col-xs-12 method="get" action="updateSearchPage">
								<h3 class="noPadding col-xl-12 col-lg-12 col-md-12 col-sm-12 col-xs-12">Advanced Options</h3>
								<label class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-xs-12 text-left"><b><u>Sort By:</u></b></label>
								<div class="row col-xl-12 col-lg-12 col-md-12 col-sm-12 col-xs-12">
									<div class="col-xl-1 col-lg-1 col-md-1 col-sm-1 col-xs-1"></div>
									<input type="radio" name="sortBy" value="m.title" checked><label>Title</label></input>
								</div>	
								<div class="row col-xl-12 col-lg-12 col-md-12 col-sm-12 col-xs-12">
									<div class="col-xl-1 col-lg-1 col-md-1 col-sm-1 col-xs-1"></div>
									<input type="radio" name="sortBy" value="ratings.rating"><label>Rating</label></input>
								</div>
								<label class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-xs-12 text-left"><b><u>Sort Results in:</u></b></label>
								<div class="row col-xl-12 col-lg-12 col-md-12 col-sm-12 col-xs-12">
									<div class="col-xl-1 col-lg-1 col-md-1 col-sm-1 col-xs-1"></div>
									<input type="radio" name="ascend" value="ASC" checked><label>Ascending Order</label></input>
								</div>
								<div class="row col-xl-12 col-lg-12 col-md-12 col-sm-12 col-xs-12">
									<div class="col-xl-1 col-lg-1 col-md-1 col-sm-1 col-xs-1"></div>
									<input type="radio" name="ascend" value="DESC"><label>Descend Order</label></input>
								</div>
								<label class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-xs-12 text-left"><b><u>Movies Per Page:</u></b></label>
								<div class="row col-xl-12 col-lg-12 col-md-12 col-sm-12 col-xs-12">
									<div class="col-xl-1 col-lg-1 col-md-1 col-sm-1 col-xs-1"></div>
									<input type="radio" name="perPage" value="10" checked><label>10</label></input>
								</div>
								<div class="row col-xl-12 col-lg-12 col-md-12 col-sm-12 col-xs-12">
									<div class="col-xl-1 col-lg-1 col-md-1 col-sm-1 col-xs-1"></div>
									<input type="radio" name="perPage" value="25"><label>25</label></input>
								</div>
								<div class="row col-xl-12 col-lg-12 col-md-12 col-sm-12 col-xs-12">
									<div class="col-xl-1 col-lg-1 col-md-1 col-sm-1 col-xs-1"></div>
									<input type="radio" name="perPage" value="50"><label>50</label></input>
								</div>
								<div class="row col-xl-12 col-lg-12 col-md-12 col-sm-12 col-xs-12"> 
									<div class="col-xl-1 col-lg-1 col-md-1 col-sm-1 col-xs-1"></div>
									<input type="radio" name="perPage" value="100"><label>100</label></input>
								</div>
								<%
									StringBuffer requestURL = request.getRequestURL();
									if(request.getQueryString()!= null)
									{
										requestURL.append("?").append(request.getQueryString());
										if(requestURL.indexOf("&pageNum") != -1)
										{
											int badIndex = requestURL.indexOf("&pageNum");
											requestURL.replace(badIndex, requestURL.length(), "");
										}
										if(requestURL.indexOf("&sortBy") != -1)
										{
											int badIndex = requestURL.indexOf("&sortBy");
											requestURL.replace(badIndex, requestURL.length(), "");
										}
									}
									
									
									out.println("<input type=\"hidden\" name=\"url\" value=\""+ requestURL +"\">");
								
								%>
								
					    		<div class="row col-xl-12 col-lg-12 col-md-12 col-sm-12 col-xs-12 CENTER"> 
					    			<div class="col-xl-1 col-lg-1 col-md-1 col-sm-1 col-xs-1"></div>
					    			<input class="col-xl-4 col-lg-4 col-md-4 col-sm-4 col-xs-4 button-fabBlue" type="submit" value="Update">
					    		</div>
							</form>
			  </div>
			 </div>
			
	</div>
<div class = "container cardContainer col-lg-9">
	<div class="row">
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
    String title = request.getParameter("title");
    String director = request.getParameter("director");
    String year = request.getParameter("year");
    String starName = request.getParameter("starName");
    String ascend = request.getParameter("ascend");
    String sortBy = request.getParameter("sortBy");
    String perPage = request.getParameter("perPage");
    String pageNum = request.getParameter("pageNum"); 
    
    int offSet;

    if(perPage == null || perPage.isEmpty())
    {
    	perPage = "10";
    }
    
    if(pageNum == null || pageNum.isEmpty())
    {
    	pageNum = "1";
    }
    
    offSet = Integer.parseInt(perPage) * Integer.parseInt(pageNum) - Integer.parseInt(perPage);
    String strOffSet = Integer.toString(offSet);
    
    if(ascend  != null && !ascend.isEmpty() && sortBy != null && !sortBy.isEmpty())
    {
    	if(!starName.isEmpty() && starName!=null)
    	{
    		String query = String.format("SELECT DISTINCT m.id, m.title, m.year, m.director, ratings.rating, stars_in_movies.movieId FROM movies AS m LEFT JOIN ratings ON m.id = ratings.movieId LEFT JOIN stars_in_movies ON m.id = stars_in_movies.movieId LEFT JOIN stars ON stars_in_movies.starId = stars.id WHERE stars.name LIKE ? AND m.title LIKE ? AND m.year LIKE ? AND m.director LIKE ? ORDER BY %s %s LIMIT ? OFFSET ?;", sortBy, ascend);
    		PreparedStatement ps = dbCon.prepareStatement(query);
    		ps.setString(1, "%"+starName+"%");
    		ps.setString(2, "%"+title+"%");
    		ps.setString(3, "%"+year+"%");
    		ps.setString(4, "%"+director+"%");
    		ps.setInt(5, Integer.parseInt(perPage));
    		ps.setInt(6, Integer.parseInt(strOffSet));
    		ResultSet resultSet = ps.executeQuery();
    		
    	    while (resultSet.next()) 
    	    {
    	    	String movieId = resultSet.getString("m.id");
    			String movieTitle = resultSet.getString("m.title");
    			String movieYear = resultSet.getString("m.year");
    			String movieDirector = resultSet.getString("m.director");
    			String movieRating = resultSet.getString("ratings.rating");
    				
    			String query2 = "SELECT DISTINCT g.name from genres_in_movies as i, genres as g WHERE i.movieId=? AND i.genreId = g.id;";
    			PreparedStatement ps2 = dbCon.prepareStatement(query2);
    			ps2.setString(1, movieId);
    			ResultSet resultSet2 = ps2.executeQuery();    			
    				
    			String query4 = "SELECT DISTINCT s.name from stars_in_movies as t, stars as s WHERE t.movieId =? AND t.starId = s.id;";
    			PreparedStatement ps4 = dbCon.prepareStatement(query4);
    			ps4.setString(1, movieId);
    			ResultSet resultSet4 = ps4.executeQuery();
    				
    	    	out.println("<div class=\"col-lg-4 col-sm-12\">");
    	    	out.println("<div class=\"card noMargin\" style=\"width: 18rem;\">");
    	    	out.println("<div class=\"card-body noMargin\">");
    	    	out.println("<h2 class=\"card-title header noMargin\"><a class=\"linkBlack\" href=\"singleMovie.jsp?Title=" + movieTitle + "\">" + movieTitle + "</a></h2>"); // add link
    	    	out.println("<p class=\"card-text cardBody noMargin\">Directed: " + movieDirector + "</p>");
    	    	out.println("<p class=\"card-text cardBody noMargin\">Year: " + movieYear + "</p>");
    	    	out.println("<p class=\"card-text cardBody noMargin\">Genres: ");
    	    	while(resultSet2.next())
    	    	{
    	    		String genreNames = resultSet2.getString("g.name");
    	    		out.println("<a class=\"linkBlack\" href=\"movieListBrowse.jsp?Genre=" + genreNames + "\">" + genreNames + "</a>");
    	    	}
    	    	ps2.close();
    	    	resultSet2.close();
    	    	out.println("<p class=\"card-text cardBody noMargin\">Stars: ");
    	    	while(resultSet4.next())
    	    	{
    	    		String starName2= resultSet4.getString("s.name");
    	    		out.println("<a a class=\"linkBlack\" href=\"singleStar.jsp?Star=" + starName2 + "\">" + starName2 + "</a>");    
    	    	}
    	    	ps4.close();
    	    	resultSet4.close();
    	    	out.println("</p>");
    	    	out.println("<p class=\"card-text cardBody noMargin\">Rating: " + movieRating + "</p>");
    	    	out.println("<div class=\"container text-center\">");
    			out.println("<div class=\"row text-center\">");
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
    	    	out.println("<input class=\"col-xl-12 col-lg-12 col-md-12 col-sm-12 col-xs-12 button-fabBlue\" type=\"submit\" value=\"Add To Cart\">");
    	    	out.println("</div>");
    	    	out.println("</div>");
    	    	
    	    	out.println("</form>");
    	 
    	    	out.println("</div>");  
    	    	out.println("</div>");
    	    	out.println("</div>");
    		  }
    		
    	}
    	else
    	{
    		String query = String.format("SELECT DISTINCT m.id, m.title, m.year, m.director, ratings.rating FROM movies AS m LEFT JOIN ratings ON m.id = ratings.movieId WHERE m.title LIKE ? AND m.year LIKE ? AND m.director LIKE ? ORDER BY %s %s LIMIT ? OFFSET ?;", sortBy, ascend);
    		PreparedStatement ps = dbCon.prepareStatement(query);
    		ps.setString(1, "%"+title+"%");
    		ps.setString(2, "%"+year+"%");
    		ps.setString(3, "%"+director+"%");
    		ps.setInt(4, Integer.parseInt(perPage));
    		ps.setInt(5, Integer.parseInt(strOffSet));
    		ResultSet resultSet = ps.executeQuery();
    	    while (resultSet.next()) 
    	    {
    	    	String movieId = resultSet.getString("m.id");
    			String movieTitle = resultSet.getString("m.title");
    			String movieYear = resultSet.getString("m.year");
    			String movieDirector = resultSet.getString("m.director");
    			String movieRating = resultSet.getString("ratings.rating");
    				
    			String query2 = "SELECT DISTINCT g.name from genres_in_movies as i, genres as g WHERE i.movieId=? AND i.genreId = g.id;";
    			PreparedStatement ps2 = dbCon.prepareStatement(query2);
        		ps2.setString(1, movieId);
        		ResultSet resultSet2 = ps2.executeQuery();
    				
    			String query4 = "SELECT DISTINCT s.name from stars_in_movies as t, stars as s WHERE t.movieId =? AND t.starId = s.id;";
    			PreparedStatement ps4 = dbCon.prepareStatement(query4);
    			ps4.setString(1, movieId);
    			ResultSet resultSet4 = ps4.executeQuery();
    				
    	    	out.println("<div class=\"col-lg-4 col-sm-12\">");
    	    	out.println("<div class=\"card noMargin\" style=\"width: 18rem;\">");
    	    	out.println("<div class=\"card-body noMargin\">");
    	    	out.println("<h2 class=\"card-title header noMargin\"><a class=\"linkBlack\" href=\"singleMovie.jsp?Title=" + movieTitle + "\">" + movieTitle + "</a></h2>"); // add link
    	    	out.println("<p class=\"card-text cardBody noMargin\">Directed: " + movieDirector + "</p>");
    	    	out.println("<p class=\"card-text cardBody noMargin\">Year: " + movieYear + "</p>");
    	    	out.println("<p class=\"card-text cardBody noMargin\">Genres: ");
    	    	while(resultSet2.next())
    	    	{
    	    		String genreNames = resultSet2.getString("g.name");
    	    		out.println("<a class=\"linkBlack\" href=\"movieListBrowse.jsp?Genre=" + genreNames + "\">" + genreNames + "</a>");
    	    	}
    	    	ps2.close();
    	    	resultSet2.close();
    	    	out.println("<p class=\"card-text cardBody noMargin\">Stars: ");
    	    	while(resultSet4.next())
    	    	{
    	    		String starName2= resultSet4.getString("s.name");
    	    		out.println("<a a class=\"linkBlack\" href=\"singleStar.jsp?Star=" + starName2 + "\">" + starName2 + "</a>");    
    	    	}
    	    	ps4.close();
    	    	resultSet4.close();
    	    	out.println("</p>");
    	    	out.println("<p class=\"card-text cardBody noMargin\">Rating: " + movieRating + "</p>");
    	    	out.println("<div class=\"container text-center\">");
    			out.println("<div class=\"row text-center\">");
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
    	    	out.println("<input class=\"col-xl-12 col-lg-12 col-md-12 col-sm-12 col-xs-12 button-fabBlue\" type=\"submit\" value=\"Add To Cart\">");
    	    	out.println("</div>");
    	    	out.println("</div>");
    	    	out.println("</form>");
    	    	out.println("</div>");  
    	    	out.println("</div>");
    	    	out.println("</div>");
    		  }
    	}
    }
    else
    {
    	if(!starName.isEmpty() && starName != null)
    	{
    		String query = "SELECT DISTINCT m.id, m.title, m.year, m.director, ratings.rating, stars_in_movies.movieId FROM movies AS m LEFT JOIN ratings ON m.id = ratings.movieId LEFT JOIN stars_in_movies ON m.id = stars_in_movies.movieId LEFT JOIN stars ON stars_in_movies.starId = stars.id WHERE stars.name LIKE ? AND m.title LIKE ? AND m.year LIKE ? AND m.director LIKE ? LIMIT ? OFFSET ?;";
    		PreparedStatement ps = dbCon.prepareStatement(query);
    		ps.setString(1, "%"+starName+"%");
    		ps.setString(2, "%"+title+"%");
    		ps.setString(3, "%"+year+"%");
    		ps.setString(4, "%"+director+"%");
    		ps.setInt(5, Integer.parseInt(perPage));
    		ps.setInt(6, Integer.parseInt(strOffSet));
    		ResultSet resultSet = ps.executeQuery();
		    while (resultSet.next()) 
		    {
				String movieId = resultSet.getString("m.id");
				String movieTitle = resultSet.getString("m.title");
				String movieYear = resultSet.getString("m.year");
				String movieDirector = resultSet.getString("m.director");
			
				String query2 = "SELECT DISTINCT g.name from genres_in_movies as i, genres as g WHERE i.movieId=? AND i.genreId = g.id;";
    			PreparedStatement ps2 = dbCon.prepareStatement(query2);
        		ps2.setString(1, movieId);
        		ResultSet resultSet2 = ps2.executeQuery();
				
				String query4 = "SELECT DISTINCT s.name from stars_in_movies as t, stars as s WHERE t.movieId =? AND t.starId = s.id;";
				PreparedStatement ps4 = dbCon.prepareStatement(query4);
		    	ps4.setString(1, movieId);
		    	ResultSet resultSet4 = ps4.executeQuery();
				
				String query5 = "SELECT DISTINCT rating from ratings WHERE movieId =?;";
				PreparedStatement ps5 = dbCon.prepareStatement(query5);
    			ps5.setString(1, movieId);
				ResultSet resultSet5 = ps5.executeQuery();
				
		    	out.println("<div class=\"col-lg-4 col-sm-12\">");
		    	out.println("<div class=\"card noMargin\" style=\"width: 18rem;\">");
		    	out.println("<div class=\"card-body noMargin\">");
		    	out.println("<h2 class=\"card-title header noMargin\"><a class=\"linkBlack\" href=\"singleMovie.jsp?Title=" + movieTitle + "\">" + movieTitle + "</a></h2>"); // add link
		    	out.println("<p class=\"card-text cardBody noMargin\">Directed: " + movieDirector + "</p>");
		    	out.println("<p class=\"card-text cardBody noMargin\">Year: " + movieYear + "</p>");
		    	out.println("<p class=\"card-text cardBody noMargin\">Genres: ");
		    	while(resultSet2.next())
		    	{
		    		String genreNames = resultSet2.getString("g.name");
		    		out.println("<a class=\"linkBlack\" href=\"movieListBrowse.jsp?Genre=" + genreNames + "\">" + genreNames + "</a>");
		    	}
		    	ps2.close();
		    	resultSet2.close();
		    	out.println("<p class=\"card-text cardBody noMargin\">Stars: ");
		    	while(resultSet4.next())
		    	{
		    		String starName2= resultSet4.getString("s.name");
		    		out.println("<a a class=\"linkBlack\" href=\"singleStar.jsp?Star=" + starName2 + "\">" + starName2 + "</a>");    
		    	}
		    	ps4.close();
		    	resultSet4.close();
		    	out.println("</p>");
		    	
		    	if(resultSet5.next())
		    	{
		    		String rs5Rating = resultSet5.getString("rating");
		    		out.println("<p class=\"card-text cardBody noMargin\">Rating: " + rs5Rating + "</p>");
		    	}
		    	else
		    	{
		    		out.println("<p class=\"card-text cardBody noMargin\">Rating: N/A </p>");
		    	}
		    	ps5.close();
		    	resultSet5.close();
		    	out.println("<div class=\"container text-center\">");
				out.println("<div class=\"row text-center\">");
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
		    	out.println("<input class=\"col-xl-12 col-lg-12 col-md-12 col-sm-12 col-xs-12 button-fabBlue\" type=\"submit\" value=\"Add To Cart\">");
		    	out.println("</div>");
		    	out.println("</div>");
		    	
		    	out.println("</form>");
		 
		    	out.println("</div>");  
		    	out.println("</div>");
		    	out.println("</div>");
		}
    }
    else
    {
    	String query = "SELECT DISTINCT m.id, m.title, m.year, m.director FROM movies AS m WHERE m.title LIKE ? AND m.year LIKE ? AND m.director LIKE ? LIMIT ? OFFSET ?;";
    	PreparedStatement ps = dbCon.prepareStatement(query);
		ps.setString(1, "%"+title+"%");
		ps.setString(2, "%"+year+"%");
		ps.setString(3, "%"+director+"%");
		ps.setInt(4, Integer.parseInt(perPage));
		ps.setInt(5, Integer.parseInt(strOffSet));
		ResultSet resultSet = ps.executeQuery();
    	while (resultSet.next()) 
    	{
    		String movieId = resultSet.getString("m.id");
			String movieTitle = resultSet.getString("m.title");
			String movieYear = resultSet.getString("m.year");
			String movieDirector = resultSet.getString("m.director");
	
			String query2 = "SELECT DISTINCT g.name from genres_in_movies as i, genres as g WHERE i.movieId=? AND i.genreId = g.id;";
			PreparedStatement ps2 = dbCon.prepareStatement(query2);
    		ps2.setString(1, movieId);
    		ResultSet resultSet2 = ps2.executeQuery();
		
			String query4 = "SELECT DISTINCT s.name from stars_in_movies as t, stars as s WHERE t.movieId =? AND t.starId = s.id;";
			PreparedStatement ps4 = dbCon.prepareStatement(query4);
	    	ps4.setString(1, movieId);
	    	ResultSet resultSet4 = ps4.executeQuery();
		
			String query5 = "SELECT DISTINCT rating from ratings WHERE movieId =?;";
			PreparedStatement ps5 = dbCon.prepareStatement(query5);
			ps5.setString(1, movieId);
			ResultSet resultSet5 = ps5.executeQuery();
		
    		out.println("<div class=\"col-lg-4 col-sm-12\">");
    		out.println("<div class=\"card noMargin\" style=\"width: 18rem;\">");
    		out.println("<div class=\"card-body noMargin\">");
    		out.println("<h2 class=\"card-title header noMargin\"><a class=\"linkBlack\" href=\"singleMovie.jsp?Title=" + movieTitle + "\">" + movieTitle + "</a></h2>"); // add link
    		out.println("<p class=\"card-text cardBody noMargin\">Directed: " + movieDirector + "</p>");
    		out.println("<p class=\"card-text cardBody noMargin\">Year: " + movieYear + "</p>");
    		out.println("<p class=\"card-text cardBody noMargin\">Genres: ");
    		while(resultSet2.next())
    		{
    			String genreNames = resultSet2.getString("g.name");
    			out.println("<a class=\"linkBlack\" href=\"movieListBrowse.jsp?Genre=" + genreNames + "\">" + genreNames + "</a>");
    		}
    		ps2.close();
    		resultSet2.close();
    		out.println("<p class=\"card-text cardBody noMargin\">Stars: ");
	    	while(resultSet4.next())
    		{
    			String starName2= resultSet4.getString("s.name");
    			out.println("<a a class=\"linkBlack\" href=\"singleStar.jsp?Star=" + starName2 + "\">" + starName2 + "</a>");    
    		}
    		ps4.close();
	    	resultSet4.close();
    		out.println("</p>");
    	
	    	if(resultSet5.next())
  		  	{
    			String rs5Rating = resultSet5.getString("rating");
    			out.println("<p class=\"card-text cardBody noMargin\">Rating: " + rs5Rating + "</p>");
	    	}
	    	else
	    	{
    			out.println("<p class=\"card-text cardBody noMargin\">Rating: N/A </p>");
    		}
	    	ps5.close();
    		resultSet5.close();
    		out.println("<div class=\"container text-center\">");
			out.println("<div class=\"row text-center\">");
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
    		out.println("<input class=\"col-xl-12 col-lg-12 col-md-12 col-sm-12 col-xs-12 button-fabBlue\" type=\"submit\" value=\"Add To Cart\">");
    		out.println("</div>");
    		out.println("</div>");
    		out.println("</form>");
	    	out.println("</div>");  
    		out.println("</div>");
    		out.println("</div>");
    	}
    	}
	}
%>
	</div>
</div>
</div>
</div>
<%
	int items = 0;
	if(!starName.isEmpty() && starName != null)
	{
		String query01 = "SELECT COUNT(*) AS rowNums FROM movies AS m LEFT JOIN ratings ON m.id = ratings.movieId LEFT JOIN stars_in_movies ON m.id = stars_in_movies.movieId LEFT JOIN stars ON stars_in_movies.starId = stars.id WHERE stars.name LIKE '%" + starName + "%' AND m.title LIKE \"%" + title + "%\" AND m.year LIKE \"%" + year + "%\" AND m.director LIKE \"%" + director + "%\";";
		PreparedStatement ps01 = dbCon.prepareStatement(query01);
		ps01.setString(1, "%"+starName+"%");
		ps01.setString(2, "%"+title+"%");
		ps01.setString(3, "%"+year+"%");
		ps01.setString(4, "%"+director+"%");
		ResultSet resultSet01 = ps01.executeQuery();
		resultSet01.next();
		String rowNums = resultSet01.getString("rowNums");
		items = Integer.parseInt(rowNums);
	}
	else
	{
		String query0 = "SELECT COUNT(*) as rowNums FROM movies AS m WHERE m.title LIKE ? AND m.year LIKE ? AND m.director LIKE ?;";
		PreparedStatement ps0 = dbCon.prepareStatement(query0);
		ps0.setString(1, "%"+title+"%");
		ps0.setString(2, "%"+year+"%");
		ps0.setString(3, "%"+director+"%");
		ResultSet resultSet0 = ps0.executeQuery();			
		resultSet0.next();
		String rowNums = resultSet0.getString("rowNums");
		items = Integer.parseInt(rowNums);
	}
	int pages = items/Integer.parseInt(perPage);
	if(items <= Integer.parseInt(perPage))
	{
		pages = 1;
	}
	out.println("<nav aria-label=\"MoviePagination\">");
	out.println("<ul class=\"pagination\">");
    requestURL = request.getRequestURL();
	if(request.getQueryString()!= null)
	{
		requestURL.append("?").append(request.getQueryString());
		if(requestURL.indexOf("&pageNum") != -1)
		{
			int badIndex = requestURL.indexOf("&pageNum");
			requestURL.replace(badIndex, requestURL.length(), "");
		}

	}
	if((Integer.parseInt(pageNum) - 1) < 1)
	{
	  out.println("<li class=\"page-item\"><a class=\"page-link\" href=\"" + requestURL + "&pageNum=" + 1 +  "\">Previous</a></li>");
	}
	else
	{
	  out.println("<li class=\"page-item\"><a class=\"page-link\" href=\"" + requestURL + "&pageNum=" + (Integer.parseInt(pageNum) - 1) +  "\">Previous</a></li>");	  
	} 
	for(int i = 1; i <= pages; i++)
	{
	  out.println("<li class=\"page-item\"><a class=\"page-link\" href=\"" + requestURL + "&pageNum=" + i +  "\">" + i + "</a></li>");
	}
	if((Integer.parseInt(pageNum) + 1) > pages)
	{
		out.println("<li class=\"page-item\"><a class=\"page-link\" href=\"" + requestURL + "&pageNum=" + pages +  "\">Next</a></li>");
	}
	else
	{
		out.println("<li class=\"page-item\"><a class=\"page-link\" href=\"" + requestURL + "&pageNum=" + (Integer.parseInt(pageNum) + 1) +  "\">Next</a></li>");
	}
	out.println("</ul>");
	out.println("</nav>");
	
%>



<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js" integrity="sha384-cs/chFZiN24E4KMATLdqdvsezGxaGsi4hLGOzlXwp5UZB1LY//20VyM2taTB4QvJ" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js" integrity="sha384-uefMccjFJAIv6A+rW+L4AHf99KvxDjWSu1z9VI8SKNVmz4sk7buKt/6v9KI65qnm" crossorigin="anonymous"></script>
</body>
</html>