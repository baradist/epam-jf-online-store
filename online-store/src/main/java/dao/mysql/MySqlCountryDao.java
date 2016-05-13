package dao.mysql;

import common.functions.Helper;
import dao.dto.CountryDto;
import dao.interfaces.CountryDao;

import java.util.*;

@FunctionalInterface
public interface MySqlCountryDao extends CountryDao {

    @Override
    default Optional<CountryDto> getById(int id) {
        return executeQuery(
                "SELECT ID, NAME FROM COUNTRY WHERE ID = " + id,
                rs -> rs.next()
                        ? new CountryDto(id, rs.getString("NAME"))
                        : null
        ).toOptional();
    }

    @Override
    default Collection<CountryDto> getListByIds(Collection<Integer> ids) { // TODO
        return getMapByIds(ids).values();
    }

    @Override
    default Collection<CountryDto> getList() {
        return executeQuery(
                "SELECT ID, NAME FROM COUNTRY ",
                rs -> {
                    Map<Integer, CountryDto> countryMap = new HashMap<>();
                    while (rs.next())
                        countryMap.put(rs.getInt("ID"),
                                new CountryDto(
                                        rs.getInt("ID"),
                                        rs.getString("NAME")
                                ));
                    return countryMap;
                }).toOptional().orElse(Collections.emptyMap()).values();
    }

    @Override
    default Map<Integer, CountryDto> getMapByIds(Collection<Integer> ids) {
        return executeQuery(
                "SELECT ID, NAME FROM COUNTRY " +
                        "WHERE ID IN (" + Helper.ArrayToString(ids) + ")",
                rs -> {
                    Map<Integer, CountryDto> countryMap = new HashMap<>();
                    while (rs.next())
                        countryMap.put(rs.getInt("ID"),
                                new CountryDto(
                                        rs.getInt("ID"),
                                        rs.getString("NAME")
                                ));
                    return countryMap;
                }).toOptional().orElse(Collections.emptyMap());
    }
}
