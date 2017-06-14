package ud.binmonkey.prog3_proyecto_client.ftp;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import ud.binmonkey.prog3_proyecto_client.common.MovieName;
import ud.binmonkey.prog3_proyecto_client.common.network.URI;

import java.io.*;

public class FTPlib {

    private static String host = URI.getHost("ftp");
    private static int port = URI.getPort("ftp");

    /**
     * Upload a file to the FTP server
     * @param userName username of user who will upload the file
     * @param password password of user
     * @param file path to file that will be uploaded
     * @param targetDirectory server directory in which the file will be stored
     * @throws IOException FTP or connection error
     */
    public static void uploadFile(String userName, String password, String file, String targetDirectory) throws IOException {

        if (targetDirectory == null) {
            targetDirectory = "";
        }
        if (!targetDirectory.endsWith("/")) {
            targetDirectory += "/";
        }

        FTPClient client = logIn(userName, password);

        for (String dir: targetDirectory.split("/")) {
            client.makeDirectory(dir);
            client.changeWorkingDirectory(dir);
        }

        client.setFileType(FTP.BINARY_FILE_TYPE);
        client.enterLocalPassiveMode();

        InputStream is = new FileInputStream(new File(file));

        String[] fileComponents = file.split("/");
        file = fileComponents[fileComponents.length - 1];

        client.storeFile(file, is);
    }

    /**
     * ACCESS IS PRIVATE SO ALL FTP CALLS ARE DEFINED IN THIS CLASS AND CALLED FROM WHEREVER
     * Returns logged in client.
     *
     * @param userName username to log in with
     * @param password password
     * @return logged in @FTPClient if success, null if false
     * @throws IOException FTP or connection error
     */
    private static FTPClient logIn(String userName, String password) throws IOException {
        FTPClient client = new FTPClient();
        client.connect(host, port);
        client.login(userName, password);
        return client;
    }

    /**
     * Rename a file on the FTP server
     * @param username username of the user that will rename the file
     * @param password password of user
     * @param oldFile old name of file
     * @param newFile new name of file
     * @param sameDir if true the file will change name but not directory
     * @throws IOException FTP or connection error
     */
    @SuppressWarnings("SameParameterValue") /* sameDir is expected to eventually be false */
    public static void rename(String username, String password, String oldFile, String newFile, boolean sameDir)
            throws IOException {

        FTPClient client = logIn(username, password);

        String newDir = "";
        if (sameDir) {
            String[] oldDir = oldFile.split("/");
            oldDir[oldDir.length - 1] = null;
            for (String dir: oldDir) {
                if (dir != null) {
                    newDir += dir + "/";
                }
            }
        }
        newFile = newDir + newFile;

        client.rename(oldFile, newFile);
    }

    /**
     * Delete a file or directory  in the FTP server
     * @param username username of the user that will delete the file or directory
     * @param password password of user
     * @param file file to be deleted
     * @throws IOException FTP or connection error
     */
    public static void delete(String username, String password, String file) throws IOException {
        FTPClient client = logIn(username, password);
        client.deleteFile(file);
        client.removeDirectory(file);
    }

    /**
     * Create a new directory in the FTP server
     * @param username username of the user that will crate the directory
     * @param password password of user
     * @param dirname name of new directory
     * @throws IOException FTP or connection error
     */
    public static void mkdir(String username, String password, String dirname) throws IOException {
        FTPClient client = logIn(username, password);
        client.makeDirectory(dirname);
    }

    /**
     * Download a movie poster from the server
     * @param name name of movie
     * @param year year of release
     */
    public static void downloadFilmImage(String name, int year) throws IOException {
        downloadFilmImage(name, new Integer(year).toString());
    }

    /**
     * Download a movie poster from the server
     * @param name name of movie
     * @param year year of release
     */
    public static void downloadFilmImage(String name, String year) throws IOException {
        FTPClient client = logIn("common", "common");
        client.enterLocalPassiveMode();
        client.setFileType(FTP.BINARY_FILE_TYPE);

        String imagePath = "data/images/" + MovieName.formatMovie(name, year) + ".jpg";

        File file = new File(imagePath);
        file.delete();

        OutputStream os = new BufferedOutputStream(new FileOutputStream(imagePath));

        client.retrieveFile(imagePath, os);
        os.close();
    }

    public static void downloadFile(String path, String fileName, String userName, String password) throws IOException {
        FTPClient client = logIn(userName, password);
        client.enterLocalPassiveMode();
        client.setFileType(FTP.BINARY_FILE_TYPE);

        File file = new File("downloads/" + fileName);

        OutputStream os = new BufferedOutputStream(new FileOutputStream(file.getPath()));
        client.retrieveFile(path, os);

        os.close();
    }

    public static void main(String[] args) {
        try {
//            uploadFile("test", "test", "src/test/resources/ftpd/.ignore", "hi/there");
            downloadFilmImage("Victoria", 2015);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
