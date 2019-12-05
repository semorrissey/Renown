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
import renown.app.model.Playlistname;


public class AppendSegmentHandler implements RequestHandler<AppendSegmentRequest,AppendSegmentResponse> {

	LambdaLogger logger;
	
	/** Store into RDS.
	 * 
	 * @throws Exception 
	 */
	boolean appendSegment(String name, String seg_id) throws Exception { 
		if (logger != null) { logger.log("in appendSegment"); }
		PlaylistsDAO dao = new PlaylistsDAO();
		
		int order = dao.getNumSegments(name);
		Playlist playlist = new Playlist(name, seg_id, order);
		return dao.appendSegment(playlist);
	}

	
	@Override 
	public AppendSegmentResponse handleRequest(AppendSegmentRequest req, Context context)  {
		logger = context.getLogger();
		logger.log(req.toString());

		AppendSegmentResponse response;
		try {
			if (appendSegment(req.name, req.seg_id)) {
				response = new AppendSegmentResponse(req.name);
			} else {
				response = new AppendSegmentResponse(req.name, 422);
			}
		} catch (Exception e) {
			response = new AppendSegmentResponse("Unable to append segment: " + req.seg_id + "to " + req.name + "(" + e.getMessage() + ")", 400);
		}

		return response;
	}
}
