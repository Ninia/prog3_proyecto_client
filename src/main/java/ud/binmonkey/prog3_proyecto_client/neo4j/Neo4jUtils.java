package ud.binmonkey.prog3_proyecto_client.neo4j;


import org.apache.commons.lang3.StringUtils;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.StatementResult;
import ud.binmonkey.prog3_proyecto_client.common.time.DateUtils;
import ud.binmonkey.prog3_proyecto_client.omdb.MediaType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Level;

public class Neo4jUtils extends Neo4j {

    /* Logger for Neo4jUtils */
    private static final java.util.logging.Logger LOG = java.util.logging.Logger.getLogger(Neo4jUtils.class.getName());

    static {
        try {
            LOG.addHandler(new FileHandler(
                    "logs/" + Neo4jUtils.class.getName() + "." +
                            DateUtils.currentFormattedDate() + ".log.xml", true));
        } catch (SecurityException | IOException e) {
            LOG.log(Level.SEVERE, "Unable to create log file.");
        }
    }

    public ArrayList<ArrayList> getTitleList(ArrayList<ArrayList> titleList, String type,
                                             String condition, String value, String orderBy) {
        StatementResult result;

        if (value.replace(" ", "").equals("")) {
            value = ".* *.";
        }

        switch (orderBy.toLowerCase()) {
            case "title":
                orderBy = "n.title, n.year, n.imdbRating";
                break;
            case "year":
                orderBy = "n.year, n.title, n.imdbRating";
                break;
            case "rating":
                orderBy = "n.imdbRating DESCENDING, n.year, n.title";
                break;
        }

        switch (condition.toLowerCase()) { /* TODO add wildcards */
            case "genre":
                result = getSession().run("MATCH p=(m:Genre)-[r:GENRE]->(n:" + type + ")" +
                        "WHERE m.name =~ '" + value + "' " +
                        "RETURN n.name as id, n.title AS title, n.poster as poster " +
                        "ORDER BY " + orderBy);
                break;
            case "person":
                result = getSession().run("MATCH p=(m:Person)-[r]->(n:" + type + ") " +
                        "WHERE m.name =~ '" + value + "' " +
                        "RETURN n.name as id, n.title AS title, n.poster as poster " +
                        "ORDER BY n." + orderBy);
                break;
            default:
                result = getSession().run("MATCH (n:" + type + ") " +
                        "WHERE n." + condition.toLowerCase() + " =~ '" + value + "'" +
                        "RETURN n.name as id, n.title AS title, n.poster as poster " +
                        "ORDER BY " + orderBy);
                break;
        }

        LOG.log(Level.INFO, "Searched " + type + " by " + condition + ": " + value);

        while (result.hasNext()) {
            Record record = result.next();

            ArrayList<String> title = new ArrayList<>();

            title.add(record.get("id").toString().replaceAll("\"", "")); /* Replaces quotation marks */
            title.add(record.get("title").toString().replaceAll("\"", ""));
            title.add(record.get("poster").toString().replaceAll("\"", ""));

            if (!titleList.contains(title)) /* Fixes duplicate if person has two roles in the same movie */
                titleList.add(title);
        }

        return titleList;
    }

    public ArrayList<ArrayList> getTitles(MediaType mediaType, String condition, String value, String orderBy) {

        ArrayList<ArrayList> titleList = new ArrayList<>();

        switch (mediaType) {
            case MOVIE:
                getTitleList(titleList, "Movie", condition, value, orderBy);
                break;
            case SERIES:
                getTitleList(titleList, "Series", condition, value, orderBy);
                break;
            case ALL:
                getTitleList(titleList, "Movie", condition, value, orderBy);
                getTitleList(titleList, "Series", condition, value, orderBy);
                break;
        }
        return titleList;
    }

    /**
     * Given the id of a Title return its MediaType
     *
     * @param id - id of the OmdbTitle
     * @return Mediatype of the OmdbTitle
     */
    public MediaType getType(String id) {

        if (checkNode(id, "Movie")) {
            return MediaType.MOVIE;
        } else if (checkNode(id, "Series")) {
            return MediaType.SERIES;
        } else if (checkNode(id, "Episode")) {
            return MediaType.EPISODE;
        }
        return null;
    }

    /**
     * Given a OmdbTitle name and type returns an attribute
     *
     * @param id    - Name of the Node
     * @param type      - MediaType of the Title
     * @param attribute - Attribute to
     * @return Value of the attribute
     */
    public String getAttribute(String id, MediaType type, String attribute) {

        StatementResult result;

        result = getSession().run("MATCH (n:" + StringUtils.capitalize(type.toString()) + ") " +
                "WHERE n.name = '" + id + "' " +
                "RETURN n." + attribute + " as value");

        if (result.hasNext()) {
            Record record = result.next();

            return record.get("value").toString().replaceAll("\"", "");
        }

        LOG.log(Level.SEVERE, " Attribute " + attribute + " from " + id + " not found");

        return null;
    }

    public String getRating(String id, MediaType type, String scoreOutlet) {

        StatementResult result;

        result = getSession().run(
                "MATCH p=(n:ScoreOutlet)-[r:SCORED]->(m:" + StringUtils.capitalize(type.toString()) + ") " +
                        "WHERE n.name='" + scoreOutlet + "' AND m.name='" + id + "' " +
                        "RETURN r.score as score");

        if (result.hasNext()) {
            Record record = result.next();

            return record.get("score").toString().replaceAll("\"", "");
        }

        LOG.log(Level.SEVERE, "Score for " + id + " from " + scoreOutlet + " not found");

        return null;
    }

    public String getFilename(String id, MediaType type, String language) {

        StatementResult result;

        result = getSession().run(
                "MATCH p=(n:Language)-[r:SPOKEN_LANGUAGE]->(m:" + StringUtils.capitalize(type.toString()) + ") " +
                        "WHERE n.name='" + language + "' AND m.name='" + id + "' " +
                        "RETURN r.filename as filename");

        if (result.hasNext()) {
            Record record = result.next();

            return record.get("filename").toString().replaceAll("\"", "");
        }

        LOG.log(Level.SEVERE, "Filename for " + id + " in " + language + " not found");

        return "";
    }
}

