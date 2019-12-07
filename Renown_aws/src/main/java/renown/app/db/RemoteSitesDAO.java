package renown.app.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import renown.app.model.*;

public class RemoteSitesDAO {

	java.sql.Connection conn;

	public RemoteSitesDAO() {
		try  {
			conn = DatabaseUtil.connect();
		} catch (Exception e) {
			conn = null;
		}
	}

	public List<RemoteSite> getAllRemoteSites() throws Exception {

		List<RemoteSite> allSites = new ArrayList<>();
		try {
			Statement statement = conn.createStatement();
			String query = "SELECT * FROM sites";
			ResultSet resultSet = statement.executeQuery(query);

			while (resultSet.next()) {
				RemoteSite s = generateRemoteSite(resultSet);
				allSites.add(s);
			}
			resultSet.close();
			statement.close();
			return allSites;

		} catch (Exception e) {
			throw new Exception("Failed in getting books: " + e.getMessage());
		}
	}
	
	private RemoteSite generateRemoteSite(ResultSet resultSet) throws Exception {
		String site_name  = resultSet.getString("site_name");
		String site_url = resultSet.getString("site_url");
		return new RemoteSite(site_name, site_url);
	}
}