package ud.binmonkey.prog3_proyecto_client.common;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

    public static boolean validName(String userName) {
        Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(userName);
        return !m.find();
    }

}
