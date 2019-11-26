package renown.app;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import renown.app.http.*;
import renown.app.model.Playlist;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class ListPlaylistsTest extends LambdaTest {
	
    @Test
    public void testListSegments() throws IOException {
    	ListRDSPlaylistsHandler handler = new ListRDSPlaylistsHandler();

        AllPlaylistsResponse resp = handler.handleRequest(null, createContext("list"));
        
        boolean Test = false;
        for (Playlist p : resp.list) {
        	if (p.name.equals("Test")) { Test = true; break; }
        }
        Assert.assertTrue("Test Needs to exist in the constants table (from tutorial) for this test case to work.", Test);
        Assert.assertEquals(200, resp.statusCode);
    }

}
