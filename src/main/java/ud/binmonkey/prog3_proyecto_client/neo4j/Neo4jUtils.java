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

    public ArrayList<ArrayList> getTitleList(ArrayList<ArrayList> titleList, String type) {

        StatementResult result = getSession().run("MATCH (n:" + type + ") " +
                "RETURN n.name as id, n.title AS title, n.year AS year, n.poster as poster");

        while (result.hasNext()) {
            Record record = result.next();

            ArrayList<String> title = new ArrayList<>();

            title.add(record.get("id").toString().replaceAll("\"", "")); /* Replaces quotation marks */
            title.add(record.get("poster").toString().replaceAll("\"", ""));
            title.add(record.get("title").toString().replaceAll("\"", ""));
            title.add(record.get("year").toString().replaceAll("\"", ""));


            titleList.add(title);
        }

        return titleList;
    }

    public ArrayList<ArrayList> getTitles(MediaType mediaType) {

        ArrayList<ArrayList> titleList = new ArrayList<>();

        switch (mediaType) {
            case MOVIE:
                getTitleList(titleList, "Movie");
                break;
            case SERIES:
                getTitleList(titleList, "Series");
                break;
            case ALL:
                getTitleList(titleList, "Movie");
                getTitleList(titleList, "Series");
                break;
        }
        return titleList;
    }
}

