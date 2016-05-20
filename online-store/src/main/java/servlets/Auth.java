package servlets;

import common.functions.Helper;
import dao.interfaces.PersonDao;
import listeners.DbInitializer;
import lombok.extern.log4j.Log4j;
import model.Person;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

/**
 * Created by Oleg Grigorjev on 16.05.2016.
 */
@Log4j
@WebServlet("/auth")
public class Auth extends HttpServlet {
    private PersonDao personDao;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/registration.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
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
        } else if (Boolean.parseBoolean(request.getParameter("register"))) {
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String confirmPassword = request.getParameter("confirm_password");
            String firstName = request.getParameter("first_name");
            String lastName = request.getParameter("last_name");
            String dob = request.getParameter("dob");
            String phone = request.getParameter("phone");
            String address = request.getParameter("address");

            boolean formFilledWrong = false;

            if (email.isEmpty()) {
                request.setAttribute("loginIsEmpty", true);
                formFilledWrong = true;
            }

            LocalDate birthday = (dob.isEmpty()) ? null : Helper.convertStringToLacalDate(dob);
            if (personDao == null) {
                personDao = (PersonDao) DbInitializer.getDaoByClass(Person.class);
            }
            if (email.isEmpty()) {
                request.setAttribute("loginIsEmpty", true);
                formFilledWrong = true;
            }

            if (personDao.getByEmail(email).isPresent()) {
                request.setAttribute("loginIsBusy", true);
                formFilledWrong = true;
            }
            if (password != confirmPassword) {
                request.setAttribute("differentPasswords", true);
                formFilledWrong = true;
            }

            if (formFilledWrong) {
                request.getRequestDispatcher("/registration.jsp").forward(request, response);

            } else {
                response.sendRedirect("/");
            }
        }
    }
}
