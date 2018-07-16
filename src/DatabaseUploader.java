import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


 public class DatabaseUploader {


	public DatabaseUploader(){

	}

	public DatabaseUploader(List<Movie> myMovies, List<Genres> myGenres, List<GenreInMovie> genreInMovie, List<Star> myStars, List<StarInMovie> starInMovie)
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection dbCon = DriverManager.getConnection("jdbc:mysql://localhost:3306/moviedb", "mytestuser", "jakeisbest");
			
			for(int i = 0; i < myMovies.size(); i++)
			{
				try
				{
				String mId = myMovies.get(i).getId();
				String mTitle = myMovies.get(i).getTitle();
				int mYear = myMovies.get(i).getYear();
				String mDirector = myMovies.get(i).getDirector();
				
				Statement statement = dbCon.createStatement();
				mTitle = mTitle.replaceAll("'", "");
				
				String queryMovieTitle = String.format("SELECT DISTINCT title FROM movies WHERE title = '%s';", mTitle);
				ResultSet rs = statement.executeQuery(queryMovieTitle);
				if(rs.next())
				{
					System.out.println("ERROR: Movie: " + mTitle + " exists!");
				}
				else
				{
					Statement statement2 = dbCon.createStatement();
					String movieInsert = String.format("INSERT INTO movies (id, title, year, director) VALUES('%s', '%s', %d, '%s');", mId, mTitle, mYear, mDirector);
					statement2.executeUpdate(movieInsert);
					statement2.close();
					System.out.println("SUCCESS: Movie: " + mTitle + " inserted!");
				}
				statement.close();
				rs.close();
				}
				catch(Exception NullPointerException)
				{
					System.out.println("VALUE = NULL");
					continue;
				}
			}

			for(int i = 0; i < myGenres.size(); i++)
			{
				int gId = myGenres.get(i).getId();
				String gName = myGenres.get(i).getName();
				
				Statement statement = dbCon.createStatement();

				String queryGenreName = String.format("SELECT DISTINCT name FROM genres WHERE name = '%s';", gName);
				ResultSet rs = statement.executeQuery(queryGenreName);
				if(rs.next())
				{
					System.out.println("ERROR: Genre: " + gName + " exists!");
				}
				else
				{
					Statement statement2 = dbCon.createStatement();
					String genreInsert = String.format("INSERT INTO genres (id, name) VALUES(%d, '%s');", gId, gName);
					statement2.executeUpdate(genreInsert);
					statement2.close();
					System.out.println("SUCCESS: Genre: " + gName + " inserted!");
				}
				statement.close();
				rs.close();
			}

			for(int i = 0; i < genreInMovie.size(); i++)
			{
				int ggId = genreInMovie.get(i).getGenreId();
				String gmId = genreInMovie.get(i).getMovieId();
				System.out.println("Genre Id: " + ggId + " Movie Id: " + gmId);
				Statement statement = dbCon.createStatement();
				Statement statement3 = dbCon.createStatement();
				Statement statement4 = dbCon.createStatement();

				String queryGenreInMovie = String.format("SELECT DISTINCT genreId, movieId FROM genres_in_movies WHERE genreId = %d AND movieId = '%s';", ggId, gmId);
				String queryMovieId = String.format("SELECT DISTINCT * FROM movies WHERE id = '%s';", gmId);
				String queryGenreId = String.format("SELECT DISTINCT * FROM genres WHERE id = %d;", ggId);
				ResultSet rs = statement.executeQuery(queryGenreInMovie);
				ResultSet rs2 = statement3.executeQuery(queryMovieId);
				ResultSet rs3 = statement4.executeQuery(queryGenreId);
				if(rs.next())
				{
					System.out.println("ERROR: Genre Id: " + ggId + " Movie Id: " + gmId + " exists!");
				}
				else if(rs2.next() && rs3.next())
				{
					Statement statement2 = dbCon.createStatement();
					String genreInMovieInsert = String.format("INSERT INTO genres_in_movies (genreId, movieId) VALUES(%d, '%s');", ggId, gmId);
					statement2.executeUpdate(genreInMovieInsert);
					statement2.close();
					System.out.println("SUCCESS: Genre Id: " + ggId + " Movie Id: " + gmId + " inserted!");
				}
				statement.close();
				rs.close();
			}

			for(int i = 0; i < myStars.size(); i++)
			{
				String sId = myStars.get(i).getId();
				String sName = myStars.get(i).getName();
				int sYear = myStars.get(i).getBirthYear();
				sName = sName.replaceAll("'", "");
				Statement statement = dbCon.createStatement();

				String queryStarName = String.format("SELECT DISTINCT name FROM stars WHERE name = '%s';", sName);
				ResultSet rs = statement.executeQuery(queryStarName);
				if(rs.next())
				{
					System.out.println("ERROR: Star: " + sName + " exists!");
				}
				else
				{
					Statement statement2 = dbCon.createStatement();
					String starInsert = String.format("INSERT INTO stars (id, name, birthYear) VALUES('%s', '%s', %d);", sId, sName, sYear);
					statement2.executeUpdate(starInsert);
					statement2.close();
					System.out.println("SUCCESS: Star: " + sName + " inserted!");
				}
				statement.close();
				rs.close();
				
			}

			for(int i = 0; i < starInMovie.size(); i++)
			{
				String sId = starInMovie.get(i).getStarId();
				String smId = starInMovie.get(i).getMovieId();
				
				Statement statement = dbCon.createStatement();
				Statement statement3 = dbCon.createStatement();
				Statement statement4 = dbCon.createStatement();

				String queryStarInMovie = String.format("SELECT DISTINCT starId, movieId FROM stars_in_movies WHERE starId = '%s' AND movieId = '%s';", sId, smId);
				String queryMovieId = String.format("SELECT DISTINCT * FROM movies WHERE id = '%s';", smId);
				String queryStarId = String.format("SELECT DISTINCT * FROM stars WHERE id = '%s';", sId);
				ResultSet rs = statement.executeQuery(queryStarInMovie);
				ResultSet rs2 = statement3.executeQuery(queryMovieId);
				ResultSet rs3 = statement4.executeQuery(queryStarId);
				if(rs.next())
				{
					System.out.println("ERROR: Star Id: " + sId + " Movie Id: " + smId + " exists!");
				}
				else if(rs2.next() && rs3.next())
				{
					Statement statement2 = dbCon.createStatement();
					String starInMovieInsert = String.format("INSERT INTO stars_in_movies (starId, movieId) VALUES('%s', '%s');", sId, smId);
					statement2.executeUpdate(starInMovieInsert);
					statement2.close();
					System.out.println("SUCCESS: Star Id: " + sId + " Movie Id: " + smId + " inserted!");
				}
				statement.close();
				rs.close();
			}
			dbCon.close();
		}
		catch(Exception ex)
		{
			System.out.println("EXCEPTION: " + ex);
		}

	}

}
