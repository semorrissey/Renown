package renown.app.http;

public class DeletePlaylistRequest {
	public String name;
	
	public void setName(String name) {
		this.name = name; 
	}
	
	public String getName() {
		return name; 
	}
	
	public DeletePlaylistRequest (String name) {
		this.name = name;
	}

	public DeletePlaylistRequest() {
		
	}
	
	public String toString() {
		return "Delete(" + name + ")";
	}
}
