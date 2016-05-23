package servlets;

import lombok.extern.log4j.Log4j;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Log4j
@WebServlet("/localizator")
public class Localizator extends HttpServlet {
    public static final String LOCAL = "local";

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String local = request.getParameter(LOCAL);
        setLocale(request.getSession(true), local);

        response.sendRedirect("/");
    }

    public static void setLocale(HttpSession session, String local) {
        String sessionLocal = (String) session.getAttribute(LOCAL);
        if (sessionLocal == null || !sessionLocal.equals(local)) {
            session.setAttribute(LOCAL, local);
            log.info("Locale set to " + local);
        }
    }
}
