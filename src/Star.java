public class Star {

	private String id;

	private String name;
	
	private int birthYear;

	
	public Star(){
		
	}
	
	public Star(String id, String name, int birthYear) {
		this.id = id;
		this.name = name;
		this.birthYear = birthYear;

		
	}
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getBirthYear() {
		return birthYear;
	}

	public void setBirthYear(int year) {
		this.birthYear = year;
	}
	
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Star Details - ");
		sb.append("ID:" + getId());
		sb.append(", ");
		sb.append("NAME:" + getName());
		sb.append(", ");
		sb.append("BIRTHYEAR:" + getBirthYear());
		sb.append(".");
		
		return sb.toString();
	}
}
