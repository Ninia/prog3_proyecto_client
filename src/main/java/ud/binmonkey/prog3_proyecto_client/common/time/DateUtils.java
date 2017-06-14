package ud.binmonkey.prog3_proyecto_client.common.time;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {
    public static String currentFormattedDate() {
        return (new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")).format(new Date());
    }

    public static String dateFormatter(Date date) {
        return (new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH)).format(date);
    }

    public static void main(String[] args) {
        System.out.println(currentFormattedDate());
    }
}
