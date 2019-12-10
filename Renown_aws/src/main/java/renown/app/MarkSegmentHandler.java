package renown.app;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import renown.app.db.SegmentsDAO;
import renown.app.http.*;

public class MarkSegmentHandler implements RequestHandler<MarkSegmentRequest,MarkSegmentResponse> {

	public LambdaLogger logger = null;

	@Override
	public MarkSegmentResponse handleRequest(MarkSegmentRequest req, Context context) {
		logger = context.getLogger();
		logger.log("Loading Java Lambda handler to mark segment");

		MarkSegmentResponse response = null;
		logger.log(req.toString());

		SegmentsDAO dao = new SegmentsDAO();

		try {
			if (dao.markSegment(req.seg_id)) {
				response = new MarkSegmentResponse(req.seg_id, 200);
			} else {
				response = new MarkSegmentResponse(req.seg_id, 422);
			}
		} catch (Exception e) {
			response = new MarkSegmentResponse(req.seg_id, 403);
		}

		return response;
	}
}
