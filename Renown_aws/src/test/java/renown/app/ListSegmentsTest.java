package renown.app;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import renown.app.http.*;
import renown.app.model.Segment;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class ListSegmentsTest extends LambdaTest {
	
    @Test
    public void testListSegments() throws IOException {
    	ListRDSSegmentsHandler handler = new ListRDSSegmentsHandler();
    	ListSegmentsRequest req = new ListSegmentsRequest();
    	req.toString();

        AllSegmentsResponse resp = handler.handleRequest(null, createContext("list"));
        AllSegmentsResponse decresp = new AllSegmentsResponse(400,"error");
        decresp.toString();
        boolean FOX1 = false;
        for (Segment s : resp.list) {
        	if (s.id.equals("FOX1")) { FOX1 = true; break; }
        }
        Assert.assertTrue("FOX1 Needs to exist in the constants table (from tutorial) for this test case to work.", FOX1);
        Assert.assertEquals(200, resp.statusCode);
    }

}
