package servlets;

import lombok.extern.log4j.Log4j;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j
@WebServlet("/localizator")
public class Localizator extends HttpServlet {
//    private static final Logger log = Logger. getRootLogger();

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String local = request.getParameter("local");
        request.getSession(true).setAttribute("local", local);

        log.info("Locale set to " + local);
        // TODO:
//        request.getRequestDispatcher("/").forward(request, response);
        response.sendRedirect("/");
    }
}
