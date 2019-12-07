package renown.app.model;

public class RemoteSite {
	public final String site_name;
	public final String site_url;
	
	public RemoteSite (String site_name, String site_url) {
		this.site_name = site_name;
		this.site_url = site_url;
	}
	
	public boolean equals (Object o) {
		if (o == null) { 
			return false; 
		}
		
		if (o instanceof RemoteSite) {
			RemoteSite other = (RemoteSite) o;
			return site_name.equals(other.site_name);
		}
		
		return false;  // not a RemoteSite
	}

}
