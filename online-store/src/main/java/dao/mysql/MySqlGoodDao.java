package dao.mysql;

import common.functions.Helper;
import dao.interfaces.GoodDao;
import dao.interfaces.ProducerDao;
import listeners.DbInitializer;
import model.Good;
import model.Producer;

import java.util.*;

@FunctionalInterface
public interface MySqlGoodDao extends GoodDao {

    @Override
    default Optional<Good> getById(int id) { // TODO
        return executeQuery(
                "SELECT name, caliber FROM Gun WHERE id = " + id,
                rs -> rs.next()
                        ? new Good(id, rs.getString("name"), new Producer(0, "", null), rs.getString("description"))
                        : null
        ).toOptional();
    }

    @Override
    default Collection<Good> getListByIds(Collection<Integer> ids) { // TODO
        return getMapByIds(ids).values();
    }

    @Override
    default Map<Integer, Good> getMapByIds(Collection<Integer> ids) {
        return executeQuery(
                "SELECT ID, NAME, PRODUCER, DESCRIPTION FROM GOOD " +
                        "WHERE ID IN (" + Helper.ArrayToString(ids) + ")",
                rs -> {
                    Map<Integer, Good> goodMap = new HashMap<>();
                    Set<Integer> producerIds = new HashSet<>();
                    while (rs.next()) {
                        producerIds.add(rs.getInt("PRODUCER"));
                    }
                    ProducerDao producerDao = (ProducerDao) DbInitializer.getDaoByClass(Producer.class);
                    Map<Integer, Producer> producerMap = producerDao.getMapByIds(producerIds);

                    rs.beforeFirst();
                    while (rs.next())
                        goodMap.put(rs.getInt("ID"),
                                new Good(
                                        rs.getInt("ID"),
                                        rs.getString("NAME"),
                                        producerMap.get(rs.getInt("PRODUCER")),
                                        rs.getString("DESCRIPTION")
                                ));

                    return goodMap;
                }).toOptional().orElse(Collections.emptyMap());
    }
}
