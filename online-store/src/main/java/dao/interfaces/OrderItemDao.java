package dao.interfaces;

import model.OrderItem;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public interface OrderItemDao extends Dao {
    Optional<OrderItem> getById(int id);

    Collection<OrderItem> getList();

    Collection<OrderItem> getListByIds(Collection<Integer> ids);

    Map<Integer, OrderItem> getMapByIds(Collection<Integer> ids);
}
