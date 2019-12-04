package renown.app;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import renown.app.db.SegmentsDAO;
import renown.app.http.*;
import renown.app.model.Segment;

public class DeleteSegmentHandler implements RequestHandler<DeleteSegmentRequest,DeleteSegmentResponse> {

	public LambdaLogger logger = null;

	@Override
	public DeleteSegmentResponse handleRequest(DeleteSegmentRequest req, Context context) {
		logger = context.getLogger();
		logger.log("Loading Java Lambda handler to delete");

		DeleteSegmentResponse response = null;
		logger.log(req.toString());

		SegmentsDAO dao = new SegmentsDAO();

		try {
			if (dao.deleteSegment(req.seg_id)) {
				response = new DeleteSegmentResponse(req.seg_id, 200);
			} else {
				response = new DeleteSegmentResponse(req.seg_id, 422, "Unable to delete segment.");
			}
		} catch (Exception e) {
			response = new DeleteSegmentResponse(req.seg_id, 403, "Unable to delete segment: " + req.seg_id + "(" + e.getMessage() + ")");
		}

		return response;
	}
}
