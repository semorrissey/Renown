package renown.app.http;

public class DeleteSegmentResponse {
	public final String seg_id;
	public final int statusCode;
	public final String error;
	
	public DeleteSegmentResponse (String seg_id, int statusCode) {
		this.seg_id = seg_id;
		this.statusCode = statusCode;
		this.error = "";
	}
	
	// 200 means success
	public DeleteSegmentResponse (String seg_id, int statusCode, String errorMessage) {
		this.statusCode = statusCode;
		this.error = errorMessage;
		this.seg_id = seg_id;
	}
	
	public String toString() {
		if (statusCode == 200) {
			return "DeleteResponse(" + seg_id + ")";
		} else {
			return "ErrorResult(" + seg_id + ", statusCode=" + statusCode + ", err=" + error + ")";
		}
	}
}
