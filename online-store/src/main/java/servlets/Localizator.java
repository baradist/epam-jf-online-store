package servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/localizator")
public class Localizator extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getSession(true).setAttribute("local", request.getParameter("local"));
        // TODO:
//        request.getRequestDispatcher("/").forward(request, response);
        response.sendRedirect("/");
    }
}
