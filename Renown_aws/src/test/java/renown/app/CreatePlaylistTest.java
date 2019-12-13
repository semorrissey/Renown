package renown.app;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import renown.app.db.PlaylistsDAO;
import renown.app.db.SegmentsDAO;
import renown.app.http.CreatePlaylistRequest;
import renown.app.http.CreatePlaylistResponse;
import renown.app.http.DeletePlaylistRequest;
import renown.app.http.DeletePlaylistResponse;
import renown.app.http.MarkSegmentRequest;
import renown.app.http.MarkSegmentResponse;
import renown.app.model.Playlist;
import renown.app.model.Playlistname;
import renown.app.model.Segment;

public class CreatePlaylistTest extends LambdaTest{

	@Test
    public void testCreatePlaylist() throws Exception {
    	CreatePlaylistHandler handler = new CreatePlaylistHandler();
    	
    	CreatePlaylistRequest req = new CreatePlaylistRequest();
    	req.setName("TTest");
    	req.getName();
    	PlaylistsDAO dao = new PlaylistsDAO();
    	Playlistname p = new Playlistname(req.name);
    	dao.getAllPlaylists();
    	
    	CreatePlaylistResponse decres = new CreatePlaylistResponse ("error", 400); 
    	CreatePlaylistResponse resp = handler.handleRequest(req, createContext("createPlaylist"));
    	CreatePlaylistResponse response = handler.handleRequest(req, createContext("createPlaylist"));
        resp.toString();
    	Assert.assertEquals(resp.httpCode, 200);
        
        DeletePlaylistHandler dhandler = new DeletePlaylistHandler();
    	
    	DeletePlaylistRequest dreq = new DeletePlaylistRequest();
    	dreq.setName("TTest");
    	dreq.getName();
    	
    	DeletePlaylistResponse dresp = dhandler.handleRequest(dreq, createContext("deletePlaylist"));
        dresp.toString();
    	//Assert.assertEquals(dresp.statusCode, 200);
        
    }
}
//public List<Playlist> getAllPlaylists() throws Exception {
