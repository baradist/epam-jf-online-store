package dao.dto.converters;

import dao.dto.OrderDto;
import dao.interfaces.PersonDao;
import model.Order;
import model.Person;
import service.DaoHandler;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Oleg Grigorjev on 13.05.2016.
 */
public interface OrderConverter {
    static Order convert(OrderDto orderDto) {
        return new Order(
                orderDto.getId(),
                orderDto.getNumber(),
                orderDto.getDate(),
                PersonConverter.convert(((PersonDao) DaoHandler.getDaoByClass(Person.class)).getById(orderDto.getCustomer()).get()),
                Order.State.valueOf(orderDto.getState()),
                orderDto.getSum(),
                orderDto.getDeleted()
        );
    }

    static Collection<Order> convert(Collection<OrderDto> orderDtos) { // TODO
        Collection<Order> orders = new ArrayList<>();
        for (OrderDto goodDto : orderDtos) {
            orders.add(convert(goodDto));
        }
        return orders;
    }

    static OrderDto convert(Order order) {
        return new OrderDto(
                order.getId(),
                order.getNumber(),
                order.getDate(),
                order.getCustomer().getId(),
                order.getState().toString(),
                order.getSum(),
                order.getDeleted()
        );
    }
}
