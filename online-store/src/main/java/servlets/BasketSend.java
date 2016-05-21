package servlets;

import dao.interfaces.OrderDao;
import dao.interfaces.OrderItemDao;
import listeners.DbInitializer;
import lombok.extern.log4j.Log4j;
import model.Order;
import model.OrderItem;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Oleg Grigorjev on 21.05.2016.
 */

@Log4j
@WebServlet({"/basket/send/"})
public class BasketSend extends HttpServlet {
    private OrderDao orderDao;
    private OrderItemDao orderItemDao;

    @Override
    public void init() throws ServletException {
        orderDao = (OrderDao) DbInitializer.getDaoByClass(Order.class);
        orderItemDao = (OrderItemDao) DbInitializer.getDaoByClass(OrderItem.class);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (Boolean.parseBoolean(request.getParameter("send"))) {
            // TODO: make order and, maybe, other docs

            response.sendRedirect("/orders/");
        }
    }
}