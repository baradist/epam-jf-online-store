package dao.interfaces;

import model.Country;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public interface CountryDao extends Dao {
    Optional<Country> getById(int id);

//    Collection<Good> getList();

    Map<Integer, Country> getMapByIds(Collection<Integer> ids);

    Collection<Country> getListByIds(Collection<Integer> ids);

    Collection<Country> getList();
}
