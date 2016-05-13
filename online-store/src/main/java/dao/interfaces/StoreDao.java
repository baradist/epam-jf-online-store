package dao.interfaces;

import model.Store;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public interface StoreDao extends Dao {
    Optional<Store> getById(int id);

    Map<Integer, Store> getMapByIds(Collection<Integer> ids);

    Collection<Store> getListByIds(Collection<Integer> ids);
}
