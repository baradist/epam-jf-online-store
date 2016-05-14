package dao.mysql;

import common.functions.Helper;
import dao.dto.CountryDto;
import dao.interfaces.CountryDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@FunctionalInterface
public interface MySqlCountryDao extends CountryDao {

    String SELECT = "SELECT ID, NAME FROM COUNTRY ";

    @Override
    default Optional<CountryDto> getById(int id) {
        return executeQuery(
                SELECT + " WHERE ID = " + id,
                rs -> rs.next()
                        ? getValue(rs)
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
                SELECT,
                rs -> {
                    Map<Integer, CountryDto> map = new HashMap<>();
                    while (rs.next())
                        map.put(rs.getInt("ID"),
                                getValue(rs));
                    return map;
                }).toOptional().orElse(Collections.emptyMap()).values();
    }

    @Override
    default Map<Integer, CountryDto> getMapByIds(Collection<Integer> ids) {
        return executeQuery(
                SELECT + " WHERE ID IN (" + Helper.ArrayToString(ids) + ")",
                rs -> {
                    Map<Integer, CountryDto> map = new HashMap<>();
                    while (rs.next())
                        map.put(rs.getInt("ID"),
                                getValue(rs));
                    return map;
                }).toOptional().orElse(Collections.emptyMap());
    }

    default CountryDto getValue(ResultSet rs) throws SQLException {
        return new CountryDto(
                rs.getInt("ID"),
                rs.getString("NAME")
        );
    }
}
