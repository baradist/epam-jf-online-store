package dao.mysql;

import common.functions.Helper;
import dao.interfaces.CountryDao;
import dao.interfaces.ProducerDao;
import listeners.DbInitializer;
import model.Country;
import model.Producer;

import java.util.*;

@FunctionalInterface
public interface MySqlProducerDao extends ProducerDao {

    @Override
    default Optional<Producer> getById(int id) {
        return executeQuery(
                "SELECT ID, NAME, COUNTRY FROM PRODUCER WHERE ID = " + id,
                rs -> {
                    rs.next();
                    CountryDao countryDao = (CountryDao) DbInitializer.getDaoByClass(Country.class);
                    Optional<Country> countryOptional = countryDao.getById(rs.getInt("COUNTRY"));
                    rs.beforeFirst();

                    return rs.next()
                            ? new Producer(id, rs.getString("NAME"), countryOptional.get())
                            : null;
                }
        ).toOptional();
    }

    @Override
    default Collection<Producer> getList() {
        return executeQuery(
                "SELECT ID, NAME, COUNTRY FROM PRODUCER ",
                rs -> {
                    Map<Integer, Producer> producerMap = new HashMap<>();
                    Set<Integer> countryIds = new HashSet<>();
                    while (rs.next()) {
                        countryIds.add(rs.getInt("COUNTRY"));
                    }
                    CountryDao countryDao = (CountryDao) DbInitializer.getDaoByClass(Country.class);
                    Map<Integer, Country> countryMap = countryDao.getMapByIds(countryIds);

                    rs.beforeFirst();
                    while (rs.next())
                        producerMap.put(rs.getInt("ID"),
                                new Producer(
                                        rs.getInt("ID"),
                                        rs.getString("NAME"),
                                        countryMap.get(rs.getInt("COUNTRY"))
                                ));

                    return producerMap;
                }).toOptional().orElse(Collections.emptyMap()).values();
    }

    @Override
    default Collection<Producer> getListByIds(Collection<Integer> ids) { // TODO
        return getMapByIds(ids).values();
    }

    @Override
    default Map<Integer, Producer> getMapByIds(Collection<Integer> ids) {
        return executeQuery(
                "SELECT ID, NAME, COUNTRY FROM PRODUCER " +
                        "WHERE ID IN (" + Helper.ArrayToString(ids) + ")",
                rs -> {
                    Map<Integer, Producer> producerMap = new HashMap<>();
                    Set<Integer> countryIds = new HashSet<>();
                    while (rs.next()) {
                        countryIds.add(rs.getInt("COUNTRY"));
                    }
                    CountryDao countryDao = (CountryDao) DbInitializer.getDaoByClass(Country.class);
                    Map<Integer, Country> countryMap = countryDao.getMapByIds(countryIds);

                    rs.beforeFirst();
                    while (rs.next())
                        producerMap.put(rs.getInt("ID"),
                                new Producer(
                                        rs.getInt("ID"),
                                        rs.getString("NAME"),
                                        countryMap.get(rs.getInt("COUNTRY"))
                                ));

                    return producerMap;
                }).toOptional().orElse(Collections.emptyMap());
    }
}
