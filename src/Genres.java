public class Genres {

	private int id;

	private String name;
	
	
	public Genres(){
		
	}
	
	public Genres(int id, String name) {
		this.id = id;
		this.name = name;

		
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}	
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Genres Details - ");
		sb.append("ID:" + getId());
		sb.append(", ");
		sb.append("name:" + getName());
		sb.append(".");
		
		return sb.toString();
	}
}
