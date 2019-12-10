package renown.app.http;

public class MarkSegmentResponse {
	public final String response;
	public final int httpCode;
	
	public MarkSegmentResponse (String s, int code) {
		this.response = s;
		this.httpCode = code;
	}
	
	// 200 means success
	public MarkSegmentResponse (String s) {
		this.response = s;
		this.httpCode = 200;
	}
	
	public String toString() {
		return "MarkSegment Response(" + response + ")";
	}
}