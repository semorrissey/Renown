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
import renown.app.model.Playlistname;


public class CreatePlaylistHandler implements RequestHandler<CreatePlaylistRequest,CreatePlaylistResponse> {

	LambdaLogger logger;
	
	/** Store into RDS.
	 * 
	 * @throws Exception 
	 */
	boolean createPlaylist(String name) throws Exception { 
		if (logger != null) { logger.log("in createPlaylist"); }
		PlaylistsDAO dao = new PlaylistsDAO();
		
		// check if present
		Playlistname exist = dao.getPlaylistname(name);
		Playlistname playlist = new Playlistname(name);
		if (exist == null) {
			return dao.addPlaylist(playlist);
		} else {
			return false;
		}
	}
	
	@Override 
	public CreatePlaylistResponse handleRequest(CreatePlaylistRequest req, Context context)  {
		logger = context.getLogger();
		logger.log(req.toString());

		CreatePlaylistResponse response;
		try {
			if (createPlaylist(req.name)) {
				response = new CreatePlaylistResponse(req.name);
			} else {
				response = new CreatePlaylistResponse(req.name, 422);
			}
		} catch (Exception e) {
			response = new CreatePlaylistResponse("Unable to create playlist: " + req.name + "(" + e.getMessage() + ")", 400);
		}

		return response;
	}
}
