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
    	
    	MarkSegmentRequest req = new MarkSegmentRequest("FOX1");
    	SegmentsDAO dao = new SegmentsDAO();
    	Segment s = dao.getSegment(req.seg_id);
    	if (s.availableRemote) {
    		MarkSegmentResponse resp = handler.handleRequest(req, createContext("mark"));
            s = dao.getSegment(req.seg_id);
            Assert.assertTrue(!s.availableRemote);
    	}
    	else {
    		MarkSegmentResponse resp = handler.handleRequest(req, createContext("mark"));
            s = dao.getSegment(req.seg_id);
            Assert.assertTrue(s.availableRemote);
    	}
    }

}
