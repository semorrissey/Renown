package renown.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.*;
import renown.app.db.SegmentsDAO;
import renown.app.http.*;
import renown.app.model.Segment;

public class ShowPlaylistHandler implements RequestHandler<ShowPlaylistRequest,ShowPlaylistResponse> {

	public LambdaLogger logger;

	/** Load from RDS, if it exists
	 * 
	 * @throws Exception 
	 */
	List<Segment> getPlaylistInfo(String name) throws Exception {
		logger.log("in getPlaylistInfo");
		SegmentsDAO dao = new SegmentsDAO();
		
		return dao.showPlaylist(name);
	}
	
	@Override
	public ShowPlaylistResponse handleRequest(ShowPlaylistRequest req, Context context)  {
		logger = context.getLogger();
		logger.log("Loading Java Lambda handler to list all segments");

		ShowPlaylistResponse response;
		try {
			List<Segment> list = getPlaylistInfo(req.name);
			ArrayList<Segment> segList = new ArrayList<>();
			for (Segment s : list) {
					segList.add(s);
			}
			
			response = new ShowPlaylistResponse(segList, 200);
		} catch (Exception e) {
			response = new ShowPlaylistResponse(400, e.getMessage());
		}
		
		return response;
	}
}
