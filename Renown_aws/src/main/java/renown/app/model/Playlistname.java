package renown.app.model;

public class Playlistname {
	public final String name;
	
	public Playlistname (String name) {
		this.name = name;
	}
	
	public boolean equals (Object o) {
		if (o == null) { 
			return false; 
		}
		
		if (o instanceof Playlistname) {
			Playlistname other = (Playlistname) o;
			return name.equals(other.name);
		}
		
		return false;  // not a Playlistname
	}

}
