package renown.app.http;

import java.util.ArrayList;
import java.util.List;

import renown.app.model.Playlist;

public class AllPlaylistsResponse {
	public final List<Playlist> list;
	public final int statusCode;
	public final String message;
	
	public AllPlaylistsResponse (List<Playlist> list, int code) {
		this.list = list;
		this.statusCode = code;
		this.message = "";
	}
	
	public AllPlaylistsResponse (int statusCode, String errorMessage) {
		this.list = new ArrayList<Playlist>();
		this.statusCode = statusCode;
		this.message = errorMessage;
	}
	
	public String toString() {
		if (list == null) { return "EmptyPlaylists"; }
		return "AllPlaylists(" + list.size() + ")";
	}
}
