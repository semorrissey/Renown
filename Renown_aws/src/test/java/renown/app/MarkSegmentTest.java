package renown.app;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import renown.app.db.SegmentsDAO;
import renown.app.http.*;
import renown.app.model.Segment;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class MarkSegmentTest extends LambdaTest {
	
    @Test
    public void testMarkSegment() throws Exception {
    	MarkSegmentHandler handler = new MarkSegmentHandler();
    	
    	MarkSegmentRequest req = new MarkSegmentRequest();
    	req = new MarkSegmentRequest("FOX1");
    	req.setID("FOX1");
    	req.getID();
    	SegmentsDAO dao = new SegmentsDAO();
    	Segment s = dao.getSegment(req.seg_id);
    	MarkSegmentResponse decresp = new MarkSegmentResponse(null);
    	decresp.toString();
    	if (s.availableRemote) {
    		MarkSegmentResponse resp = handler.handleRequest(req, createContext("mark"));
            s = dao.getSegment(req.seg_id);
            Assert.assertFalse(s.availableRemote);
            Assert.assertEquals(200, resp.httpCode);
            MarkSegmentResponse resp2 = handler.handleRequest(req, createContext("mark"));
            s = dao.getSegment(req.seg_id);
            Assert.assertTrue(s.availableRemote);
            Assert.assertEquals(200, resp2.httpCode);
    	}
    	else {
    		MarkSegmentResponse resp = handler.handleRequest(req, createContext("mark"));
            s = dao.getSegment(req.seg_id);
            Assert.assertTrue(s.availableRemote);
            Assert.assertEquals(200, resp.httpCode);
            MarkSegmentResponse resp2 = handler.handleRequest(req, createContext("mark"));
            s = dao.getSegment(req.seg_id);
            Assert.assertFalse(s.availableRemote);
            Assert.assertEquals(200, resp2.httpCode);
    	}
    }

}
