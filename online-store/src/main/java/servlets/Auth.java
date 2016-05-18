package servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Oleg Grigorjev on 16.05.2016.
 */

@WebServlet("/auth")
public class Auth extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (Boolean.parseBoolean(request.getParameter("logout"))) {
            request.logout();
            response.sendRedirect("/");
        } else if (Boolean.parseBoolean(request.getParameter("login"))) {
            if (request.getUserPrincipal() == null) {
//                request.login();
                response.sendRedirect("/");
            }
        }
    }
}
