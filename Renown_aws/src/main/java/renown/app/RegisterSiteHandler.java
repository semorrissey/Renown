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

import renown.app.db.RemoteSitesDAO;
import renown.app.http.*;
import renown.app.model.RemoteSite;


public class RegisterSiteHandler implements RequestHandler<RegisterSiteRequest,RegisterSiteResponse> {

	LambdaLogger logger;
	
	/** Store into RDS.
	 * 
	 * @throws Exception 
	 */
	boolean registerSite(String site_name, String site_url) throws Exception { 
		if (logger != null) { logger.log("in registerSite"); }
		RemoteSitesDAO dao = new RemoteSitesDAO();
		
		// check if present
		RemoteSite exist = dao.getRemoteSite(site_url);
		RemoteSite remotesite = new RemoteSite(site_name, site_url);
		if (exist == null) {
			return dao.addRemoteSite(remotesite);
		} else {
			return false;
		}
	}
	
	@Override 
	public RegisterSiteResponse handleRequest(RegisterSiteRequest req, Context context)  {
		logger = context.getLogger();
		logger.log(req.toString());

		RegisterSiteResponse response;
		try {
			if (registerSite(req.site_name, req.site_url)) {
				response = new RegisterSiteResponse(req.site_name);
			} else {
				response = new RegisterSiteResponse(req.site_name, 422);
			}
		} catch (Exception e) {
			response = new RegisterSiteResponse("Unable to register site: " + req.site_name + "(" + e.getMessage() + ")", 400);
		}

		return response;
	}
}
