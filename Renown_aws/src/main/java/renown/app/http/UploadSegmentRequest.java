package renown.app.http;

public class UploadSegmentRequest {
	public String seg_id;
	public String character;
	public String line;
	public String base64EncodedValue;
	public boolean system;
	
	public String getID( ) { 
		return seg_id; 
	}
	
	public void setID(String seg_id) { 
		this.seg_id = seg_id; 
	}
	
	public boolean getSystem( ) { 
		return system; 
	}
	
	public void setSystem(boolean system) { 
		this.system = system; 
	}
	
	public String getBase64EncodedValue() { 
		return base64EncodedValue; 
	}
	
	public void setBase64EncodedValue(String base64EncodedValue) { 
		this.base64EncodedValue = base64EncodedValue; 
	}
	
	public UploadSegmentRequest() {
	}
	
	public UploadSegmentRequest(String seg_id, String character, String line, String encoding) {
		this.seg_id = seg_id;
		this.character = character;
		this.line = line;
		this.base64EncodedValue = encoding;
	}
	
	public UploadSegmentRequest (String seg_id, String character, String line, String encoding, boolean system) {
		this.seg_id = seg_id;
		this.character = character;
		this.line = line;
		this.base64EncodedValue = encoding;
		this.system = system;
	}
	
	public String toString() {
		return "UploadSegment(" + seg_id + "," + base64EncodedValue + ")";
	}
}
