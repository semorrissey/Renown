package renown.app.http;


public class SearchSegmentsRequest {
	public String character;
	public String line;

	public SearchSegmentsRequest(){
	}
	
	public SearchSegmentsRequest(String character, String line){
		this.character = character;
		this.line = line;
	}
	
	public String toString() {
		return "List()";
	}
	
	
}


