package src.db;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import src.model.*;

public class PlaylistsDAO {
	
	java.sql.Connection conn;

	public PlaylistsDAO() {
		try  {
			conn = DatabaseUtil.connect();
		} catch (Exception e) {
			conn = null;
		}
	}
	
	public List<Playlist> getAllPlaylists() throws Exception {

		List<Playlist> allPlaylists = new ArrayList<>();
		try {
			Statement statement = conn.createStatement();
			String query = "SELECT * FROM playlists";
			ResultSet resultSet = statement.executeQuery(query);

			while (resultSet.next()) {
				Playlist p = generatePlaylist(resultSet);
				allPlaylists.add(p);
			}
			resultSet.close();
			statement.close();
			return allPlaylists;

		} catch (Exception e) {
			throw new Exception("Failed in getting books: " + e.getMessage());
		}
	}
	
	private Playlist generatePlaylist(ResultSet resultSet) throws Exception {
		String name  = resultSet.getString("name");
		String seg_id  = resultSet.getString("seg_id");
		int seg_order = resultSet.getInt("seg_order");
		return new Playlist(name, seg_id, seg_order);
	}
}
