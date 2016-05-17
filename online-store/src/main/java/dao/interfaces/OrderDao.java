package dao.interfaces;

import dao.dto.OrderDto;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public interface OrderDao extends Dao {
    Optional<OrderDto> getById(int id);

    Collection<OrderDto> getList();

    Map<Integer, OrderDto> getMapByIds(Collection<Integer> ids);

    Collection<OrderDto> getListByIds(Collection<Integer> ids);

    boolean add(OrderDto orderDto);

    boolean update(OrderDto orderDto);

    boolean delete(int id);

    Optional<OrderDto> getPersonsBasket(int personId);

    Optional<OrderDto> getPersonsBasket(String email);

    boolean deleteIfEmpty(int orderId);

    boolean markDeleted(int orderId);
}
