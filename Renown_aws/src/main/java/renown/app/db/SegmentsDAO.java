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

	private Segment generateSegment(ResultSet resultSet) throws Exception {
		String id  = resultSet.getString("id");
		String character = resultSet.getString("character");
		String line  = resultSet.getString("line");
		boolean availableRemote  = resultSet.getBoolean("availableRemote");
		return new Segment(id, character, line, availableRemote);
	}
}
