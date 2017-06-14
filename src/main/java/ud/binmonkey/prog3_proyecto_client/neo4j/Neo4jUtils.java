package ud.binmonkey.prog3_proyecto_client.neo4j;


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

    public static void main(String[] args) {

    }

    public ArrayList<ArrayList> getTitleList(ArrayList<ArrayList> titleList, String type,
                                             String condition, String value) {
        StatementResult result;

        if (value.replace(" ", "").equals("")) {
            value = ".* *.";
        }

        switch (condition.toLowerCase()) { /* TODO add wildcards */
            case "title":
                result = getSession().run("MATCH (n:" + type + ") " +
                        "WHERE n.title =~ '" + value + "'" +
                        "RETURN n.name as id, n.title AS title, n.year AS year, n.poster as poster");
                break;
            case "year":
                result = getSession().run("MATCH (n:" + type + ") " +
                        "WHERE n.year =~ '" + value + "'" +
                        "RETURN n.name as id, n.title AS title, n.year AS year, n.poster as poster");
                break;
            case "genre":
                result = getSession().run("MATCH p=(n:Genre)-[r:GENRE]->(m:" + type + ")" +
                        "WHERE n.name =~ '" + value + "' " +
                        "RETURN m.name as id, m.title AS title, m.year AS year, m.poster as poster");
                break;
            default:
                result = getSession().run("MATCH p=(n:Person)-[r]->(m:" + type + ") " +
                        "WHERE n.name =~ '" + value + "' " +
                        "RETURN m.name as id, m.title AS title, m.year AS year, m.poster as poster");
                break;
        }

        LOG.log(Level.INFO, "Searched " + type + " by " + condition + ": " + value);

        while (result.hasNext()) {
            Record record = result.next();

            ArrayList<String> title = new ArrayList<>();

            title.add(record.get("id").toString().replaceAll("\"", "")); /* Replaces quotation marks */
            title.add(record.get("poster").toString().replaceAll("\"", ""));
            title.add(record.get("title").toString().replaceAll("\"", ""));

            titleList.add(title);
        }

        return titleList;
    }

    public ArrayList<ArrayList> getTitles(MediaType mediaType, String condition, String value) {

        ArrayList<ArrayList> titleList = new ArrayList<>();

        switch (mediaType) {
            case MOVIE:
                getTitleList(titleList, "Movie", condition, value);
                break;
            case SERIES:
                getTitleList(titleList, "Series", condition, value);
                break;
            case ALL:
                getTitleList(titleList, "Movie", condition, value);
                getTitleList(titleList, "Series", condition, value);
                break;
        }
        return titleList;
    }
}

