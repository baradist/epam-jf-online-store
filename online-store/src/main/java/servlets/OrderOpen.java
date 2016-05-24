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
@WebServlet({"/orders/open/"})
public class OrderOpen extends HttpServlet{

    private OrderDao orderDao;
    private OrderItemDao orderItemDao;

    @Override
    public void init() throws ServletException {
        orderDao = (OrderDao) DaoHandler.getDaoByClass(Order.class);
        orderItemDao = (OrderItemDao) DaoHandler.getDaoByClass(OrderItem.class);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Order order = OrderConverter.convert(orderDao.getById(parseInt(request.getParameter("id"))).get());
        Collection<OrderItem> orderItems = OrderItemConverter.convert(orderItemDao.getByOrder(order.getId()));
        request.setAttribute("order", order);
        request.setAttribute("items", orderItems);

        // noinspection InjectedReferences
        log.info("order (id=" + order.getId() + ") info add to the request");

        // noinspection InjectedReferences
        request.getRequestDispatcher("/orders/open/index.jsp").forward(request, response);
    }
}
