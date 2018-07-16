public class Movie {

	private String id;

	private String title;
	
	private int year;

	private String director;
	
	private String rating;
	private String genres;
	private String stars;
	
	public Movie(){
		
	}
	
	public Movie(String id, String title, int year, String director, String genres, String stars, String rating) {
		this.id = id;
		this.title = title;
		this.year  = year;
		this.director = director;
		this.genres = genres;
		this.stars = stars;
		this.rating = rating;
		
	}
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getYear() {
		return year;
	}
	
	public String getGenres()
	{
		return genres;
	}
	
	public String getStars()
	{
		return stars;
	}
	
	public String getRating() {
		return rating;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}		
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Movie Details - ");
		sb.append("ID:" + getId());
		sb.append(", ");
		sb.append("TITLE:" + getTitle());
		sb.append(", ");
		sb.append("YEAR:" + getYear());
		sb.append(", ");
		sb.append("Director:" + getDirector());
		sb.append(".");
		
		return sb.toString();
	}
}
