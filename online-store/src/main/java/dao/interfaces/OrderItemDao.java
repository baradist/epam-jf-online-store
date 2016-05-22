package dao.interfaces;

import dao.dto.OrderItemDto;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public interface OrderItemDao extends Dao {
    Optional<OrderItemDto> getById(int id);

    Optional<OrderItemDto> get(int orderId, int goodId);

    Collection<OrderItemDto> getList();

    Collection<OrderItemDto> getListByIds(Collection<Integer> ids);

    Map<Integer, OrderItemDto> getMapByIds(Collection<Integer> ids);

    boolean add(OrderItemDto orderItemDto);

    boolean update(OrderItemDto orderItemDto);

    boolean delete(int id);

    boolean delete(int orderId, int goodId);

    Collection<OrderItemDto> getByOrder(int orderId);
}
