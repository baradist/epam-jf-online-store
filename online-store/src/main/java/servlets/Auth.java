package servlets;

import lombok.extern.log4j.Log4j;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Oleg Grigorjev on 16.05.2016.
 */
@Log4j
@WebServlet("/auth")
public class Auth extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (Boolean.parseBoolean(request.getParameter("logout"))) {
            try {
                request.logout();
            } catch (ServletException e) {
                log.info("Can't log out", e);
            }
            response.sendRedirect("/");
        } else if (Boolean.parseBoolean(request.getParameter("login"))) {
            if (request.getUserPrincipal() == null) {
                String j_username = request.getParameter("j_username");

                try {
                    request.login(j_username, request.getParameter("j_password"));
                    log.info(j_username + " logging in successful");
                } catch (ServletException e) {
                    log.info(j_username, e);
                }
                response.sendRedirect("/");
            }
        }
    }
}
