package ud.binmonkey.prog3_proyecto_client.common;

public class MovieName {

    /**
     * Returns a string with $name($year) format
     * @param name name of movie
     * @param year year of release
     * @return String with $name($year) format
     */
    public static String formatMovie(String name, String year) {
        return name + "(" + year + ")";
    }

    /**
     * Returns a string with $name($year) format
     * @param name name of movie
     * @param year year of release
     * @return String with $name($year) format
     */
    public static String formatMovie(String name, int year) {
        return formatMovie(name, new Integer(year).toString());
    }

    /**
     * Returns only the name given a $name($year) formatted string
     * @param movieFileName $name($year) formatted string
     * @return name of movie
     */
    public static String getName(String movieFileName) {
        String fileName = "";
        String[] components = movieFileName.split("\\(");

        for (int i = 0; i < components.length - 1; i++) {
            fileName += components[i];
            if (i != components.length - 2) {
                fileName += ")";
            }
        }

        return fileName;
    }

    /**
     * Returns only the year given a $name($year) formatted string
     * @param movieFileName $name($year) formatted string
     * @return year of release
     */
    public static String getYear(String movieFileName) {
        String[] components = movieFileName.split("\\(");
        try {
            return components[components.length - 1].split("\\)")[0];
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    /**
     * Removes extension from filename
     * @param movieFileName name of file
     * @return name without extension
     */
    public static String removeExtension(String movieFileName) {
        String fileName = "";
        String[] components = movieFileName.split("\\.");

        for (int i = 0; i < components.length - 1; i++) {
            fileName += components[i];
            if (i != components.length - 2) {
                fileName += ".";
            }
        }

        return fileName;
    }

    /**
     * Checks if filename matches $name($year).$extension format
     * @param fileName filename to check
     * @return true if match, false if no match
     */
    public static boolean matchesMovie(String fileName) {
        return fileName.matches("[A-Za-z0-9]+\\(\\d{4}\\)\\.[A-Za-z0-9]+");
    }

    public static void main(String[] args) {
        String movie = "Victoria(2015).mp4";
        System.out.println(getName(movie));
        System.out.println(getYear(movie));
    }
}
