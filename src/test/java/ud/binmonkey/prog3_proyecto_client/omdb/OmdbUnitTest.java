package ud.binmonkey.prog3_proyecto_client.omdb;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class OmdbUnitTest {

    /**
     * Tests method Omdb.search()
     */
    @org.junit.Test
    public void testSearch() {

        HashMap search = Omdb.search("Trainspotting", MediaType.ALL);
        HashMap movie = (HashMap) search.get("tt0117951");

        assertEquals("movie", movie.get("Type"));
        assertEquals("1996", movie.get("Year"));
        assertEquals("Trainspotting", movie.get("Title"));
    }

    /**
     * Tests method Omdb.getTitle()
     */
    @org.junit.Test
    public void testTitle() {

        Map movie = Omdb.getTitle("tt0117951");

        /* Checks that the movie is the same as the one searched for */
        assertEquals("movie", movie.get("Type"));
        assertEquals("1996", movie.get("Year"));
        assertEquals("Trainspotting", movie.get("Title"));

        /* Checks info about the movie */
        String actors = (String) movie.get("Actors");
        assertTrue(actors.contains("Ewan McGregor"));
        assertEquals("UK", movie.get("Country"));
    }

    /**
     * Tests method Omdb.getType()
     */
    @org.junit.Test
    public void testType() {

        MediaType mediaType = Omdb.getType("tt0117951");

        /* Checks that the movie is the same as the one searched for */
        assertEquals(MediaType.MOVIE, mediaType);
    }
}
