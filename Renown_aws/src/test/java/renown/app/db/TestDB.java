package renown.app.db;

import static org.junit.Assert.*;

import java.util.List;
//import java.util.UUID;

import org.junit.Test;

import renown.app.model.Segment;
import renown.app.model.Playlist;

public class TestDB {

	@Test
	public void testAllSegments() {
	    SegmentsDAO sd = new SegmentsDAO();
	    try {
	    	List<Segment> list = sd.getAllSegments();
			for (Segment s : list) {
				System.out.println("segment " + s.id);
			}
	    } catch (Exception e) {
	    	fail ("didn't work:" + e.getMessage());
	    }
	}
	
	@Test
	public void testAllPlaylists() {
	    PlaylistsDAO sd = new PlaylistsDAO();
	    try {
	    	List<Playlist> list = sd.getAllPlaylists();
			for (Playlist p : list) {
				System.out.println("playlist " + p.name);
			}
	    } catch (Exception e) {
	    	fail ("didn't work:" + e.getMessage());
	    }
	}
	
}