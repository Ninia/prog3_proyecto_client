package ud.binmonkey.prog3_proyecto_client.omdb;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

public class OmdbEpisode extends OmdbTitle {

    private String seriesID;
    private int season;
    private int episode;

    private ArrayList actors;
    private ArrayList writer;
    private ArrayList director;

    /**
     * Constructor for the class OMDBMovie that extends from OMDBTitle
     *
     * @param id - IMDB id of the Movie
     */
    public OmdbEpisode(String id) {

        super(Omdb.getTitle(id));
        Map series = Omdb.getTitle(id);

        this.seriesID = (String) series.get("seriesID");
        this.season = JSONFormatter.intergerConversor(series.get("Season"));
        this.episode = JSONFormatter.intergerConversor(series.get("Episode"));

        this.actors = JSONFormatter.listFormatter(series.get("Actors"));
        this.writer = JSONFormatter.listFormatter(series.get("Writer"));
        this.director = JSONFormatter.listFormatter(series.get("Director"));
    }

    public static void main(String[] args) {
        OmdbEpisode omdbEpisode = new OmdbEpisode("tt3097534");

        System.out.println(omdbEpisode.toJSON());
    }

    /* Getters */

    /**
     * Converts OmdbEpisode to JSON format
     *
     * @return JSONObject
     */
    public JSONObject toJSON() {

        JSONObject episodeJSON = super.toJSON();

        episodeJSON.put("seriesID", seriesID);
        episodeJSON.put("season", season);
        episodeJSON.put("episode", episode);

        episodeJSON.put("writer", writer);
        episodeJSON.put("director", director);
        episodeJSON.put("actors", actors);

        return episodeJSON;
    }

    /* Overridden Methods */
    @Override
    public String toString() {
        return "OmdbMovie:\n" +
                "\tTitle=" + title + "\n" +
                "\tIMDB ID=" + imdbID + "\n" +
                "\tYear=" + year + "\n" +
                "\tReleased=" + released + "\n" +
                "\tPlot=" + plot + "\n" +
                "\tRated=" + ageRating + "\n" +
                "\tAward=" + awards + "\n" +
                "\tMetascore=" + metascore + "\n" +
                "\tIMDB Rating=" + imdbRating + "\n" +
                "\tIMDB Votes=" + imdbVotes + "\n" +
                "\tRuntime=" + runtime + "\n" +
                "\tPoster=" + poster + "\n" +
                "\tGenre=" + writer + "\n";
    }
}
