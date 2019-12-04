package renown.app.http;

import java.util.ArrayList;
import java.util.List;

import renown.app.model.Playlist;
import renown.app.model.Playlistname;

public class AllPlaylistsResponse {
	public final List<Playlistname> list;
	public final int statusCode;
	public final String message;
	
	public AllPlaylistsResponse (ArrayList<Playlistname> segList, int code) {
		this.list = segList;
		this.statusCode = code;
		this.message = "";
	}
	
	public AllPlaylistsResponse (int statusCode, String errorMessage) {
		this.list = new ArrayList<Playlistname>();
		this.statusCode = statusCode;
		this.message = errorMessage;
	}
	
	public String toString() {
		if (list == null) { return "EmptyPlaylists"; }
		return "AllPlaylists(" + list.size() + ")";
	}
}
