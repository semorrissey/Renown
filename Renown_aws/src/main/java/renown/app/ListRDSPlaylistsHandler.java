package renown.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.*;
import renown.app.db.PlaylistsDAO;
import renown.app.http.*;
import renown.app.model.Playlist;
import renown.app.model.Playlistname;

public class ListRDSPlaylistsHandler implements RequestHandler<ListPlaylistsRequest,AllPlaylistsResponse> {

	public LambdaLogger logger;

	/** Load from RDS, if it exists
	 * 
	 * @throws Exception 
	 */
	List<Playlistname> getPlaylists() throws Exception {
		logger.log("in getPlaylists");
		PlaylistsDAO dao = new PlaylistsDAO();
		
		return dao.getAllPlaylistnames();
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
			List<Playlistname> list = getPlaylists();
			ArrayList<Playlistname> segList = new ArrayList<>();
			for (Playlistname p : list) {
					segList.add(p);
			}
			
			response = new AllPlaylistsResponse(segList, 200);
		} catch (Exception e) {
			response = new AllPlaylistsResponse(400, e.getMessage());
		}
		
		return response;
	}
}
