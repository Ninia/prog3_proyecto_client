package ud.binmonkey.prog3_proyecto_client.ftp;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import ud.binmonkey.prog3_proyecto_client.common.network.URI;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FTPlib {

    private static String host = URI.getHost("ftp");
    private static int port = URI.getPort("ftp");

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

    public static FTPClient logIn(String userName, String password) throws IOException {
        FTPClient client = new FTPClient();
        client.connect(host, port);
        client.login(userName, password);
        return client;
    }

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

    public static void delete(String username, String password, String file) throws IOException {
        FTPClient client = logIn(username, password);
        try {
            client.deleteFile(file);
        } catch (IOException e) {
            client.removeDirectory(file);
        }
    }

    public static void mkdir(String username, String password, String dirname) throws IOException {
        FTPClient client = logIn(username, password);
        client.makeDirectory(dirname);
    }

    public static void main(String[] args) {
        try {
            uploadFile("test", "test", "src/test/resources/ftpd/.ignore", "hi/there");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
