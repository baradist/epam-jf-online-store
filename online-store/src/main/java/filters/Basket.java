package filters;

import common.servlets.HttpFilter;
import dao.dto.OrderDto;
import dao.dto.converters.OrderItemConverter;
import dao.interfaces.OrderDao;
import dao.interfaces.OrderItemDao;
import listeners.DbInitializer;
import model.Order;
import model.OrderItem;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.Collection;

/**
 * Created by o_grigorev on 17.05.2016.
 */
@WebFilter({"/basket", "/basket/", "/basket/index.jsp"})
public class Basket implements HttpFilter {
    private OrderDao orderDao;
    private OrderItemDao orderItemDao;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        orderDao = (OrderDao) DbInitializer.getDaoByClass(Order.class);
        orderItemDao = (OrderItemDao) DbInitializer.getDaoByClass(OrderItem.class);
    }

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        Principal userPrincipal = request.getUserPrincipal();

        if (userPrincipal == null) {
            return;
        }
        OrderDto orderDto = orderDao.getPersonsBasket(userPrincipal.getName()).get();

        Collection<OrderItem> orderItems = OrderItemConverter.convert(orderItemDao.getByOrder(orderDto.getId()));
        request.setAttribute("list", orderItems);

        chain.doFilter(request, response);
    }
}
