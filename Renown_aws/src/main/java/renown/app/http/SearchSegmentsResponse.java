package renown.app.http;

import java.util.ArrayList;
import java.util.List;

import renown.app.model.Segment;

public class SearchSegmentsResponse {
	public final List<Segment> list;
	public final int statusCode;
	public final String message;
	
	public SearchSegmentsResponse (List<Segment> list, int code) {
		this.list = list;
		this.statusCode = code;
		this.message = "";
	}
	
	public SearchSegmentsResponse (int statusCode, String errorMessage) {
		this.list = new ArrayList<Segment>();
		this.statusCode = statusCode;
		this.message = errorMessage;
	}
	
	public String toString() {
		if (list == null) { return "EmptySegments"; }
		return "SearchSegments(" + list.size() + ")";
	}
}
