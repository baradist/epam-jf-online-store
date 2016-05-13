package dao.interfaces;

import model.OrderItem;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public interface OrderItemDao extends Dao {
    Optional<OrderItem> getById(int id);

    Map<Integer, OrderItem> getMapByIds(Collection<Integer> ids);

    Collection<OrderItem> getListByIds(Collection<Integer> ids);

    Collection<OrderItem> getList();
}
