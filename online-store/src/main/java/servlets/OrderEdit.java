package servlets;

import dao.dto.OrderDto;
import dao.dto.converters.OrderConverter;
import dao.interfaces.OrderDao;
import listeners.DbInitializer;
import model.Good;
import model.Order;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Integer.parseInt;

@WebServlet({"/documents/orders/edit"})
public class OrderEdit extends HttpServlet{

    private OrderDao orderDao;
    private static Map<String, Order> editingOrders = new HashMap<>();

    @Override
    public void init(ServletConfig config) throws ServletException {
        orderDao = (OrderDao) DbInitializer.getDaoByClass(Good.class);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (Boolean.parseBoolean(request.getParameter("isNew"))) {
            // new
            request.setAttribute("isNew", true);
        } else {
            // edit
            Order order = OrderConverter.convert(orderDao.getById(parseInt(request.getParameter("id"))).get());
            editingOrders.put(request.getSession().getId(), order);

            request.setAttribute("order", order);
            request.setAttribute("isNew", false);
        }

        // noinspection InjectedReferences
        request.getRequestDispatcher("/documents/orders/edit/index.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (Boolean.parseBoolean(request.getParameter("delete"))) {
            // delete
            orderDao.delete(parseInt(request.getParameter("id")));
        } else if (Boolean.parseBoolean(request.getParameter("isNew"))) {
            // new
            OrderDto orderDto = new OrderDto(
                    request.getParameter("number"),
                    Instant.now(), // TODO: date
                    0, // TODO: customer
                    request.getParameter("state"),
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
