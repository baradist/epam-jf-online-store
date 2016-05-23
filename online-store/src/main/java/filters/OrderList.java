package filters;

import common.servlets.HttpFilter;
import dao.dto.OrderDto;
import dao.dto.converters.OrderConverter;
import dao.interfaces.OrderDao;
import model.Order;
import service.DaoHandler;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@WebFilter({"/documents/orders", "/documents/orders/index.jsp"})
public class OrderList implements HttpFilter {
    private OrderDao orderDao;

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (orderDao == null) {
            orderDao = (OrderDao) DaoHandler.getDaoByClass(Order.class);
        }
        Collection<OrderDto> orderDtos = orderDao.getList();
        request.setAttribute("orders", OrderConverter.convert(orderDtos));

        chain.doFilter(request, response);
    }
}
