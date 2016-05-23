package service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Date;

/**
 * Created by Oleg Grigorjev on 23.04.2016.
 */

public interface Helper {

    /*public*/ static String ArrayToString(Collection<Integer> array) {
        if (array.size() == 0) {
            return "";
        }
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
        if (string != null) {
            DateFormat format = new SimpleDateFormat(PATTERN);
            try {
                return format.parse(string).toInstant();
            } catch (ParseException e) {
//                log.warning() TODO
                return null;
//                throw new RuntimeException(e);
            }
        }
        return null;
    }

    /*public*/ static LocalDate convertStringToLacalDate(String string) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(string, formatter);
    }

    /*public*/ static Instant convertStringToInstant(String string) {
        if (string != null) {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            try {
                return format.parse(string).toInstant();
            } catch (ParseException e) {
//                log.warning() TODO
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    /*public*/ static String getCookieValue(HttpServletRequest request, String key) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                Cookie c = cookies[i];
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
        response.addCookie(cookie);
    }

    /**
     * works only with /WEB-INF/pager.jsp !!!
     * @param request
     * @param response
     * @param quantity of rows
     * @return the TwoValues object, which contains an offset and a number of rows on the page
     */
    /*public*/ static TwoValues<Integer, Integer> longListByPages(HttpServletRequest request, HttpServletResponse response, int quantity) {
        request.setAttribute("url", request.getRequestURI());

        String id = request.getParameter("id");
        if (id != null) {
            request.setAttribute("id", id);
        }
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
        return new TwoValues(offset, rowsOnPage);
    }

    static LocalDate getLocalDateFromDate(java.sql.Date date) {
        if (date != null) {
            return date.toLocalDate();
        } else {
            return null;
        }
    }

    /*public static*/ class TwoValues<F, S> {
        public final F first;
        public final S second;

        public TwoValues(F first, S second) {
            this.first = first;
            this.second = second;
        }
    }
}
