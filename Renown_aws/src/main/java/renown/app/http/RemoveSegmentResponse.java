package renown.app.http;

public class RemoveSegmentResponse {
	public final String response;
	public final int httpCode;
	
	public RemoveSegmentResponse (String s, int code) {
		this.response = s;
		this.httpCode = code;
	}
	
	// 200 means success
	public RemoveSegmentResponse (String s) {
		this.response = s;
		this.httpCode = 200;
	}
	
	public String toString() {
		return "RemoveSegment Response(" + response + ")";
	}
}