package dao.dto.converters;

import dao.dto.OrderDto;
import model.Order;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Oleg Grigorjev on 13.05.2016.
 */
public interface OrderItemConverter {
    static Order convert(OrderDto orderDto) {
        return new Order(
                orderDto.getId(),
                orderDto.getNumber(),
                orderDto.getDate(),
                null, // customer
                Order.State.valueOf(orderDto.getState()),
                orderDto.getDeleted()
        );
    }

    static Collection<Order> convert(Collection<OrderDto> orderDtos) { // TODO
        Collection<Order> orders = new ArrayList<>();
        for (OrderDto producerDto : orderDtos) {
            orders.add(convert(producerDto));
        }
        return orders;
    }

    static OrderDto convert(Order order) {
        return new OrderDto(
                order.getId(),
                order.getNumber(),
                order.getDate(),
                7, // customer // TODO
                order.getState().toString(),
                order.getDeleted()
        );
    }
}
