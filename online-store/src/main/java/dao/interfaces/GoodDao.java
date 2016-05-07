package dao.interfaces;

import model.Good;
import model.Lot;

import java.util.Collection;
import java.util.Optional;

public interface GoodDao extends Dao {
    Optional<Good> getGoodById(int id);

    Collection<Lot> getLot();
}
