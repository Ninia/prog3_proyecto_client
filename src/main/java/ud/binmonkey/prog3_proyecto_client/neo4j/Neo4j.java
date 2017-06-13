package ud.binmonkey.prog3_proyecto_client.neo4j;

import org.neo4j.driver.v1.*;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import ud.binmonkey.prog3_proyecto_client.common.DocumentReader;
import ud.binmonkey.prog3_proyecto_client.common.time.DateUtils;
import ud.binmonkey.prog3_proyecto_client.omdb.MediaType;

import java.io.IOException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Level;

public class Neo4j {

    /* Logger for Neo4j */
    private static final java.util.logging.Logger LOG = java.util.logging.Logger.getLogger(Neo4j.class.getName());

    static {
        try {
            LOG.addHandler(new FileHandler(
                    "logs/" + Neo4j.class.getName() + "." +
                            DateUtils.currentFormattedDate() + ".log.xml", true));
        } catch (SecurityException | IOException e) {
            LOG.log(Level.SEVERE, "Unable to create log file.");
        }
    }
    /* END Logger for Neo4j */

    private String username;
    private String password;
    private String server_address;
    private Driver driver;
    private Session session;

    private Statement statement;


    /**
     * Constructor for the class Neoj
     */
    public Neo4j() {
        readConfig();
        while (!startSession()) {
            LOG.log(Level.INFO, " Retrying Connection in 5s");
            try {
                Thread.sleep(5000);                 //1000 milliseconds is one second.
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) {
        Neo4j neo4j = new Neo4j();

        System.out.println(neo4j.getTitles(MediaType.ALL));
    }

    /* Server Utility Methods */
    private void readConfig() {

        NodeList nList = DocumentReader.getDoc("conf/Network.xml").getElementsByTagName("neo4j-server");
        Node nNode = nList.item(0);
        Element eElement = (Element) nNode;


        username = eElement.getElementsByTagName("username").item(0).getTextContent();
        password = eElement.getElementsByTagName("password").item(0).getTextContent();
        server_address = eElement.getElementsByTagName("server_address").item(0).getTextContent();
    }

    public Session getSession() {
        return session;
    }

    public boolean startSession() {

        try {
            driver = GraphDatabase.driver(server_address, AuthTokens.basic(username, password));
            session = driver.session();
            LOG.log(Level.INFO, "Connection to Neo4j server started");
            return true;
        } catch (org.neo4j.driver.v1.exceptions.ServiceUnavailableException e) {
            LOG.log(Level.SEVERE, "Unable to connect to server," +
                    " ensure the database is running and that there is a working network connection to it.");
            return false;
        } catch (org.neo4j.driver.v1.exceptions.AuthenticationException e) {
            LOG.log(Level.SEVERE, ": The client is unauthorized due to authentication failure.");
            System.exit(0);
        }

        return false;
    }
    /* END Server Utility Methods */

    public void closeSession() {
        session.close();
        driver.close();

        LOG.log(Level.INFO, "Connection to Neo4j server ended");
    }

    public ArrayList<ArrayList> getTitleList(ArrayList<ArrayList> titleList, String type) {

        StatementResult result = session.run("MATCH (n:" + type + ") " +
                "RETURN n.name as id, n.title AS title, n.year AS year, n.poster");

        while (result.hasNext()) {
            Record record = result.next();

            ArrayList<String> title = new ArrayList<>();

            title.add(record.get("id").toString().replaceAll("\"", "")); /* Replaces quotation marks */
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

