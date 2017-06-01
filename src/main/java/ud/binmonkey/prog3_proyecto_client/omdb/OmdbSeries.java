package ud.binmonkey.prog3_proyecto_client.omdb;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

public class OmdbSeries extends OmdbTitle {

    private int seasons;

    private ArrayList language;
    private ArrayList genre;
    private ArrayList producers = new ArrayList();
    private ArrayList country;

    /**
     * Constructor for the class OMDBMovie that extends from OMDBTitle
     *
     * @param id - IMDB id of the Movie
     */
    public OmdbSeries(String id) {

        super(Omdb.getTitle(id));
        Map series = Omdb.getTitle(id);

        this.seasons = JSONFormatter.intergerConversor(series.get("totalSeasons"));
        this.language = JSONFormatter.listFormatter(series.get("Language"));
        this.genre = JSONFormatter.listFormatter(series.get("Genre"));
        this.producers.add("Placeholder"); /* TODO Placeholder */
        this.country = JSONFormatter.listFormatter(series.get("Country"));
    }

    public JSONObject toJSON() {

        JSONObject episodeJSON = super.toJSON();

        episodeJSON.put("seasons", seasons);

        episodeJSON.put("language", language);
        episodeJSON.put("genre", genre);
        episodeJSON.put("producers", producers);
        episodeJSON.put("country", country);

        return episodeJSON;
    }

    /* Getters */

    public ArrayList getLanguage() {
        return language;
    }

    public ArrayList getGenre() {
        return genre;
    }

    public ArrayList getProducers() {
        return producers;
    }

    public ArrayList getCountry() {
        return country;
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
                "\tLanguage=" + language + "\n" +
                "\tGenre=" + genre + "\n";
    }
}