package renown.app.http;

public class RegisterSiteRequest {
	public String site_url;
	
	public String getUrl( ) { 
		return site_url; 
	}
	
	public void setUrl(String site_url) { 
		this.site_url = site_url; 
	}
	
	public RegisterSiteRequest() {
	}
	
	public RegisterSiteRequest(String site_url) {
		this.site_url = site_url;
	}
	
	public String toString() {
		return "RegisterSite(" + site_url + ")";
	}
}
