package renown.app.model;

public class Playlist {
	public final String name;
	public final String seg_id;
	public final int seg_order;
	
	public Playlist (String name, String seg_id, int seg_order) {
		this.name = name;
		this.seg_id = seg_id;
		this.seg_order = seg_order;
	}
	
	public boolean equals (Object o) {
		if (o == null) { 
			return false; 
		}
		
		if (o instanceof Playlist) {
			Playlist other = (Playlist) o;
			return name.equals(other.name);
		}
		
		return false;  // not a Playlist
	}

}
