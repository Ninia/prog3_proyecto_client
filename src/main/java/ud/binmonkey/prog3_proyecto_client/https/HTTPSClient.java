package ud.binmonkey.prog3_proyecto_client.https;

import ud.binmonkey.prog3_proyecto_client.common.InputStreamStringReader;
import ud.binmonkey.prog3_proyecto_client.common.network.URI;

import javax.net.ssl.*;
import java.io.*;
import java.net.URL;
import java.security.*;
import java.security.cert.CertificateException;

public class HTTPSClient {
    private String host = URI.getHost("https-client");
    private int port = URI.getPort("https-client");

    static {
        HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> hostname.equals("localhost"));
    }

    public HTTPSClient() {
    }

    public SSLContext createSSLContext() {
        try {
            KeyStore ks = KeyStore.getInstance("JKS");
            ks.load(new FileInputStream("src/test/resources/keys/keystore.jks"), "changeit".toCharArray());

            /* key manager */
            KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            kmf.init(ks, "changeit".toCharArray());
            KeyManager[] kms = kmf.getKeyManagers();

            /* trust manager */
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tmf.init(ks);
            TrustManager[] tms = tmf.getTrustManagers();

            SSLContext sslContext = SSLContext.getInstance("TLSv1");
            sslContext.init(kms, tms, null);

            return sslContext;

        } catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException |
                UnrecoverableKeyException | KeyManagementException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @param userName username
     * @param passWord password of user with username
     * @return token if succeeded, null if it did not
     * @throws IOException
     */
    public String login(String userName, String passWord) throws IOException {
        SSLContext sslContext = this.createSSLContext();

        HttpsURLConnection conn = (HttpsURLConnection) new URL(
                "https://" + host + ":" + port + "/login?username=" + userName + "&password=" + passWord
        ).openConnection();
        conn.setRequestMethod("POST");
        conn.connect();
        try {
            return InputStreamStringReader.readInputStream((InputStream) conn.getContent());
        } catch (IOException e) {
            return null;
        }
    }


    public static void main(String[] args) {
        HTTPSClient httpsClient = new HTTPSClient();
        try {
            System.out.println(httpsClient.login("WonderWoman", "MYHMJDG4"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
