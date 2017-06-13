package ud.binmonkey.prog3_proyecto_client.https;

import org.json.JSONObject;
import ud.binmonkey.prog3_proyecto_client.common.Pair;
import ud.binmonkey.prog3_proyecto_client.common.network.URI;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.util.HashMap;

@SuppressWarnings({"WeakerAccess", "SameParameterValue"})
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
     */
    public Response login(String userName, String passWord) {
        try {
            return HTTPS.sendRequest("https://" + host, port, "/login", Methods.GET, null, null,
                    new Pair<>("username", userName), new Pair<>("password", passWord));
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Retrieve info of current session
     * @param userName username of session
     * @param token token of session
     * @return JSON object
     */
    public String sessionInfo(String userName, String token) {
        try {
            return HTTPS.sendRequest("https://" + host, port, "/sessionInfo", Methods.GET, null, null,
                    new Pair<>("username", userName), new Pair<>("token", token)).getContent();
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Request creation of account in server
     * @param userName new username
     * @param password new password
     * @param displayName new display name
     * @param email new email
     * @param birthdate new birthdate
     * @param gender new gender
     * @param preferred_lang new preferred lang
     * @return 200 if OK
     */
    public Response signUp(String userName, String password, String displayName, String email, String birthdate,
                         String gender, String preferred_lang) {
        HashMap<String, String> args = new HashMap<String, String>() {{
            put("username", userName);
            put("password", password);
            put("display_name", displayName);
            put("email", email);
            put("birth_date", birthdate);
            put("gender", gender);
            put("preferred_language", preferred_lang);
        }};

        for (String key: args.keySet()) {
            if (args.get(key) == null || args.keySet().equals("")) {
                args.remove(key);
            }
        }

        Pair[] pairs = new Pair[args.size()];

        int i = 0;
        for (String key: args.keySet()) {
            pairs[i] = new Pair(key, args.get(key));
            i++;
        }

        try {
            return HTTPS.sendRequest("https://" + host, port, "/signUp", Methods.POST,
                    null, null, pairs);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Request creation of account in server
     * @param userName new username
     * @param password new password
     * @param displayName new display name
     * @param email new email
     * @param birthdate new birthdate
     * @param gender new gender
     * @param preferred_lang new preferred lang
     * @return 200 if OK
     */
    public Response signUp(String userName, char[] password, String displayName, String email, String birthdate,
                         String gender, String preferred_lang) {
        return signUp(userName, new String(password), displayName, email, birthdate, gender, preferred_lang);
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

    /**
     * Change a property of an account
     * @param userName username of account
     * @param property property to be changed
     * @param value new value
     * @param token current session token
     * @return 200 if OK
     */
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

    /**
     * List directory of user
     * @param userName username of user
     * @param directory directory to list
     * @param token current session token
     * @return 200 if OK
     */
    @SuppressWarnings("unchecked")
    public Response listDir(String userName, String directory, String token) {
        Pair[] pairs;
        if (directory == null || directory.equals("")) {
            pairs = new Pair[] {new Pair("username", userName), new Pair("token", token)};
        } else {
            pairs = new Pair[] {new Pair("username", userName), new Pair("token", token),
                    new Pair("directory", directory)};
        }

        try {
            return HTTPS.sendRequest("https://" + host, port, "/listDir", Methods.GET,
                    null, null, pairs);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Parse response from @listDir to JSON Object
     * @param response response from @listDir
     * @return JSON Object
     */
    public static JSONObject parseJSONResponse(Response response) {
        if (response == null) {
            return null;
        }
        return new JSONObject(response.getContent());
    }

    /**
     * Request search for a Movie in OMDB
     * @param movieName movie to search
     * @param userName username of user that will request the search
     * @param token current session token
     * @return Response of server
     */
    public Response searchMovie(String movieName, String userName, String token) {
        Pair[] pairs = new Pair[] {
                new Pair("movieName", movieName),
                new Pair("user", userName),
                new Pair("token", token)
        };
        if (movieName != null && userName != null && token != null) {
            try {
                return HTTPS.sendRequest("https://" + host, port, "/searchMovie",
                        Methods.GET, null, null, pairs);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    public static void main(String[] args) {
        String userName = "test";
        String passWord = "test";
        HTTPSClient httpsClient = new HTTPSClient();
        String token = httpsClient.login(userName, passWord).getContent();
        if (token == null) {
            System.out.println("Login failed.");
        }
        System.out.println("Token: " + token);
        System.out.println(httpsClient.sessionInfo(userName, token));
        System.out.println(httpsClient.userInfo(userName, token));
        System.out.println(httpsClient.changeProperty(userName, "birth_date", "23-10-1990", token));
        System.out.println(httpsClient.userInfo(userName, token));
        System.out.println(httpsClient.parseJSONResponse(httpsClient.listDir(userName, null, token)));

        /* Uncomment the following lines to check user token expiration */
        /*
        try {
            Thread.sleep(6 * 60 * 1000);
        } catch (InterruptedException e) {}
        System.out.println(httpsClient.sessionInfo(userName, token));
        */
    }
}
