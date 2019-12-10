package renown.app;

import java.io.ByteArrayInputStream;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;

import renown.app.db.PlaylistsDAO;
import renown.app.http.*;
import renown.app.model.Playlist;


public class RemoveSegmentHandler implements RequestHandler<RemoveSegmentRequest,RemoveSegmentResponse> {

	LambdaLogger logger;
	
	/** Store into RDS.
	 * 
	 * @throws Exception 
	 */
	boolean removeSegment(String name, String seg_id, int seg_order) throws Exception { 
		if (logger != null) { logger.log("in removeSegment"); }
		PlaylistsDAO dao = new PlaylistsDAO();
		
		Playlist playlist = new Playlist(name, seg_id, seg_order);
		return dao.removeSegment(playlist);
	}

	
	@Override 
	public RemoveSegmentResponse handleRequest(RemoveSegmentRequest req, Context context)  {
		logger = context.getLogger();
		logger.log(req.toString());

		RemoveSegmentResponse response;
		try {
			if (removeSegment(req.name, req.seg_id, req.seg_order)) {
				response = new RemoveSegmentResponse(req.name);
			} else {
				response = new RemoveSegmentResponse(req.name, 422);
			}
		} catch (Exception e) {
			response = new RemoveSegmentResponse("Unable to remove segment: " + req.seg_id + "from " + req.name + "in position " + (" + e.getMessage() + "), 400);
		}

		return response;
	}
}
