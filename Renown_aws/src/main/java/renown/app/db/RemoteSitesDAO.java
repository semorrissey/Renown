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
	
	public RemoteSite getRemoteSite(String site_url) throws Exception {
        
        try {
            RemoteSite remotesite = null;
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM sites WHERE site_url=?;");
            ps.setString(1,  site_url);
            ResultSet resultSet = ps.executeQuery();
            
            while (resultSet.next()) {
                remotesite = generateRemoteSite(resultSet);
            }
            resultSet.close();
            ps.close();
            
            return remotesite;

        } catch (Exception e) {
        	e.printStackTrace();
            throw new Exception("Failed in getting remote site: " + e.getMessage());
        }
    }
	
	public boolean addRemoteSite(RemoteSite remotesite) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM sites WHERE site_url = ?;");
            ps.setString(1, remotesite.site_url);
            ResultSet resultSet = ps.executeQuery();
            
            // already present?
            while (resultSet.next()) {
                RemoteSite rs = generateRemoteSite(resultSet);
                resultSet.close();
                return false;
            }

            ps = conn.prepareStatement("INSERT INTO sites (site_name,site_url) values(?,?);");
            ps.setString(1, remotesite.site_name);
            ps.setString(2, remotesite.site_url);
            ps.execute();
            return true;

        } catch (Exception e) {
            throw new Exception("Failed to insert remote site: " + e.getMessage());
        }
    }
	
	private RemoteSite generateRemoteSite(ResultSet resultSet) throws Exception {
		String site_name  = resultSet.getString("site_name");
		String site_url = resultSet.getString("site_url");
		return new RemoteSite(site_name, site_url);
	}
}