package servlets;

import dao.dto.OrderDto;
import dao.interfaces.OrderDao;
import dao.interfaces.OrderItemDao;
import lombok.extern.log4j.Log4j;
import model.Order;
import model.OrderItem;
import service.DaoHandler;

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
        orderDao = (OrderDao) DaoHandler.getDaoByClass(Order.class);
        orderItemDao = (OrderItemDao) DaoHandler.getDaoByClass(OrderItem.class);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (Boolean.parseBoolean(request.getParameter("send"))) {
            String email = request.getRemoteUser();
            log.info(email + ": sending the order");
            OrderDto basketDto = orderDao.getPersonsBasket(email).get();
            basketDto.setState(Order.State.SENT.toString());
            orderDao.update(basketDto);
            log.info("order " + basketDto.getId() + " SENT");

            response.sendRedirect("/orders/");
        }
    }
}
