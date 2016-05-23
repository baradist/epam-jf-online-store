package servlets;

import service.Helper;
import dao.dto.PersonDto;
import dao.interfaces.PersonDao;
import lombok.extern.log4j.Log4j;
import model.Person;
import service.DaoHandler;

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
    public void init() throws ServletException {
        personDao = (PersonDao) DaoHandler.getDaoByClass(Person.class);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/registration.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (Boolean.parseBoolean(request.getParameter("logout"))) {
            try {
                request.logout();
                log.info("logged out");
            } catch (ServletException e) {
                log.error("Can't log out", e);
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
                log.info("Registration: loginIsEmpty");
            }
            if (password.isEmpty()) {
                request.setAttribute("passwordIsEmpty", true);
                formFilledWrong = true;
                log.info("Registration: passwordIsEmpty");

            }
            if (firstName.isEmpty()) {
                request.setAttribute("firstNameIsEmpty", true);
                formFilledWrong = true;
                log.info("Registration: firstNameIsEmpty");

            }

            if (lastName.isEmpty()) {
                request.setAttribute("lastNameIsEmpty", true);
                formFilledWrong = true;
                log.info("Registration: lastNameIsEmpty");

            }
            if (personDao.getByEmail(email).isPresent()) {
                request.setAttribute("loginIsBusy", true);
                formFilledWrong = true;
                log.info("Registration: loginIsBusy (" + email + ")");
            }
            if (!password.equals(confirmPassword)) {
                request.setAttribute("differentPasswords", true);
                formFilledWrong = true;
                log.info("Registration: entered different passwords");
            }

            LocalDate birthday = (dob.isEmpty()) ? null : Helper.convertStringToLacalDate(dob);

            if (formFilledWrong) {
                // try again
                request.setAttribute("email", email);
                request.setAttribute("firstName", firstName);
                request.setAttribute("lastName", lastName);
                request.setAttribute("dob", dob);
                request.setAttribute("phone", phone);
                request.setAttribute("address", address);
                request.getRequestDispatcher("/registration.jsp").forward(request, response);

            } else {
                // welcome
                PersonDto personDto = new PersonDto(email, firstName, lastName, birthday, password, address, phone);
                personDao.add(personDto);
                log.info("Added new person: " + personDto);
                personDao.addRole(email, Person.Role.CUSTOMER.toString());
                log.info("Added role: " + email + " - " + Person.Role.CUSTOMER.toString());
                request.login(email, password);
                log.info(email + " logging in successful");
                response.sendRedirect("/");
            }
        }
    }
}
