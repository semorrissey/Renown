package renown.app;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import renown.app.db.PlaylistsDAO;
import renown.app.http.*;

public class DeletePlaylistHandler implements RequestHandler<DeletePlaylistRequest,DeletePlaylistResponse> {

	public LambdaLogger logger = null;

	@Override
	public DeletePlaylistResponse handleRequest(DeletePlaylistRequest req, Context context) {
		logger = context.getLogger();
		logger.log("Loading Java Lambda handler to delete");

		DeletePlaylistResponse response = null;
		logger.log(req.toString());

		PlaylistsDAO dao = new PlaylistsDAO();

		try {
			if (dao.deletePlaylist(req.name)) {
				response = new DeletePlaylistResponse(req.name, 200);
			} else {
				response = new DeletePlaylistResponse(req.name, 422, "Unable to delete playlist.");
			}
		} catch (Exception e) {
			response = new DeletePlaylistResponse(req.name, 403, "Unable to delete playlist: " + req.name + "(" + e.getMessage() + ")");
		}

		return response;
	}
}
