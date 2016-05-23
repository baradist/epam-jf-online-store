package dao.dto.converters;

import dao.dto.OrderItemDto;
import dao.interfaces.GoodDao;
import dao.interfaces.OrderDao;
import model.Good;
import model.Order;
import model.OrderItem;
import service.DaoHandler;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Oleg Grigorjev on 13.05.2016.
 */
public interface OrderItemConverter {
    static OrderItem convert(OrderItemDto orderItemDto) {
        return new OrderItem(
                orderItemDto.getId(),
                OrderConverter.convert(((OrderDao) DaoHandler.getDaoByClass(Order.class)).getById(orderItemDto.getOrder()).get()),
                GoodConverter.convert(((GoodDao) DaoHandler.getDaoByClass(Good.class)).getById(orderItemDto.getGood()).get()),
                orderItemDto.getQuantity(),
                orderItemDto.getPrice()
        );
    }

    static Collection<OrderItem> convert(Collection<OrderItemDto> orderItemDtos) { // TODO
        Collection<OrderItem> orderItems = new ArrayList<>();
        for (OrderItemDto producerDto : orderItemDtos) {
            orderItems.add(convert(producerDto));
        }
        return orderItems;
    }

    static OrderItemDto convert(OrderItem orderItem) {
        return new OrderItemDto(
                orderItem.getId(),
                orderItem.getOrder().getId(),
                orderItem.getGood().getId(),
                orderItem.getQuantity(),
                orderItem.getPrice()
        );
    }
}
