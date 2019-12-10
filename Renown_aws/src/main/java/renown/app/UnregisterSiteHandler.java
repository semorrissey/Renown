package renown.app;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import renown.app.db.RemoteSitesDAO;
import renown.app.http.*;

public class UnregisterSiteHandler implements RequestHandler<UnregisterSiteRequest,UnregisterSiteResponse> {

	public LambdaLogger logger = null;

	@Override
	public UnregisterSiteResponse handleRequest(UnregisterSiteRequest req, Context context) {
		logger = context.getLogger();
		logger.log("Loading Java Lambda handler to delete");

		UnregisterSiteResponse response = null;
		logger.log(req.toString());

		RemoteSitesDAO dao = new RemoteSitesDAO();

		try {
			if (dao.deleteRemoteSite(req.site_url)) {
				response = new UnregisterSiteResponse(req.site_url, 200);
			} else {
				response = new UnregisterSiteResponse(req.site_url, 422, "Unable to delete remote site.");
			}
		} catch (Exception e) {
			response = new UnregisterSiteResponse(req.site_url, 403, "Unable to delete remote site: " + req.site_url + "(" + e.getMessage() + ")");
		}

		return response;
	}
}
