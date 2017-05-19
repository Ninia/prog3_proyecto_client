package ud.binmonkey.prog3_proyecto_client.common;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

public class InputStreamStringReader {
    public static String readInputStream(InputStream is) throws IOException {
        char[] buf = new char[2048];
        Reader r = new java.io.InputStreamReader(is);
        StringBuilder s = new StringBuilder();
        while (true) {
            int n = r.read(buf);
            if(n < 0) {
                break;
            }
            s.append(buf, 0, n);
        }
        return s.toString();
    }
}
