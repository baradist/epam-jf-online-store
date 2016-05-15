package servlets;

import dao.dto.OrderDto;
import dao.dto.OrderItemDto;
import dao.interfaces.OrderDao;
import dao.interfaces.OrderItemDao;
import listeners.DbInitializer;
import model.Order;
import model.OrderItem;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.util.Optional;

/**
 * Created by Oleg Grigorjev on 15.05.2016.
 */
@WebServlet({"/basket"})
public class Basket extends HttpServlet {
    private OrderDao orderDao;
    private OrderItemDao orderItemDao;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (orderDao == null) {
            orderDao = (OrderDao) DbInitializer.getDaoByClass(Order.class);
        }
        if (orderItemDao == null) {
            orderItemDao = (OrderItemDao) DbInitializer.getDaoByClass(OrderItem.class);
        }
        Optional<OrderDto> basket = orderDao.getPersonsBasket(7);
        if (!basket.isPresent()) {
            OrderDto orderDto = new OrderDto("number", Instant.now(), 7, Order.State.NEW.toString(), null);
            orderDao.add(orderDto); // TODO return orderDto
            basket = orderDao.getPersonsBasket(7);
        }
        OrderDto orderDto = basket.get();

        if (Boolean.parseBoolean(request.getParameter("add"))) {
            OrderItemDto orderItemDto = new OrderItemDto(orderDto.getId(),
                    Integer.parseInt(request.getParameter("good")),
                    Double.parseDouble(request.getParameter("quantity")),
                    Double.parseDouble(request.getParameter("price"))
            );
            orderItemDao.add(orderItemDto);


        } else if (Boolean.parseBoolean(request.getParameter("delete"))) {
            orderItemDao.deleteFromBasket(orderDto.getId(), Integer.parseInt(request.getParameter("good")));
//            orderDao.deleteIfEmpty(orderDto.getId()); // TODO
        }
        response.sendRedirect("/");
    }
}
