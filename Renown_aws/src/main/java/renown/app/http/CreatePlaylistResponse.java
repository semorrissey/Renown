package renown.app.http;

public class CreatePlaylistResponse {
	public final String response;
	public final int httpCode;
	
	public CreatePlaylistResponse (String s, int code) {
		this.response = s;
		this.httpCode = code;
	}
	
	// 200 means success
	public CreatePlaylistResponse (String s) {
		this.response = s;
		this.httpCode = 200;
	}
	
	public String toString() {
		return "Response(" + response + ")";
	}
}