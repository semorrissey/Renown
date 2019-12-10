package renown.app.http;

public class UnregisterSiteRequest {
	public String site_url;
	
	public String getUrl( ) { 
		return site_url; 
	}
	
	public void setUrl(String site_url) { 
		this.site_url = site_url; 
	}
	
	public UnregisterSiteRequest() {
	}
	
	public UnregisterSiteRequest(String site_url) {
		this.site_url = site_url;
	}
	
	public String toString() {
		return "UnregisterSite(" + site_url + ")";
	}
}
