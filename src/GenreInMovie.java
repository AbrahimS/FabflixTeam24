public class GenreInMovie {

	private int genreId;

	private String movieId;
	
	
	public GenreInMovie(){
		
	}
	
	public GenreInMovie(int genreId, String movieId) {
		this.movieId = movieId;
		this.genreId = genreId;

		
	}
	public String getMovieId() {
		return movieId;
	}

	public void setMovieId(String movieId) {
		this.movieId = movieId;
	}

	public int getGenreId() {
		return genreId;
	}

	public void setGenreId(int genreId) {
		this.genreId = genreId;
	}	
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("GenresInMovie Details - ");
		sb.append("genreId:" + getGenreId());
		sb.append(", ");
		sb.append("movieIc:" + getMovieId());
		sb.append(".");
		
		return sb.toString();
	}
}
