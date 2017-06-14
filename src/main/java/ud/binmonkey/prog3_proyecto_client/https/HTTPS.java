package ud.binmonkey.prog3_proyecto_client.https;

import ud.binmonkey.prog3_proyecto_client.common.InputStreamStringReader;
import ud.binmonkey.prog3_proyecto_client.common.Pair;
import ud.binmonkey.prog3_proyecto_client.common.network.URLParamEncoder;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.InputStream;
import java.net.ProtocolException;
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
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(address).append(":").append(port).append(path);

        /* append all args */
        if (args != null) {
            urlBuilder.append("?");

            int argCount = 0;
            for (Pair arg: args) {
                if (argCount > 0) {
                    urlBuilder.append("&");
                }
                argCount++;
                urlBuilder.append(
                        URLParamEncoder.encode(arg.getKey().toString())
                ).append("=").append(
                        URLParamEncoder.encode(arg.getValue().toString())
                );
            }
        }

        String url = urlBuilder.toString();

        /* initialize connection */
        HttpsURLConnection conn = (HttpsURLConnection) new URL(url).openConnection();

        /* set request method */
        conn.setRequestMethod(method.toString());
        if (method == Methods.POST ) {
//            conn.setDoInput(false);
            conn.setDoOutput(true);
        }

        /* append headers */
        if (headers != null && headers.size() != 0) {

            for (String header: headers.keySet()) {
                ArrayList<String> headerList = new ArrayList<>();
                headerList.add(headers.get(header));
                try {
//                    conn.getHeaderFields().put(header, headerList);
                } catch (/* UnsupportedOperationException | SocketException */
                        Exception e ) {}
            }
        }

        if (content != null) {
            try {
                conn.getOutputStream().write(content.getBytes("UTF-8"));
                conn.getOutputStream().close();
            } catch (Exception e) {
                e.printStackTrace();
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
        try {
            conn.getOutputStream().close();
        } catch (ProtocolException e) {}
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
