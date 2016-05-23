package filters;

import common.servlets.HttpFilter;
import dao.dto.OrderDto;
import dao.dto.converters.OrderConverter;
import dao.interfaces.OrderDao;
import lombok.extern.log4j.Log4j;
import model.Order;
import service.DaoHandler;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

/**
 * Created by Oleg Grigorjev 
 */
 
@Log4j
@WebFilter({"/documents/orders/", "/documents/orders/index.jsp"})
public class OrderList implements HttpFilter {
    private OrderDao orderDao;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        orderDao = (OrderDao) DaoHandler.getDaoByClass(Order.class);
    }

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        Collection<OrderDto> orderDtos = orderDao.getList();
        request.setAttribute("orders", OrderConverter.convert(orderDtos));
        log.info("order list add to the request");

        chain.doFilter(request, response);
    }
}
