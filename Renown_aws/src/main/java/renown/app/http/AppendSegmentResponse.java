package renown.app.http;

public class AppendSegmentResponse {
	public final String response;
	public final int httpCode;
	
	public AppendSegmentResponse (String s, int code) {
		this.response = s;
		this.httpCode = code;
	}
	
	// 200 means success
	public AppendSegmentResponse (String s) {
		this.response = s;
		this.httpCode = 200;
	}
	
	public String toString() {
		return "Response(" + response + ")";
	}
}