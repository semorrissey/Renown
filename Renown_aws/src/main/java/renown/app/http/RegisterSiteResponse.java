package renown.app.http;

public class RegisterSiteResponse {
	public final String response;
	public final int httpCode;
	
	public RegisterSiteResponse (String s, int code) {
		this.response = s;
		this.httpCode = code;
	}
	
	// 200 means success
	public RegisterSiteResponse (String s) {
		this.response = s;
		this.httpCode = 200;
	}
	
	public String toString() {
		return "RegisterSite Response(" + response + ")";
	}
}