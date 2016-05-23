package filters;

import service.Helper;
import common.servlets.HttpFilter;
import dao.interfaces.OrderDao;
import model.Order;
import model.Person;
import service.DaoHandler;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;

/**
 *
 */

@WebFilter("/*")
public class TopLineFilter implements HttpFilter {
    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        Principal userPrincipal = request.getUserPrincipal();
        if (userPrincipal != null) {
            request.setAttribute("isLoggedIn", true);
            if (request.isUserInRole(Person.Role.MANAGER.toString()) || request.isUserInRole(Person.Role.ADMINISTRATOR.toString())) {
                request.setAttribute("canEdit", true);
            }
            request.setAttribute("email", userPrincipal.getName());
        }

        if (userPrincipal != null && request.getMethod().equalsIgnoreCase("get")
                || request.getRequestURI().equalsIgnoreCase("/basket/")) {
            Helper.TwoValues<Float, Float> quantitySum = ((OrderDao) DaoHandler.getDaoByClass(Order.class)).getPersonsBasketQuantityAndSum(userPrincipal.getName()).get();
            request.setAttribute("basketSum", quantitySum.first);
            request.setAttribute("basketQuantity", quantitySum.second);
        }

        chain.doFilter(request, response);
    }
}
