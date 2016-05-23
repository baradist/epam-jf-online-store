package servlets;

import dao.dto.converters.OrderConverter;
import dao.interfaces.OrderDao;
import model.Order;
import service.DaoHandler;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

/**
 * Created by Oleg Grigorjev on 21.05.2016.
 */
@WebServlet("/orders/")
public class OrderList extends HttpServlet {
    private OrderDao orderDao;

    @Override
    public void init() throws ServletException {
        orderDao = (OrderDao) DaoHandler.getDaoByClass(Order.class);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Collection<Order> orders = OrderConverter.convert(orderDao.getListByEmailAndState(request.getRemoteUser(), "SENT"));
        request.setAttribute("orders", orders);

        request.getRequestDispatcher("/orders/index.jsp").forward(request, response);
    }
}
