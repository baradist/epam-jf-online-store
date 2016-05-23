package servlets;

import dao.dto.OrderDto;
import dao.dto.OrderItemDto;
import dao.dto.PersonDto;
import dao.interfaces.OrderDao;
import dao.interfaces.OrderItemDao;
import dao.interfaces.PersonDao;
import lombok.extern.log4j.Log4j;
import model.Order;
import model.OrderItem;
import model.Person;
import service.DaoHandler;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.time.Instant;
import java.util.Optional;

/**
 * Created by Oleg Grigorjev on 15.05.2016.
 *
 * adding and deleting goods from the main-page
 */

@Log4j
@WebServlet({"/basket/edit/"})
public class BasketEdit extends HttpServlet {
    private OrderDao orderDao;
    private OrderItemDao orderItemDao;
    private PersonDao personDao;

    @Override
    public void init() throws ServletException {
        personDao = (PersonDao) DaoHandler.getDaoByClass(Person.class);
        orderDao = (OrderDao) DaoHandler.getDaoByClass(Order.class);
        orderItemDao = (OrderItemDao) DaoHandler.getDaoByClass(OrderItem.class);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Principal userPrincipal = request.getUserPrincipal();
        PersonDto personDto = personDao.getByEmail(userPrincipal.getName()).get();
        Optional<OrderDto> basket = orderDao.getPersonsBasket(personDto.getId());
        if (!basket.isPresent()) {
            OrderDto orderDto = new OrderDto("number", Instant.now(), personDto.getId(), Order.State.NEW.toString(), 0, null);
            orderDao.add(orderDto); // TODO return orderDto
            basket = orderDao.getPersonsBasket(personDto.getId());
            log.info(userPrincipal.getName() + ": new basket (id=" + basket.get());
        }
        OrderDto orderDto = basket.get();

        Optional<OrderItemDto> orderItemDtoOptional = orderItemDao.get(orderDto.getId(), Integer.parseInt(request.getParameter("good")));
        if (Boolean.parseBoolean(request.getParameter("add"))) {
            OrderItemDto orderItemDto;
            if (orderItemDtoOptional.isPresent()) {
                orderItemDto = orderItemDtoOptional.get();
                orderItemDto.setQuantity(orderItemDto.getQuantity() + 1);
                orderItemDao.update(orderItemDto);
            } else {
                orderItemDto = new OrderItemDto(orderDto.getId(),
                        Integer.parseInt(request.getParameter("good")),
                        Double.parseDouble(request.getParameter("quantity")),
                        Double.parseDouble(request.getParameter("price")));
                orderItemDao.add(orderItemDto);
            }

//        } else if (Boolean.parseBoolean(request.getParameter("decrease"))) { // TODO: hasn't used yet
//            if (orderItemDtoOptional.isPresent()) {
//                OrderItemDto orderItemDto = orderItemDtoOptional.get();
//                if (orderItemDto.getQuantity() > 1) {
//                    orderItemDto.setQuantity(orderItemDto.getQuantity() - 1);
//                    orderItemDao.update(orderItemDto);
//                } else {
//                    orderItemDao.delete(orderDto.getId(), Integer.parseInt(request.getParameter("good")));
//                }
////            orderDao.deleteIfEmpty(orderDto.getId()); // TODO
//            }
        }else if (Boolean.parseBoolean(request.getParameter("delete"))) {
            if (orderItemDtoOptional.isPresent()) {
                OrderItemDto orderItemDto = orderItemDtoOptional.get();
                orderItemDao.delete(orderItemDto.getId());
            }
//            orderDao.deleteIfEmpty(orderDto.getId()); // TODO
        }
        response.sendRedirect("/");
    }
}
