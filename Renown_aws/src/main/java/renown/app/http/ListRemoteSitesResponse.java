package renown.app.http;

import java.util.ArrayList;
import java.util.List;

import renown.app.model.RemoteSite;

public class ListRemoteSitesResponse {
	public final List<RemoteSite> list;
	public final int statusCode;
	public final String message;
	
	public ListRemoteSitesResponse (List<RemoteSite> list, int code) {
		this.list = list;
		this.statusCode = code;
		this.message = "";
	}
	
	public ListRemoteSitesResponse (int statusCode, String errorMessage) {
		this.list = new ArrayList<RemoteSite>();
		this.statusCode = statusCode;
		this.message = errorMessage;
	}
	
	public String toString() {
		if (list == null) { return "Empty RemoteSites"; }
		return "All RemoteSites(" + list.size() + ")";
	}
}
