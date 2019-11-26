package renown.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.*;
import renown.app.db.PlaylistsDAO;
import renown.app.http.*;
import renown.app.model.Playlist;

public class ListRDSPlaylistsHandler implements RequestHandler<ListPlaylistsRequest,AllPlaylistsResponse> {

	public LambdaLogger logger;

	/** Load from RDS, if it exists
	 * 
	 * @throws Exception 
	 */
	List<Playlist> getPlaylists() throws Exception {
		logger.log("in getPlaylists");
		PlaylistsDAO dao = new PlaylistsDAO();
		
		return dao.getAllPlaylists();
	}
	
	@Override
	public AllPlaylistsResponse handleRequest(ListPlaylistsRequest input, Context context)  {
		logger = context.getLogger();
		logger.log("Loading Java Lambda handler to list all playlists");

		Map<String,String> map = System.getenv();
		for (String k: map.keySet()) {
			logger.log(k + "=" + map.get(k));
		}
		AllPlaylistsResponse response;
		try {
			List<Playlist> list = getPlaylists();
			ArrayList<Playlist> segList = new ArrayList<>();
			for (Playlist p : list) {
					segList.add(p);
			}
			
			response = new AllPlaylistsResponse(segList, 200);
		} catch (Exception e) {
			response = new AllPlaylistsResponse(400, e.getMessage());
		}
		
		return response;
	}
}
