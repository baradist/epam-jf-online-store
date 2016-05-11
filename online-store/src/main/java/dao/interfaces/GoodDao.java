package dao.interfaces;

import model.Good;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public interface GoodDao extends Dao {
    Optional<Good> getById(int id);

//    Collection<Good> getList();

    Map<Integer, Good> getMapByIds(Collection<Integer> ids);

    Collection<Good> getListByIds(Collection<Integer> ids);

    Collection<Good> getList();
}
