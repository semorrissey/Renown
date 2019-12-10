package renown.app.http;

public class RemoveSegmentRequest {
	public String name;
	public String seg_id;
	public int seg_order;
	
	public String getName( ) { 
		return name; 
	}
	
	public String getID( ) { 
		return seg_id; 
	}
	
	public int getOrder() {
		return seg_order;
	}
	
	public void setName(String name) { 
		this.name = name; 
	}
	
	public void setID(String seg_id) { 
		this.seg_id = seg_id; 
	}
	
	public RemoveSegmentRequest() {
	}
	
	public RemoveSegmentRequest(String name, String seg_id, int seg_order) {
		this.name = name;
		this.seg_id = seg_id;
		this.seg_order = seg_order;
	}
	
	public String toString() {
		return "RemoveSegment(" + name + ": " + seg_id + ")";
	}
}
