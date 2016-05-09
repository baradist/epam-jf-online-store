package dao.mysql;

import common.functions.Helper;
import dao.interfaces.StoreDao;
import model.Store;

import java.util.*;

@FunctionalInterface
public interface MySqlStoreDao extends StoreDao {

    @Override
    default Optional<Store> getById(int id) {
        return executeQuery(
                "SELECT ID, NAME, ADDRESS FROM STORE WHERE id = " + id,
                rs -> rs.next()
                        ? new Store(id, rs.getString("NAME"), rs.getString("ADDRESS"))
                        : null
        ).toOptional();
    }

    @Override
    default Collection<Store> getListByIds(Collection<Integer> ids) { // TODO
        return getMapByIds(ids).values();
    }

    @Override
    default Map<Integer, Store> getMapByIds(Collection<Integer> ids) {
        return executeQuery(
                "SELECT ID, NAME, ADDRESS FROM STORE " +
                        "WHERE ID IN (" + Helper.ArrayToString(ids) + ")",
                rs -> {
                    Map<Integer, Store> storeMap = new HashMap<>();

                    while (rs.next())
                        storeMap.put(rs.getInt("ID"),
                                new Store(
                                        rs.getInt("ID"),
                                        rs.getString("NAME"),
                                        rs.getString("ADDRESS")
                                ));

                    return storeMap;
                }).toOptional().orElse(Collections.emptyMap());
    }
}
