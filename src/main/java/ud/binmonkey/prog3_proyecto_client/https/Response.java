package ud.binmonkey.prog3_proyecto_client.https;

import java.util.HashMap;

public class Response {
    public int code;
    public HashMap<String, String> headers;
    public String content;

    public Response() {
    }

    public Response(int code, HashMap<String, String> headers, String content) {
        this.code = code;
        this.headers = headers;
        this.content = content;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public HashMap<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(HashMap<String, String> headers) {
        this.headers = headers;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
