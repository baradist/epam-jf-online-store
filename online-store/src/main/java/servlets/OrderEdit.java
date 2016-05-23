package servlets;

import dao.dto.OrderDto;
import dao.dto.converters.OrderConverter;
import dao.dto.converters.OrderItemConverter;
import dao.interfaces.OrderDao;
import dao.interfaces.OrderItemDao;
import lombok.extern.log4j.Log4j;
import model.Order;
import model.OrderItem;
import service.DaoHandler;
import service.Helper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.util.Collection;

import static java.lang.Integer.parseInt;

/**
 * Created by Oleg Grigorjev 
 */
 
@Log4j
@WebServlet({"/documents/orders/edit"})
public class OrderEdit extends HttpServlet{

    private OrderDao orderDao;
    private OrderItemDao orderItemDao;

    @Override
    public void init() throws ServletException {
        orderDao = (OrderDao) DaoHandler.getDaoByClass(Order.class);
        orderItemDao = (OrderItemDao) DaoHandler.getDaoByClass(OrderItem.class);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (Boolean.parseBoolean(request.getParameter("isNew"))) {
            // new
            request.setAttribute("isNew", true);
        } else {
            // edit
            Order order = OrderConverter.convert(orderDao.getById(parseInt(request.getParameter("order"))).get());
            Collection<OrderItem> orderItems = OrderItemConverter.convert(orderItemDao.getByOrder(order.getId()));
            request.setAttribute("order", order);
            request.setAttribute("items", orderItems);
            request.setAttribute("isNew", false);
        }

        // noinspection InjectedReferences
        request.getRequestDispatcher("/documents/orders/edit/index.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (Boolean.parseBoolean(request.getParameter("delete"))) {
            // delete
//            orderDao.delete(parseInt(request.getParameter("order")));
            int orderId = parseInt(request.getParameter("order"));
            orderDao.markDeleted(orderId);
            log.info("the order id=" + orderId + " deleted");
        } else if (Boolean.parseBoolean(request.getParameter("isNew"))) {
            // new
            OrderDto orderDto = new OrderDto(
                    request.getParameter("number"),
                    Instant.now(), // TODO: date
                    0, // TODO: customer
                    request.getParameter("state"),
                    0,
                    null // deleted
            );
            orderDao.add(orderDto);
            log.info("a new order added");
        } else {
            // edit
            OrderDto orderDto = new OrderDto(
                    Integer.parseInt(request.getParameter("id")),
                    request.getParameter("number"),
                    Helper.convertStringToInstant(request.getParameter("date")),
                    0, // TODO: customer
                    request.getParameter("state"),
                    0,
                    null // deleted
            );
            orderDao.update(orderDto);
            log.info("the order id=" + orderDto.getId() + " edited");
        }

        response.sendRedirect("/documents/orders/");
    }
}
