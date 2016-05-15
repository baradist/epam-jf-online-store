package common.functions;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Collection;
import java.util.Date;

/**
 * Created by 1 on 23.04.2016.
 */
public interface Helper {

    public static String ArrayToString(Collection<Integer> array) {
        StringBuilder buffer = new StringBuilder();
        for (Integer integer : array) {
            buffer.append(integer);
            buffer.append(",");
        }
        return buffer.substring(0, buffer.length() - 1);
    }

    String PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static String convertDateTime(Instant instant) {
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(PATTERN);
        Date dt = Date.from(instant);
        return sdf.format(dt);
    }

    public static Instant convertDateTime(String string) {
        DateFormat format = new SimpleDateFormat(PATTERN);
        try {
            return format.parse(string).toInstant();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
