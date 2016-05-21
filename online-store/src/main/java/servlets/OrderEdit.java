package servlets;

import dao.dto.OrderDto;
import dao.dto.converters.OrderConverter;
import dao.dto.converters.OrderItemConverter;
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
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Integer.parseInt;

@WebServlet({"/documents/orders/edit"})
public class OrderEdit extends HttpServlet{

    private OrderDao orderDao;
    private OrderItemDao orderItemDao;
    private static Map<String, Order> editingOrders = new HashMap<>();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (orderDao == null) {
            orderDao = (OrderDao) DbInitializer.getDaoByClass(Order.class);
        }
        if (Boolean.parseBoolean(request.getParameter("isNew"))) {
            // new
            request.setAttribute("isNew", true);
//        } else if (Boolean.parseBoolean(request.getParameter("delete"))) {
////             delete

        } else {
            // edit
            if (orderItemDao == null) {
                orderItemDao = (OrderItemDao) DbInitializer.getDaoByClass(OrderItem.class);
            }
            Order order = OrderConverter.convert(orderDao.getById(parseInt(request.getParameter("order"))).get());
            editingOrders.put(request.getSession().getId(), order);

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
        if (orderDao == null) {
            orderDao = (OrderDao) DbInitializer.getDaoByClass(Order.class);
        }
        if (Boolean.parseBoolean(request.getParameter("delete"))) {
            // delete
//            orderDao.delete(parseInt(request.getParameter("order")));
            orderDao.markDeleted(parseInt(request.getParameter("order")));
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

        } else {
            // edit
            String sessionId = request.getSession().getId();
            Order order = editingOrders.get(sessionId);
            editingOrders.remove(sessionId);

            order.setNumber(request.getParameter("number"));
            order.setState(Order.State.valueOf(request.getParameter("state")));

            orderDao.update(OrderConverter.convert(order));
        }

        response.sendRedirect("/documents/orders");
    }
}
