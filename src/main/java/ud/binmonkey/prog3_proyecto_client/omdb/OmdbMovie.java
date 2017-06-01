package ud.binmonkey.prog3_proyecto_client.omdb;

import org.json.JSONObject;
import ud.binmonkey.prog3_proyecto_client.common.time.DateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.neo4j.driver.v1.Values.parameters;

public class OmdbMovie extends OmdbTitle {

    private Date dvd;
    private double boxOffice; /* in Dollars */
    private String website;

    private HashMap ratings = new HashMap<String, Integer>();
    private ArrayList language;
    private ArrayList genre;
    private ArrayList writer;
    private ArrayList director;
    private ArrayList actors;
    private ArrayList producers;
    private ArrayList country;

    /**
     * Constructor for the class OmdbMovie that extends from OmdbTitle
     *
     * @param movie - Map with the info of the Episode
     */
    public OmdbMovie(Map movie) {
        super(movie);

        this.dvd = JSONFormatter.dateFormatter(movie.get("DVD"));
        this.boxOffice = JSONFormatter.doubleConversor(movie.get("BoxOffice"));
        this.website = (String) movie.get("Website");
        this.language = JSONFormatter.listFormatter(movie.get("Language"));
        this.genre = JSONFormatter.listFormatter(movie.get("Genre"));
        this.writer = JSONFormatter.listFormatter(movie.get("Writer"));
        this.director = JSONFormatter.listFormatter(movie.get("Director"));
        this.actors = JSONFormatter.listFormatter(movie.get("Actors"));
        this.producers = JSONFormatter.listFormatter(movie.get("Production"));
        this.country = JSONFormatter.listFormatter(movie.get("Country"));

        try {
            this.ratings = (HashMap) movie.get("Ratings");
        } catch (java.lang.ClassCastException e) { /* If it is not formatted */
            this.ratings = JSONFormatter.scoreFormatter((ArrayList) movie.get("Ratings"));
        }
    }

    /* Format Conversion Methods */

    public Object toParameters() {
        return parameters(
                "title", title,
                "name", imdbID,
                "year", year,
                "released", released.toString(),
                "dvd", dvd.toString(),
                "plot", plot,
                "awards", awards,
                "boxOffice", boxOffice,
                "metascore", metascore,
                "imdbRating", imdbRating,
                "imdbVotes", imdbVotes,
                "runtime", runtime,
                "website", website,
                "poster", poster);
    }

    /**
     * @return Return information in JSON format
     */
    public JSONObject toJSON() {

        JSONObject episodeJSON = super.toJSON();

        episodeJSON.put("DVD", DateUtils.dateFormatter(dvd));
        episodeJSON.put("BoxOffice", boxOffice);
        episodeJSON.put("Website", website);
        episodeJSON.put("Ratings", ratings);
        episodeJSON.put("Language", language);
        episodeJSON.put("Genre", genre);
        episodeJSON.put("Writer", writer);
        episodeJSON.put("Director", director);
        episodeJSON.put("Actors", actors);
        episodeJSON.put("Production", producers);
        episodeJSON.put("Country", country);

        return episodeJSON;
    }
    /* END Format Conversion Methods */

    /* Getters */

    public Enum getType() {
        return MediaType.MOVIE;
    }

    public HashMap getRatings() {
        return ratings;
    }

    public ArrayList getLanguage() {
        return language;
    }

    public ArrayList getGenre() {
        return genre;
    }

    public ArrayList getWriter() {
        return writer;
    }

    public ArrayList getDirector() {
        return director;
    }

    public ArrayList getActors() {
        return actors;
    }

    public ArrayList getProducers() {
        return producers;
    }

    public ArrayList getCountry() {
        return country;
    }
    /* END Getters*/
}
