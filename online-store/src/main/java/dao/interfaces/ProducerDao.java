package dao.interfaces;

import model.Producer;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public interface ProducerDao extends Dao {
    Optional<Producer> getById(int id);

//    Collection<Good> getList();

    Map<Integer, Producer> getMapByIds(Collection<Integer> ids);

    Collection<Producer> getListByIds(Collection<Integer> ids);

    Collection<Producer> getList();
}
