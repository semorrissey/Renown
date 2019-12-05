package renown.app.http;

public class AppendSegmentRequest {
	public String name;
	public String seg_id;
	
	public String getName( ) { 
		return name; 
	}
	
	public String getID( ) { 
		return seg_id; 
	}
	
	public void setName(String name) { 
		this.name = name; 
	}
	
	public void setID(String seg_id) { 
		this.seg_id = seg_id; 
	}
	
	public AppendSegmentRequest() {
	}
	
	public AppendSegmentRequest(String name, String seg_id) {
		this.name = name;
		this.seg_id = seg_id;
	}
	
	public String toString() {
		return "AppendSegment(" + name + ": " + seg_id + ")";
	}
}
