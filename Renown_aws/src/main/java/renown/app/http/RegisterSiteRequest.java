package renown.app.http;

public class RegisterSiteRequest {
	public String site_name;
	public String site_url;
	
	public String getUrl( ) { 
		return site_url; 
	}
	
	public String getName( ) { 
		return site_name; 
	}
	
	public void setUrl(String site_url) { 
		this.site_url = site_url; 
	}
	
	public void setName(String site_name) { 
		this.site_name = site_name; 
	}
	
	public RegisterSiteRequest() {
	}
	
	public RegisterSiteRequest(String site_name, String site_url) {
		this.site_name = site_name;
		this.site_url = site_url;
	}
	
	public String toString() {
		return "RegisterSite(" + site_name + ": " + site_url + ")";
	}
}
