package ud.binmonkey.prog3_proyecto_client.https;

import ud.binmonkey.prog3_proyecto_client.common.Pair;
import ud.binmonkey.prog3_proyecto_client.common.network.URI;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.util.ArrayList;

public class HTTPSClient {
    private String host = URI.getHost("https-client");
    private int port = URI.getPort("https-client");

    static {
        HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> hostname.equals("localhost"));
    }

    public HTTPSClient() {
    }

    /**
     *
     * @param userName username
     * @param passWord password of user with username
     * @return token if succeeded, null if it did not
     * @throws IOException login failed
     */
    public String login(String userName, String passWord) {
        ArrayList<Pair> args = new ArrayList<>();
        try {
            return HTTPS.sendRequest("https://" + host, port, "/login", Methods.POST, null, null,
                    new Pair<>("username", userName), new Pair<>("password", passWord)).getContent();
        } catch (IOException e) {
            return null;
        }
    }

    public String sessionInfo(String userName, String token) {
        try {
            return HTTPS.sendRequest("https://" + host, port, "/sessionInfo", Methods.GET, null, null,
                    new Pair<>("username", userName), new Pair<>("token", token)).getContent();
        } catch (IOException e) {
            return null;
        }
    }

    public String userInfo(String userName, String token) {
        try {
            return HTTPS.sendRequest("https://" + host, port, "/userInfo", Methods.GET, null, null,
                    new Pair<>("username", userName), new Pair<>("token", token)).getContent();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String changeProperty(String userName, String property, String value, String token) {
        try {
            return HTTPS.sendRequest("https://" + host, port, "/changeProperty", Methods.GET,
                    null, null,
                    new Pair<>("username", userName), new Pair<>("token", token),
                    new Pair<>("property", property), new Pair<>("value", value)).getContent();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static void main(String[] args) {
        String userName = "WonderWoman";
        String passWord = "MYHMJDG4";
        HTTPSClient httpsClient = new HTTPSClient();
        String token = httpsClient.login(userName, passWord);
        if (token == null) {
            System.out.println("Login failed.");
        }
        System.out.println("Token: " + token);
        System.out.println(httpsClient.sessionInfo(userName, token));
        System.out.println(httpsClient.userInfo(userName, token));
        System.out.println(httpsClient.changeProperty(userName, "birth_date", "23-10-1990", token));
        System.out.println(httpsClient.userInfo(userName, token));

        /* Uncomment the following lines to check user token expiration */
        /*
        try {
            Thread.sleep(6 * 60 * 1000);
        } catch (InterruptedException e) {}
        System.out.println(httpsClient.sessionInfo(userName, token));
        */
    }
}
