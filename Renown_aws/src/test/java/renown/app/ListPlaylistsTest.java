package renown.app;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import renown.app.http.*;
import renown.app.model.Playlist;
import renown.app.model.Playlistname;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class ListPlaylistsTest extends LambdaTest {
	
    @Test
    public void testListPlaylists() throws IOException {
    	ListRDSPlaylistsHandler handler = new ListRDSPlaylistsHandler();
    	ListPlaylistsRequest lreq = new ListPlaylistsRequest();
    	lreq.toString();

        AllPlaylistsResponse resp = handler.handleRequest(null, createContext("list"));
        //decoy
        AllPlaylistsResponse decoy = new AllPlaylistsResponse (400, "error");
        decoy.toString();
        boolean Test = false;
        for (Playlistname p : resp.list) {
        	if (p.name.equals("Test")) { Test = true; break; }
        }
        Assert.assertTrue("Test Needs to exist in the constants table (from tutorial) for this test case to work.", Test);
        Assert.assertEquals(200, resp.statusCode);
    }

}
