package renown.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.*;
import renown.app.db.SegmentsDAO;
import renown.app.http.*;
import renown.app.model.Segment;

public class ListRDSSegmentsHandler implements RequestHandler<ListSegmentsRequest,AllSegmentsResponse> {

	public LambdaLogger logger;

	/** Load from RDS, if it exists
	 * 
	 * @throws Exception 
	 */
	List<Segment> getSegments() throws Exception {
		logger.log("in getSegments");
		SegmentsDAO dao = new SegmentsDAO();
		
		return dao.getAllSegments();
	}
	
	@Override
	public AllSegmentsResponse handleRequest(ListSegmentsRequest input, Context context)  {
		logger = context.getLogger();
		logger.log("Loading Java Lambda handler to list all segments");

		SegmentsDAO dao = new SegmentsDAO();
		
		Map<String,String> map = System.getenv();
		for (String k: map.keySet()) {
			logger.log(k + "=" + map.get(k));
		}
		AllSegmentsResponse response;
		try {
			List<Segment> list = getSegments();
			ArrayList<Segment> segList = new ArrayList<>();
			for (Segment s : list) {
					segList.add(s);
			}
			
			response = new AllSegmentsResponse(segList, 200);
		} catch (Exception e) {
			response = new AllSegmentsResponse(400, e.getMessage());
		}
		
		return response;
	}
}
