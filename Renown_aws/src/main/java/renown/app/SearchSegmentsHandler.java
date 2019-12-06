package renown.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import renown.app.db.SegmentsDAO;
import renown.app.http.*;
import renown.app.model.Segment;


public class SearchSegmentsHandler implements RequestHandler<SearchSegmentsRequest,SearchSegmentsResponse> {

	public LambdaLogger logger;

	/** Load from RDS, if it exists
	 * 
	 * @throws Exception 
	 */
	List<Segment> searchSegments(String character, String line) throws Exception {
		logger.log("in searchSegments");
		SegmentsDAO dao = new SegmentsDAO();
		
		return dao.getSearchedSegments(character, line);
	}
	
	@Override
	public SearchSegmentsResponse handleRequest(SearchSegmentsRequest req, Context context)  {
		logger = context.getLogger();
		logger.log("Loading Java Lambda handler to search all segments");

		Map<String,String> map = System.getenv();
		for (String k: map.keySet()) {
			logger.log(k + "=" + map.get(k));
		}
		SearchSegmentsResponse response;
		try {
			List<Segment> list = searchSegments(req.character, req.line);
			ArrayList<Segment> segList = new ArrayList<>();
			for (Segment s : list) {
					segList.add(s);
			}
			
			response = new SearchSegmentsResponse(segList, 200);
		} catch (Exception e) {
			response = new SearchSegmentsResponse(400, e.getMessage());
		}
		
		return response;
	}
}
