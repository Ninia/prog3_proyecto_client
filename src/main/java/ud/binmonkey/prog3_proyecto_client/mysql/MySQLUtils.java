package ud.binmonkey.prog3_proyecto_client.mysql;

import ud.binmonkey.prog3_proyecto_client.gui.MainWindow;
import ud.binmonkey.prog3_proyecto_client.neo4j.Neo4jUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MySQLUtils extends MySQL {

    public int dwhCheckRating(String id) {
        try {

            ResultSet resultSet = getStatement().executeQuery(
                    "SELECT RATING FROM user_ratings WHERE USER = '" + MainWindow.INSTANCE.getFrame().getUser() +
                            "' AND OMDBID = '" + id + "';");

            if (resultSet.next()) {
                return resultSet.getInt("RATING");
            } else {
                return 2;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 2;
    }

    public void dwhAddRating(String id, int value) {
        try {
            System.out.println();
            if (dwhCheckRating(id) == 2) {
                getStatement().executeUpdate("INSERT INTO user_ratings " +
                        " VALUES (DEFAULT, '" + MainWindow.INSTANCE.getFrame().getUser() + "'," +
                        " '" + id + "', '" + value + "' , CURRENT_TIMESTAMP)");
            } else {
                getStatement().executeUpdate("UPDATE user_ratings " +
                        " SET RATING='" + value + "'" +
                        " WHERE OMDBID = '" + id + "' AND USER = '" + MainWindow.INSTANCE.getFrame().getUser() + "';");
            }
        } catch (SQLException e) {
            e.printStackTrace(); /* TODO add logger */
        }
    }

    public void dwhAddView(String id) {
        try {
            getStatement().executeUpdate("INSERT INTO user_viewing_history " +
                    " VALUES (DEFAULT, '" + MainWindow.INSTANCE.getFrame().getUser() + "'," +
                    " '" + id + "', CURRENT_TIMESTAMP)");
        } catch (SQLException e) {
            e.printStackTrace(); /* TODO add logger */
        }
    }

    public ArrayList<String> getTitleList(String table) {
        try {

            ArrayList<String> titles = new ArrayList<>();

            ResultSet resultSet = getStatement().executeQuery("SELECT DISTINCT OMDBID FROM " + table + ";");

            while (resultSet.next()) {
                titles.add(resultSet.getString("OMDBID"));
            }

            return titles;

        } catch (SQLException e) {
            e.printStackTrace(); /* TODO add logger */
        }

        return null;
    }

    public ArrayList<String> getUserTitleList(String table) {

        ArrayList<String> watchedTitles = new ArrayList<>();

        try {
            ResultSet resultSet = getStatement().executeQuery("" +
                    "SELECT DISTINCT OMDBID FROM " + table +
                    " WHERE USER='" + MainWindow.INSTANCE.getFrame().getUser() + "';");

            while (resultSet.next()) {
                watchedTitles.add(resultSet.getString("OMDBID"));
            }

        } catch (SQLException e) {
            e.printStackTrace(); /* TODO add logger */
        }

        return watchedTitles;
    }

    public int getTimeWatched() {

        Neo4jUtils neo4j = new Neo4jUtils();
        int timeWatched = 0;

        for (String id : getUserTitleList("user_viewing_history")) {
            timeWatched += Integer.parseInt(neo4j.getAttribute(id, "runtime"));
        }

        return timeWatched;
    }

    public String getMostWatchedGenre() {

        Neo4jUtils neo4jUtils = new Neo4jUtils();

        ArrayList<String> genres = new ArrayList<>();

        for (String id : getUserTitleList("user_viewing_history")) {
            genres = neo4jUtils.getGenres(id, genres);
        }

        Map<String, Integer> wordCount = new HashMap<>();

        for (String word : genres) {
            Integer count = wordCount.get(word);
            wordCount.put(word, (count == null) ? 1 : count + 1);
        }

        Map.Entry<String, Integer> maxEntry = null;

        for (Map.Entry<String, Integer> entry : wordCount.entrySet()) {
            if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
                maxEntry = entry;
            }
        }

        return maxEntry.getKey();
    }

    public int getUserRatings(String id) {
        try {
            ResultSet resultSet = getStatement().executeQuery(
                    "SELECT DISTINCT (SUM(RATING)/COUNT(USER)) * 100 AS RATING " +
                            "FROM user_ratings WHERE OMDBID LIKE '" + id + "';");

            if (resultSet.next()) {
                return resultSet.getInt("RATING");
            }
        } catch (SQLException e) {
            e.printStackTrace(); /* TODO add logger */
        }
        return 0;
    }

    public int getTimesWatched(String id) {
        try {
            ResultSet resultSet = getStatement().executeQuery(
                    "SELECT DISTINCT COUNT(USER) AS TIMES_WATCHED " +
                            "FROM user_viewing_history WHERE OMDBID LIKE '" + id + "';");

            if (resultSet.next()) {
                return resultSet.getInt("TIMES_WATCHED");
            }
        } catch (SQLException e) {
            e.printStackTrace(); /* TODO add logger */
        }
        return 0;
    }

    public int getAverageRating() {
        try {
            ResultSet resultSet = getStatement().executeQuery(
                    "SELECT DISTINCT (SUM(RATING)/COUNT(USER)) * 100 AS RATING " +
                            "FROM user_ratings WHERE USER = '"
                            + MainWindow.INSTANCE.getFrame().getUser() + "';");

            if (resultSet.next()) {
                return resultSet.getInt("RATING");
            }
        } catch (SQLException e) {
            e.printStackTrace(); /* TODO add logger */
        }
        return 0;
    }
}
