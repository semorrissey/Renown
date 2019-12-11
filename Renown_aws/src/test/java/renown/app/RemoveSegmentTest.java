package renown.app;

import org.junit.Assert;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;

import renown.app.db.PlaylistsDAO;
import renown.app.http.CreatePlaylistRequest;
import renown.app.http.CreatePlaylistResponse;
import renown.app.http.RemoveSegmentRequest;
import renown.app.http.RemoveSegmentResponse;
import renown.app.model.Playlist;
import renown.app.model.Playlistname;

public class RemoveSegmentTest extends LambdaTest{
	@Test
    public void testCreatePlaylist() throws Exception {
    	RemoveSegmentHandler handler = new RemoveSegmentHandler();
    	
    	RemoveSegmentRequest req = new RemoveSegmentRequest("Test", "KHAN1", 2);
    	RemoveSegmentResponse response = new RemoveSegmentResponse(req.name);
    	
    	Context c = createContext("removeSegment");
    	RemoveSegmentResponse resp = handler.handleRequest(req, c);
        Assert.assertEquals(resp, response);
    }
}
