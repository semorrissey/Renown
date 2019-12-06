package renown.app.http;

public class ShowPlaylistRequest {
	public String name;
	
	public String getName( ) { 
		return name; 
	}
	
	
	public void setName(String name) { 
		this.name = name; 
	}
	
	
	public ShowPlaylistRequest() {
	}
	
	public ShowPlaylistRequest(String name) {
		this.name = name;
	}
	
	public String toString() {
		return "ShowPlaylist(" + name + ")";
	}
}
