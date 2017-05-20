package ud.binmonkey.prog3_proyecto_client.https;

import ud.binmonkey.prog3_proyecto_client.common.InputStreamStringReader;
import ud.binmonkey.prog3_proyecto_client.common.Pair;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class HTTPS {

    /**
     * TODO: implement content
     * Send an HTTP request to an address
     * @param address address where the request will be sent
     * @param port port of the host
     * @param path path of the url
     * @param method HTTP method from enum @Methods
     * @param headers headers of HTTP request
     * @param content content of the request
     * @param args REST arguments
     * @return class @Response with HTTP response
     */
    public static Response sendRequest(String address, int port, String path, Methods method,
                                       HashMap<String, String> headers, String content, Pair... args)
            throws IOException {

        /* build request uri */
        StringBuilder url = new StringBuilder();
        url.append(address).append(":").append(port).append(path);

        /* append all args */
        if (args != null) {
            url.append("?");

            int argCount = 0;
            for (Pair arg: args) {
                if (argCount > 0) {
                    url.append("&");
                }
                argCount++;
                url.append(arg.getKey()).append("=").append(arg.getValue());
            }
        }

        /* initialize connection */
        HttpsURLConnection conn = (HttpsURLConnection) new URL(url.toString()).openConnection();

        /* set request method */
        conn.setRequestMethod(method.toString());

        /* append headers */
        if (headers != null) {

            for (String header: headers.keySet()) {
                ArrayList<String> headerList = new ArrayList<>();
                headerList.add(headers.get(header));
                conn.getHeaderFields().put(header, headerList);
            }
        }

        /* establish connection */
        conn.connect();

        /* build response */
        Response response = new Response();

        response.setCode(conn.getResponseCode());

        HashMap<String, String> responseHeaders = new HashMap<>();
        for (String h: conn.getHeaderFields().keySet()) {
            responseHeaders.put(h, conn.getHeaderField(h));
        }

        response.setHeaders(responseHeaders);
        response.setContent(InputStreamStringReader.readInputStream((InputStream) conn.getContent()));

        return response;
    }

    /**
     * TODO: implement content
     * Send an HTTP request to an address
     * @param address address where the request will be sent
     * @param port port of the host
     * @param path path of the url
     * @param method HTTP method from enum @Methods
     * @param headers headers of HTTP request
     * @param content content of the request
     * @param args REST arguments
     * @return class @Response with HTTP response
     */
    public static Response sendRequest(String address, int port, String path, Methods method,
                                       HashMap<String, String> headers, String content, ArrayList<Pair> args) throws IOException {
        return sendRequest(address, port, path, method, headers, content, args.toArray(new Pair[args.size()]));
    }


}
