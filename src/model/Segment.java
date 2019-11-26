package model;

public class Segment {
	public final String id;
	public final String character;
	public final String line;
	public boolean availableRemote;
	public boolean system;            // when TRUE this is actually stored in S3 bucket
	
	public Segment (String id, String character, String line) {
		this.id = id;
		this.character = character;
		this.line = line;
		this.availableRemote = true;
	}
	
	public Segment (String id, String character, String line, boolean system) {
		this.id = id;
		this.character = character;
		this.line = line;
		this.availableRemote = true;
		this.system = system;
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
