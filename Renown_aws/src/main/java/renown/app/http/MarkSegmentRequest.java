package renown.app.http;

public class MarkSegmentRequest {
	public String seg_id;
	
	public String getID( ) { 
		return seg_id; 
	}
	
	public void setID(String seg_id) { 
		this.seg_id = seg_id; 
	}
	
	public MarkSegmentRequest() {
	}
	
	public MarkSegmentRequest(String seg_id) {
		this.seg_id = seg_id;
	}
	
	public String toString() {
		return "MarkSegment(" + seg_id + ")";
	}
}
