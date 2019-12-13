package renown.app;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import renown.app.http.*;
import renown.app.model.RemoteSite;


public class ListRemotesTest extends LambdaTest{

	@Test
	public void testListRemotes() {
		String siteName = "test";
		String siteUrl = "test_url";
		RegisterSiteRequest regreq = new RegisterSiteRequest(siteName, siteUrl);
		regreq.setName(siteName);
		regreq.setUrl(siteUrl);
		regreq.getName();
		regreq.getUrl();
		RegisterSiteHandler reghandler = new RegisterSiteHandler();
		RegisterSiteResponse regresp = reghandler.handleRequest(regreq, createContext("register"));
		RegisterSiteResponse decregresp = new RegisterSiteResponse ("test", 400);
		decregresp.toString();
		Assert.assertEquals(siteName, regresp.response);
		
		ListRemoteSitesHandler handler = new ListRemoteSitesHandler();
		ListRemoteSitesRequest lrreq = new ListRemoteSitesRequest();
		lrreq.toString();
		ListRemoteSitesResponse resp = handler.handleRequest(null, createContext("list"));
		ListRemoteSitesResponse decresp = new ListRemoteSitesResponse (400, "error");
		decresp.toString();
		boolean test = false;
        for (RemoteSite s : resp.list) {
        	if (s.site_name.equals(siteName)) { test = true; break; }
		
        }
        Assert.assertTrue(test);
		Assert.assertEquals(200, resp.statusCode);
		
		UnregisterSiteRequest unregreq = new UnregisterSiteRequest(siteUrl);
		unregreq.setUrl(siteUrl);
		UnregisterSiteHandler Unreghandler = new UnregisterSiteHandler();
		UnregisterSiteResponse Unregresp = Unreghandler.handleRequest(unregreq, createContext("register"));
		Assert.assertEquals(Unregresp.httpCode,200);
		
		
		
		
}
}