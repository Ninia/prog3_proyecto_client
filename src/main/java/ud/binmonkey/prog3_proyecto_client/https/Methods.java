package ud.binmonkey.prog3_proyecto_client.https;

public enum Methods {
    GET("GET"), POST("POST"), PUT("PUT"), DELETE("DELETE");

    private final String request;

    Methods(String request) {
        this.request = request;
    }

     @Override
    public String toString() {
        return request;
     }
}
