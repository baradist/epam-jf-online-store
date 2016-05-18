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

    /*public*/ static String ArrayToString(Collection<Integer> array) {
        StringBuilder buffer = new StringBuilder();
        for (Integer integer : array) {
            buffer.append(integer);
            buffer.append(",");
        }
        return buffer.substring(0, buffer.length() - 1);
    }

    String PATTERN = "yyyy-MM-dd HH:mm:ss";

    /*public*/ static String convertDateTime(Instant instant) {
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(PATTERN);
        Date dt = Date.from(instant);
        return sdf.format(dt);
    }

    /*public*/ static Instant convertDateTime(String string) {
        DateFormat format = new SimpleDateFormat(PATTERN);
        try {
            return format.parse(string).toInstant();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /*public*/ static String getCookieValue(HttpServletRequest request, String key) { // TODO
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                Cookie c = cookies[i];
//                if (c.getName().equals(key) && ((path.equals("/") && c.getPath() == null) || (c.getPath() != null && c.getPath().equalsIgnoreCase(path)))) {
                if (c.getName().equals(key)) {
                    return c.getValue();
                }
            }
        }
        return null;
    }

    /*public*/ static void putCookie(HttpServletResponse response, String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(86400); // TODO ??
//        cookie.setPath(path);
        response.addCookie(cookie);
    }

    /**
     * works only with /WEB-INF/pager.jsp !!!
     * @param request
     * @param response
     * @param quantity of rows
     * @return an offset and a number of rows on the page
     */
    /*public*/ static OffsetAndRowsOnPage longListByPages(HttpServletRequest request, HttpServletResponse response, int quantity) {
        request.setAttribute("url", request.getRequestURI());

        request.setAttribute("quantity", quantity);

        int rowsOnPage = 10;

        String rowsOnPageString = request.getParameter("rowsOnPage");

        if (rowsOnPageString != null) {
            if (Boolean.parseBoolean(request.getParameter("changeRowsOnPage"))) {
                Helper.putCookie(response, "rowsOnPage", rowsOnPageString);
            }
            rowsOnPage = Integer.parseInt(rowsOnPageString);
        } else {
            rowsOnPageString = Helper.getCookieValue(request, "rowsOnPage");
            if (rowsOnPageString != null) {
                rowsOnPage = Integer.parseInt(rowsOnPageString);
            }
        }
        request.setAttribute("rowsOnPage", rowsOnPage);

        String offsetString = request.getParameter("offset");
        String pageNumberString = request.getParameter("pageNumber");
        int offset;
        int pageNumber;
        if (offsetString != null && pageNumberString != null) {
            offset = Integer.parseInt(offsetString);
            pageNumber = Integer.parseInt(pageNumberString);
        } else {
            offset = 0;
            pageNumber = 1;
        }
        request.setAttribute("pageNumber", pageNumber);
        return new OffsetAndRowsOnPage(offset, rowsOnPage);
    }

    /*public static*/ class OffsetAndRowsOnPage {
        public final int offset;
        public final int rowsOnPage;

        public OffsetAndRowsOnPage(int offset, int rowsOnPage) {
            this.offset = offset;
            this.rowsOnPage = rowsOnPage;
        }
    }
}
