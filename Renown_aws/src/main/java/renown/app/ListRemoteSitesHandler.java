package renown.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.*;

import renown.app.db.RemoteSitesDAO;
import renown.app.http.*;
import renown.app.model.RemoteSite;

public class ListRemoteSitesHandler implements RequestHandler<ListRemoteSitesRequest,ListRemoteSitesResponse> {

	public LambdaLogger logger;

	/** Load from RDS, if it exists
	 * 
	 * @throws Exception 
	 */
	List<RemoteSite> getRemoteSites() throws Exception {
		logger.log("in getRemoteSites");
		RemoteSitesDAO dao = new RemoteSitesDAO();
		
		return dao.getAllRemoteSites();
	}
	
	@Override
	public ListRemoteSitesResponse handleRequest(ListRemoteSitesRequest req, Context context)  {
		logger = context.getLogger();
		logger.log("Loading Java Lambda handler to list all segments");

		RemoteSitesDAO dao = new RemoteSitesDAO();
		
		Map<String,String> map = System.getenv();
		for (String k: map.keySet()) {
			logger.log(k + "=" + map.get(k));
		}
		ListRemoteSitesResponse response;
		try {
			List<RemoteSite> list = getRemoteSites();
			ArrayList<RemoteSite> siteList = new ArrayList<>();
			for (RemoteSite s : list) {
					siteList.add(s);
			}
			
			response = new ListRemoteSitesResponse(siteList, 200);
		} catch (Exception e) {
			response = new ListRemoteSitesResponse(400, e.getMessage());
		}
		
		return response;
	}
}
