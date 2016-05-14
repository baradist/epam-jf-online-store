package dao.mysql;

import common.functions.Helper;
import dao.dto.GoodDto;
import dao.interfaces.GoodDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@FunctionalInterface
public interface MySqlGoodDao extends GoodDao {

    String SELECT = "SELECT ID, NAME, PRODUCER, DESCRIPTION FROM GOOD";

    @Override
    default Optional<GoodDto> getById(int id) {
        return executeQuery(
                SELECT + " WHERE ID = " + id,
                rs -> rs.next()
                        ? getValue(rs)
                        : null
        ).toOptional();
    }

    @Override
    default Collection<GoodDto> getList() {
        return executeQuery(
                SELECT,
                rs -> {
                    Map<Integer, GoodDto> map = new HashMap<>();
                    while (rs.next())
                        map.put(rs.getInt("ID"),
                                getValue(rs));
                    return map;
                }).toOptional().orElse(Collections.emptyMap()).values();
    }

    @Override
    default Collection<GoodDto> getListByIds(Collection<Integer> ids) { // TODO
        return getMapByIds(ids).values();
    }

    @Override
    default Map<Integer, GoodDto> getMapByIds(Collection<Integer> ids) {
        return executeQuery(
                SELECT + " WHERE ID IN (" + Helper.ArrayToString(ids) + ")",
                rs -> {
                    Map<Integer, GoodDto> map = new HashMap<>();
                    while (rs.next())
                        map.put(rs.getInt("ID"),
                                getValue(rs));
                    return map;
                }).toOptional().orElse(Collections.emptyMap());
    }

    default GoodDto getValue(ResultSet rs) throws SQLException {
        return new GoodDto(
                rs.getInt("ID"), rs.getString("NAME"), rs.getInt("PRODUCER"), rs.getString("DESCRIPTION")
        );
    }
}
