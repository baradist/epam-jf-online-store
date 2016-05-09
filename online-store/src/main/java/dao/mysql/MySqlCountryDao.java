package dao.mysql;

import common.functions.Helper;
import dao.interfaces.CountryDao;
import model.Country;

import java.util.*;

@FunctionalInterface
public interface MySqlCountryDao extends CountryDao {

    @Override
    default Optional<Country> getById(int id) { // TODO
        return executeQuery(
                "SELECT name, caliber FROM Gun WHERE id = " + id,
                rs -> rs.next()
                        ? new Country(id, rs.getString("NAME"))
                        : null
        ).toOptional();
    }

    @Override
    default Collection<Country> getListByIds(Collection<Integer> ids) { // TODO
        return getMapByIds(ids).values();
    }

    @Override
    default Map<Integer, Country> getMapByIds(Collection<Integer> ids) {
        return executeQuery(
                "SELECT ID, NAME FROM COUNTRY " +
                        "WHERE ID IN (" + Helper.ArrayToString(ids) + ")",
                rs -> {
                    Map<Integer, Country> countryMap = new HashMap<>();

                    while (rs.next())
                        countryMap.put(rs.getInt("ID"),
                                new Country(
                                        rs.getInt("ID"),
                                        rs.getString("NAME")
                                ));

                    return countryMap;
                }).toOptional().orElse(Collections.emptyMap());
    }
}
