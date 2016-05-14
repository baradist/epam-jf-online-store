package dao.mysql;

import common.functions.Helper;
import dao.interfaces.OrderDao;
import model.Order;

import java.sql.Date;
import java.util.*;

@FunctionalInterface
public interface MySqlOrderDao extends OrderDao {

    @Override
    default Optional<Order> getById(int id) {
        return executeQuery(
                "SELECT  NUMBER, DATE, CUSTOMER, STATE, DELETED FROM STORE.ORDER WHERE ID = " + id,
                rs -> {
                    rs.next();
                    // TODO: personDao
//                    ProducerDao producerDao = (ProducerDao) DbInitializer.getDaoByClass(Producer.class);
//                    Optional<Producer> producerOptional = producerDao.getById(rs.getInt("PRODUCER"));
                    rs.beforeFirst();

                    return rs.next()
                            ? new Order(
                                id,
                                rs.getString("NUMBER"),
                                rs.getDate("DATE").toInstant(),
                                null, // rs.getInt("CUSTOMER") // TODO Person
                                Order.State.valueOf(rs.getString("STATE")),
                                rs.getDate("DELETED").toInstant())
                            : null;
                }
        ).toOptional();
    }

    @Override
    default Collection<Order> getList() {
        return executeQuery(
                "SELECT ID, NUMBER, DATE, CUSTOMER, STATE, DELETED FROM ORDER ",
                rs -> {
                    Map<Integer, Order> orderMap = new HashMap<>();
//                    Set<Integer> producerIds = new HashSet<>();
//                    while (rs.next()) {
//                        producerIds.add(rs.getInt("PRODUCER"));
//                    }
//                    ProducerDao producerDao = (ProducerDao) DbInitializer.getDaoByClass(Producer.class);
//                    Map<Integer, Producer> producerMap = producerDao.getMapByIds(producerIds);

                    rs.beforeFirst();
                    while (rs.next())
                        orderMap.put(rs.getInt("ID"),
                                new Order(
                                        rs.getInt("ID"),
                                        rs.getString("NUMBER"),
                                        ((Date) rs.getDate("DATE")).toInstant(),
                                        null, // rs.getInt("CUSTOMER") // TODO Person
                                        Order.State.valueOf(rs.getString("STATE")),
                                        ((Date) rs.getDate("DELETED")).toInstant()
                                ));

                    return orderMap;
                }).toOptional().orElse(Collections.emptyMap()).values();
    }

    @Override
    default Collection<Order> getListByIds(Collection<Integer> ids) { // TODO
        return getMapByIds(ids).values();
    }

    @Override
    default Map<Integer, Order> getMapByIds(Collection<Integer> ids) {
        return executeQuery(
                "SELECT ID, NUMBER, DATE, CUSTOMER, STATE, DELETED FROM STORE.ORDER " +
                        "WHERE ID IN (" + Helper.ArrayToString(ids) + ")",
                rs -> {
                    Map<Integer, Order> orderMap = new HashMap<>();
//                    Set<Integer> producerIds = new HashSet<>();
//                    while (rs.next()) {
//                        producerIds.add(rs.getInt("PRODUCER"));
//                    }
//                    ProducerDao producerDao = (ProducerDao) DbInitializer.getDaoByClass(Producer.class);
//                    Map<Integer, Producer> producerMap = producerDao.getMapByIds(producerIds);

                    rs.beforeFirst();
                    while (rs.next())
                        orderMap.put(rs.getInt("ID"),
                                new Order(
                                        rs.getInt("ID"),
                                        rs.getString("NUMBER"),
                                        ((Date) rs.getDate("DATE")).toInstant(),
                                        null, // rs.getInt("CUSTOMER") // TODO Person
                                        Order.State.valueOf(rs.getString("STATE")),
                                        ((Date) rs.getDate("DELETED")).toInstant()
                                ));

                    return orderMap;
                }).toOptional().orElse(Collections.emptyMap());
    }
}
