package renown.app.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import renown.app.model.*;

public class PlaylistsDAO {
	
	java.sql.Connection conn;

	public PlaylistsDAO() {
		try  {
			conn = DatabaseUtil.connect();
		} catch (Exception e) {
			conn = null;
		}
	}
	
	public Playlistname getPlaylistname(String name) throws Exception {
        
        try {
            Playlistname playlistname = null;
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM playlistnames WHERE name=?;");
            ps.setString(1,  name);
            ResultSet resultSet = ps.executeQuery();
            
            while (resultSet.next()) {
                playlistname = generatePlaylistname(resultSet);
            }
            resultSet.close();
            ps.close();
            
            return playlistname;

        } catch (Exception e) {
        	e.printStackTrace();
            throw new Exception("Failed in getting playlistname: " + e.getMessage());
        }
    }
	
	public List<Playlistname> getAllPlaylistnames() throws Exception {

		List<Playlistname> allPlaylistnames = new ArrayList<>();
		try {
			Statement statement = conn.createStatement();
			String query = "SELECT * FROM playlistnames";
			ResultSet resultSet = statement.executeQuery(query);

			while (resultSet.next()) {
				Playlistname p = generatePlaylistname(resultSet);
				allPlaylistnames.add(p);
			}
			resultSet.close();
			statement.close();
			return allPlaylistnames;

		} catch (Exception e) {
			throw new Exception("Failed in getting books: " + e.getMessage());
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
	
	public boolean deletePlaylist(String name) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM playlists WHERE name = ?;");
            ps.setString(1, name);
            int numAffected = ps.executeUpdate();
            ps.close();
            
            PreparedStatement ps1 = conn.prepareStatement("DELETE FROM playlistnames WHERE name = ?;");
            ps1.setString(1, name);
            int numAffected1 = ps1.executeUpdate();
            ps1.close();
            
            return (numAffected == 1 && numAffected1 == 1);

        } catch (Exception e) {
            throw new Exception("Failed to delete playlist: " + e.getMessage());
        }
    }
	
    public boolean addPlaylist(Playlistname playlist) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM playlistnames WHERE name = ?;");
            ps.setString(1, playlist.name);
            ResultSet resultSet = ps.executeQuery();
            
            // already present?
            while (resultSet.next()) {
                Playlistname p = generatePlaylistname(resultSet);
                resultSet.close();
                return false;
            }

            ps = conn.prepareStatement("INSERT INTO playlistnames (name) values(?);");
            ps.setString(1,  playlist.name);
            ps.execute();
            return true;

        } catch (Exception e) {
            throw new Exception("Failed to insert constant: " + e.getMessage());
        }
    }
	
	private Playlist generatePlaylist(ResultSet resultSet) throws Exception {
		String name  = resultSet.getString("name");
		String seg_id  = resultSet.getString("seg_id");
		int seg_order = resultSet.getInt("seg_order");
		return new Playlist(name, seg_id, seg_order);
	}
	
	private Playlistname generatePlaylistname(ResultSet resultSet) throws Exception {
		String name  = resultSet.getString("name");
		return new Playlistname(name);
	}
}
