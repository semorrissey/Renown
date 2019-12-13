package renown.app;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;

import renown.app.db.PlaylistsDAO;
import renown.app.http.AllPlaylistsResponse;
import renown.app.http.AppendSegmentRequest;
import renown.app.http.AppendSegmentResponse;
import renown.app.http.CreatePlaylistRequest;
import renown.app.http.CreatePlaylistResponse;
import renown.app.http.DeletePlaylistRequest;
import renown.app.http.DeletePlaylistResponse;
import renown.app.http.RemoveSegmentRequest;
import renown.app.http.RemoveSegmentResponse;
import renown.app.http.ShowPlaylistRequest;
import renown.app.http.ShowPlaylistResponse;
import renown.app.model.Playlist;
import renown.app.model.Playlistname;
import renown.app.model.Segment;

public class RemoveSegmentTest extends LambdaTest{
	@Test
    public void testCreatePlaylist() throws Exception {
		CreatePlaylistHandler handler = new CreatePlaylistHandler();
    	
    	CreatePlaylistRequest req = new CreatePlaylistRequest();
    	req.setName("RemTest");
    	req.getName();
    	CreatePlaylistRequest creq = new CreatePlaylistRequest("RemTest");
    	req.toString();
    	
    	
    	CreatePlaylistResponse resp = handler.handleRequest(req, createContext("createPlaylist"));
		
    	ListRDSPlaylistsHandler Lhandler = new ListRDSPlaylistsHandler();

        AllPlaylistsResponse respon = Lhandler.handleRequest(null, createContext("list"));
        
        boolean Test = false;
        for (Playlistname p : respon.list) {
        	if (p.name.equals(req.name)) { Test = true; break; }
        }
        Assert.assertTrue(Test);
        Assert.assertEquals(200, respon.statusCode);
        
        AppendSegmentRequest areq = new AppendSegmentRequest();
        areq.setID("FOX1");
        areq.setName(req.name);
        areq.getName();
        areq.getID();
        areq =new AppendSegmentRequest(req.name, "FOX1");
        AppendSegmentHandler ahandler = new AppendSegmentHandler();
        AppendSegmentResponse ares = ahandler.handleRequest(areq, createContext("append"));
        AppendSegmentResponse decares = new AppendSegmentResponse ("error", 400);
        decares.toString();
        Assert.assertEquals(ares.response, req.name);
        
        ShowPlaylistHandler showHandler = new ShowPlaylistHandler();
        ShowPlaylistRequest showReq = new ShowPlaylistRequest();
        showReq.setName(req.name);
        ShowPlaylistResponse showRes = showHandler.handleRequest(showReq, createContext("show"));
        List<Segment> seglist = showRes.list;
        boolean showTest = false;
        for (Segment s: seglist) {
        	if (s.id.equals("FOX1")) {showTest = true;}
        	System.out.println(s.id);
        }
        Assert.assertTrue(showTest);
        
        RemoveSegmentRequest rreq = new RemoveSegmentRequest();
        rreq = new RemoveSegmentRequest("FOX1", req.name, 1);
        rreq.setID("FOX1");
        rreq.setName(req.name);
        rreq.getID();
        rreq.getName();
        rreq.getOrder();
        RemoveSegmentHandler rhandler = new RemoveSegmentHandler();
        RemoveSegmentResponse Rresponse = rhandler.handleRequest(rreq, createContext("remove"));
        
        RemoveSegmentResponse decRresponse = new RemoveSegmentResponse("error", 400);
        decRresponse.toString();
        
        Assert.assertEquals(200, Rresponse.httpCode);
        //
        ShowPlaylistHandler showHandler2 = new ShowPlaylistHandler();
        ShowPlaylistRequest showReq2 = new ShowPlaylistRequest(req.name);
        showReq2.setName(req.name);
        showReq2.getName();
        showReq2.toString();
        ShowPlaylistResponse showRes2 = showHandler.handleRequest(showReq2, createContext("show"));
        List<Segment> seglist2 = showRes2.list;
        boolean showTest2 = true;
        for (Segment s2: seglist2) {
        	if (s2.id.equals("FOX1")) {showTest2 = false;}
        	System.out.println(s2.id);
        }
        Assert.assertTrue(showTest2);
        
        //DeletePlaylist
        DeletePlaylistHandler dhandler = new DeletePlaylistHandler();
    	
    	DeletePlaylistRequest dreq = new DeletePlaylistRequest();
    	dreq.setName("RemTest");
    	dreq.getName();
    	dreq = new DeletePlaylistRequest("RemTest");
    	
    	DeletePlaylistResponse dresp = dhandler.handleRequest(dreq, createContext("deletePlaylist"));
        dresp.toString();
    	//Assert.assertEquals(dresp.statusCode, 200);
    	
		//List<Playlist> allPlaylists = dao.getAllPlaylists();
		
		
//    	RemoveSegmentHandler rhandler = new RemoveSegmentHandler();
//    	
//    	RemoveSegmentRequest requ = new RemoveSegmentRequest("Dino", "KHAN1", 2);
//    	RemoveSegmentResponse response = new RemoveSegmentResponse(req.name);
//    	
//    	Context c = createContext("removeSegment");
//    	RemoveSegmentResponse resp = handler.handleRequest(req, c);
//        Assert.assertEquals(resp, response);
    }
}
