package renown.app;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import renown.app.http.*;
import renown.app.model.Segment;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class UploadSegmentTest extends LambdaTest {
	
    @Test
    public void testUploadSegment() throws IOException {
    	ListRDSSegmentsHandler handler = new ListRDSSegmentsHandler();

        AllSegmentsResponse resp = handler.handleRequest(null, createContext("list"));
        
        boolean FOX1 = false;
        for (Segment s : resp.list) {
        	if (s.id.equals("FOX1")) { FOX1 = true; break; }
        }
        Assert.assertTrue("FOX1 Needs to exist in the constants table (from tutorial) for this test case to work.", FOX1);
        Assert.assertEquals(200, resp.statusCode);
        
        String searchChar = "Fox";
        String searchLine = "afraid";
        SearchSegmentsHandler shandler = new SearchSegmentsHandler();
        SearchSegmentsRequest sreq = new SearchSegmentsRequest(searchChar,searchLine);
        SearchSegmentsResponse sresp = shandler.handleRequest(sreq, createContext("search"));
        //decoy
        SearchSegmentsRequest decsreq = new SearchSegmentsRequest();
        SearchSegmentsResponse decresp = new SearchSegmentsResponse(400, "error");        
        sresp.toString();
        Assert.assertEquals(200, sresp.statusCode);
        
        SearchSegmentsHandler shandler1 = new SearchSegmentsHandler();
        SearchSegmentsRequest sreq1 = new SearchSegmentsRequest("",searchLine);
        SearchSegmentsResponse sresp1 = shandler1.handleRequest(sreq1, createContext("search"));
        Assert.assertEquals(200, sresp1.statusCode);
        
        SearchSegmentsHandler shandler2 = new SearchSegmentsHandler();
        SearchSegmentsRequest sreq2 = new SearchSegmentsRequest(searchChar,"");
        SearchSegmentsResponse sresp2 = shandler.handleRequest(sreq2, createContext("search"));
        Assert.assertEquals(200, sresp2.statusCode);
        
        
        
        
        
//      
        String dseg_id = "THOMAS1";
        DeleteSegmentHandler dhandler = new DeleteSegmentHandler();
        DeleteSegmentRequest dreq = new DeleteSegmentRequest();
        dreq.setName(dseg_id);
        DeleteSegmentResponse dresp = dhandler.handleRequest(dreq, createContext("delete"));
        dresp.toString();
        Assert.assertEquals(200, dresp.statusCode);
        
        
    }

}
