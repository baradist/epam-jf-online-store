package dao.interfaces;

import model.Good;
import model.Lot;

import java.util.Collection;
import java.util.Optional;

public interface LotDao extends Dao {
    Optional<Good> getById(int id);

    Collection<Lot> getList();
}
