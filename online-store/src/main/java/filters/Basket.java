package filters;

import common.servlets.HttpFilter;
import dao.dto.OrderDto;
import dao.dto.OrderItemDto;
import dao.dto.converters.OrderItemConverter;
import dao.dto.converters.PersonConverter;
import dao.interfaces.OrderDao;
import dao.interfaces.OrderItemDao;
import dao.interfaces.PersonDao;
import lombok.extern.log4j.Log4j;
import model.Order;
import model.OrderItem;
import model.Person;
import service.DaoHandler;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.Collection;
import java.util.Optional;

/**
 * Created by Oleg Grigorjev on 17.05.2016.
 */

@Log4j
@WebFilter({"/basket", "/basket/", "/basket/index.jsp"})
public class Basket implements HttpFilter {
    private OrderDao orderDao;
    private OrderItemDao orderItemDao;
    private PersonDao personDao;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        personDao = (PersonDao) DaoHandler.getDaoByClass(Person.class);
        orderDao = (OrderDao) DaoHandler.getDaoByClass(Order.class);
        orderItemDao = (OrderItemDao) DaoHandler.getDaoByClass(OrderItem.class);
    }

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("editing the basket");
        String orderItemIdString = request.getParameter("orderItemId");
        if (orderItemIdString != null) {
            int orderItemId = Integer.parseInt(orderItemIdString);
            Optional<OrderItemDto> orderItemDtoOptional = orderItemDao.getById(orderItemId);
            if (orderItemDtoOptional.isPresent()) {
                log.info("got the order item id=" + orderItemId);
                OrderItemDto orderItemDto = orderItemDtoOptional.get();
                if (Boolean.parseBoolean(request.getParameter("decrease"))) {
                    if (orderItemDto.getQuantity() <= 1) {
                        orderItemDao.delete(orderItemDto.getId());
                        log.info("the order item (id=" + orderItemId + ") deleted");
                    } else {
                        orderItemDto.setQuantity(orderItemDto.getQuantity() - 1);
                        log.info("the order item (id=" + orderItemId + ") quantity decreased to " + (orderItemDto.getQuantity() - 1));
                        orderItemDao.update(orderItemDto);
                        log.info("the order item (id=" + orderItemId + ") updated");
                    }
                } else if (Boolean.parseBoolean(request.getParameter("increase"))) {
                    orderItemDto.setQuantity(orderItemDto.getQuantity() + 1);
                    log.info("the order item (id=" + orderItemId + ") quantity increased to " + (orderItemDto.getQuantity() + 1));
                    orderItemDao.update(orderItemDto);
                    log.info("the order item (id=" + orderItemId + ") updated");
                } else if (Boolean.parseBoolean(request.getParameter("delete"))) {
                    orderItemDao.delete(orderItemDto.getId());
                    log.info("the order item (id=" + orderItemId + ") deleted");
                }
            }
        }
        Principal userPrincipal = request.getUserPrincipal();
        OrderDto orderDto = orderDao.getPersonsBasket(userPrincipal.getName()).get();
        Collection<OrderItem> orderItems = OrderItemConverter.convert(orderItemDao.getByOrder(orderDto.getId()));
        request.setAttribute("list", orderItems);

        Person person = PersonConverter.convert(personDao.getByEmail(userPrincipal.getName()).get());
        request.setAttribute("person", person);

        chain.doFilter(request, response);
    }
}
