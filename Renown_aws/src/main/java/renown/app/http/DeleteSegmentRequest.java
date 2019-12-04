package renown.app.http;

public class DeleteSegmentRequest {
	public String seg_id;
	
	public void setName(String seg_id) {
		this.seg_id = seg_id; 
	}
	
	public String getName() {
		return seg_id; 
	}
	
	public DeleteSegmentRequest (String seg_id) {
		this.seg_id = seg_id;
	}

	public DeleteSegmentRequest() {
		
	}
	
	public String toString() {
		return "Delete(" + seg_id + ")";
	}
}
