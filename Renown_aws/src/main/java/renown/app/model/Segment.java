package renown.app.model;

public class Segment {
	public final String id;
	public final String character;
	public final String line;
	public final String url;
	public boolean availableRemote;
	public boolean system;            // when TRUE this is actually stored in S3 bucket
	
	public Segment (String id, String character, String line, String url) {
		this.id = id;
		this.character = character;
		this.line = line;
		this.url = url;
		this.availableRemote = true;
	}
	
	public Segment (String id, String character, String line, String url, boolean availableRemote) {
		this.id = id;
		this.character = character;
		this.line = line;
		this.availableRemote = availableRemote;
		this.url = url;
	}
	
	public boolean getSystem() { 
		return system; 
	}
	
	public void setSystem(boolean s) { 
		system = s; 
	}
	
	public void markSegment() {
		availableRemote = false;
	}
	
	public void unmarkSegment() {
		availableRemote = true;
	}
	
	public boolean equals (Object o) {
		if (o == null) { 
			return false; 
		}
		
		if (o instanceof Segment) {
			Segment other = (Segment) o;
			return id.equals(other.id);
		}
		
		return false;  // not a Segment
	}

}
