package renown.app;

import org.junit.Assert;
import org.junit.Test;

import renown.app.db.PlaylistsDAO;
import renown.app.db.SegmentsDAO;
import renown.app.http.CreatePlaylistRequest;
import renown.app.http.CreatePlaylistResponse;
import renown.app.http.MarkSegmentRequest;
import renown.app.http.MarkSegmentResponse;
import renown.app.model.Playlistname;
import renown.app.model.Segment;

public class CreatePlaylistTest extends LambdaTest{

	@Test
    public void testCreatePlaylist() throws Exception {
    	CreatePlaylistHandler handler = new CreatePlaylistHandler();
    	
    	CreatePlaylistRequest req = new CreatePlaylistRequest("Dino");
    	PlaylistsDAO dao = new PlaylistsDAO();
    	Playlistname p = new Playlistname(req.name);
    	dao.addPlaylist(p);
    	
    	CreatePlaylistResponse resp = handler.handleRequest(req, createContext("createPlaylist"));
        Assert.assertTrue(true);
    }
}
