package renown.app.http;

public class UnregisterSiteResponse {
	public final String response;
	public final int httpCode;
	public final String error;
	
	public UnregisterSiteResponse (String s, int code) {
		this.response = s;
		this.httpCode = code;
		this.error = "";
	}
	
	// 200 means success
	public UnregisterSiteResponse (String s, int code, String errorMessage) {
		this.response = s;
		this.httpCode = code;
		this.error = errorMessage;
	}
	
	public String toString() {
		return "RegisterSite Response(" + response + ")";
	}
}