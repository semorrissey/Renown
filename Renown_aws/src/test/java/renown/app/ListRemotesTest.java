package renown.app;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import renown.app.http.*;
import renown.app.model.RemoteSite;


public class ListRemotesTest extends LambdaTest{

	@Test
	public void testListRemotes() {
		ListRemoteSitesHandler handler = new ListRemoteSitesHandler();
		ListRemoteSitesResponse resp = handler.handleRequest(null, createContext("list"));
		
		boolean test = false;
        for (RemoteSite s : resp.list) {
        	if (s.site_name.equals("Test")) { test = true; break; }
		
        }
        Assert.assertTrue("site with site name 'Test' needs to be in database", test);
		Assert.assertEquals(200, resp.statusCode);
}
}