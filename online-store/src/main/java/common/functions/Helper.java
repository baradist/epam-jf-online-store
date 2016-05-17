package common.functions;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    public static String getCookieValue(HttpServletRequest request, String path, String key) { // TODO
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                Cookie c = cookies[i];
                if (c.getName().equals(key) && ((path.equals("/") && c.getPath() == null) || (c.getPath() != null && c.getPath().equalsIgnoreCase(path)))) {
                    return c.getValue();
                }
            }
        }
        return null;
    }

    public static void putCookie(HttpServletResponse response, String path, String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(86400); // TODO ??
        cookie.setPath(path);
        response.addCookie(cookie);
    }
}
