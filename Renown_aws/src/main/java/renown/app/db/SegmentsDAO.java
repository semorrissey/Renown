package renown.app.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import renown.app.model.*;

public class SegmentsDAO {

	java.sql.Connection conn;

	public SegmentsDAO() {
		try  {
			conn = DatabaseUtil.connect();
		} catch (Exception e) {
			conn = null;
		}
	}

	public List<Segment> getAllSegments() throws Exception {

		List<Segment> allSegments = new ArrayList<>();
		try {
			Statement statement = conn.createStatement();
			String query = "SELECT * FROM segments";
			ResultSet resultSet = statement.executeQuery(query);

			while (resultSet.next()) {
				Segment s = generateSegment(resultSet);
				allSegments.add(s);
			}
			resultSet.close();
			statement.close();
			return allSegments;

		} catch (Exception e) {
			throw new Exception("Failed in getting books: " + e.getMessage());
		}
	}
	
	public Segment getSegment(String seg_id) throws Exception {
        
        try {
            Segment segment = null;
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM segments WHERE seg_id=?;");
            ps.setString(1,  seg_id);
            ResultSet resultSet = ps.executeQuery();
            
            while (resultSet.next()) {
                segment = generateSegment(resultSet);
            }
            resultSet.close();
            ps.close();
            
            return segment;

        } catch (Exception e) {
        	e.printStackTrace();
            throw new Exception("Failed in getting segment: " + e.getMessage());
        }
    }
	
	public boolean addSegment(Segment segment) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM segments WHERE seg_id = ?;");
            ps.setString(1, segment.id);
            ResultSet resultSet = ps.executeQuery();
            
            // already present?
            while (resultSet.next()) {
                Segment s = generateSegment(resultSet);
                resultSet.close();
                return false;
            }

            ps = conn.prepareStatement("INSERT INTO segments (seg_id,segments.character,line,url,availableRemote) values(?,?,?,?,?);");
            ps.setString(1, segment.id);
            ps.setString(2, segment.character);
            ps.setString(3, segment.line);
            ps.setString(4, segment.url);
            ps.setInt(5, 1);
            ps.execute();
            return true;

        } catch (Exception e) {
            throw new Exception("Failed to insert segment: " + e.getMessage());
        }
    }
	
	public boolean deleteSegment(String seg_id) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM segments WHERE seg_id = ?;");
            ps.setString(1, seg_id);
            int numAffected = ps.executeUpdate();
            ps.close();
            
            return (numAffected == 1);

        } catch (Exception e) {
            throw new Exception("Failed to delete segment: " + e.getMessage());
        }
    }
	
	public List<Segment> showPlaylist(String name) throws Exception {

		List<Segment> playlistSegments = new ArrayList<>();
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM playlists NATURAL JOIN segments WHERE playlists.name = ? ORDER BY playlists.seg_order;");
            ps.setString(1, name);
            ResultSet resultSet = ps.executeQuery();

			while (resultSet.next()) {
				Segment s = generateSegment(resultSet);
				playlistSegments.add(s);
			}
			resultSet.close();
			return playlistSegments;

		} catch (Exception e) {
			throw new Exception("Failed in getting books: " + e.getMessage());
		}
	}
	
	public List<Segment> getSearchedSegments(String character, String line) throws Exception {

		List<Segment> searchedSegments = new ArrayList<>();
		try {
			// if the search is only by character
			if (character != "" && line == "") {
				PreparedStatement ps = conn.prepareStatement("SELECT * FROM segments WHERE segments.character=?;");
				ps.setString(1, character);
				ResultSet resultSet = ps.executeQuery();
				
				while (resultSet.next()) {
					Segment s = generateSegment(resultSet);
					searchedSegments.add(s);
				}
				resultSet.close();
				return searchedSegments;
			}
			
			// if the search is only by line
			else if (line != "" && character == "") {
				String new_line = "%" + line + "%";
				PreparedStatement ps = conn.prepareStatement("SELECT * FROM segments WHERE segments.line LIKE ?;");
				ps.setString(1, new_line);
				ResultSet resultSet = ps.executeQuery();
				
				while (resultSet.next()) {
					Segment s = generateSegment(resultSet);
					searchedSegments.add(s);
				}
				resultSet.close();
				return searchedSegments;
			}
			
			// if the search is by character and line
			else if (character != "" && line != "") {
				String new_line = "%" + line + "%";
				PreparedStatement ps = conn.prepareStatement("SELECT * FROM segments WHERE segments.character=? AND segments.line LIKE ?;");
				ps.setString(1, character);
				ps.setString(2, new_line);
				ResultSet resultSet = ps.executeQuery();
				
				while (resultSet.next()) {
					Segment s = generateSegment(resultSet);
					searchedSegments.add(s);
				}
				resultSet.close();
				return searchedSegments;
			}
			
			// if there is no search, list will be null;
			else {
				return searchedSegments;
			}

		} catch (Exception e) {
			throw new Exception("Failed in getting books: " + e.getMessage());
		}
	}

	private Segment generateSegment(ResultSet resultSet) throws Exception {
		String id  = resultSet.getString("seg_id");
		String character = resultSet.getString("character");
		String line  = resultSet.getString("line");
		String url = resultSet.getString("url");
		boolean availableRemote  = resultSet.getBoolean("availableRemote");
		return new Segment(id, character, line, url, availableRemote);
	}
}
