package dao.interfaces;

import model.Order;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public interface OrderDao extends Dao {
    Optional<Order> getById(int id);

    Collection<Order> getList();

    Map<Integer, Order> getMapByIds(Collection<Integer> ids);

    Collection<Order> getListByIds(Collection<Integer> ids);
}
