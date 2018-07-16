public class StarInMovie {

	private String starId;

	private String movieId;
	
	
	public StarInMovie(){
		
	}
	
	public StarInMovie(String starId, String movieId) {
		this.movieId = movieId;
		this.starId = starId;

		
	}
	public String getMovieId() {
		return movieId;
	}

	public void setMovieId(String movieId) {
		this.movieId = movieId;
	}

	public String getStarId() {
		return starId;
	}

	public void setStarId(String starId) {
		this.starId = starId;
	}	
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("StarsInMovie Details - ");
		sb.append("StarId:" + getStarId());
		sb.append(", ");
		sb.append("movieId:" + getMovieId());
		sb.append(".");
		
		return sb.toString();
	}
}
