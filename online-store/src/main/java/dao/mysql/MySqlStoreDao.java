package dao.mysql;

import common.functions.Helper;
import dao.dto.StoreDto;
import dao.interfaces.StoreDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@FunctionalInterface
public interface MySqlStoreDao extends StoreDao {
    String SELECT = "SELECT id, name, address FROM store ";

    @Override
    default Optional<StoreDto> getById(int id) {
        return executeQuery(
                SELECT + " WHERE id = " + id,
                rs -> rs.next()
                        ? getValue(rs)
                        : null
        ).toOptional();
    }

    @Override
    default Collection<StoreDto> getListByIds(Collection<Integer> ids) { // TODO
        return getMapByIds(ids).values();
    }

    @Override
    default Collection<StoreDto> getList() {
        return executeQuery(
                SELECT,
                rs -> {
                    Map<Integer, StoreDto> map = new HashMap<>();
                    while (rs.next())
                        map.put(rs.getInt("id"),
                                getValue(rs));
                    return map;
                }).toOptional().orElse(Collections.emptyMap()).values();
    }

    @Override
    default Map<Integer, StoreDto> getMapByIds(Collection<Integer> ids) {
        return executeQuery(
                SELECT + " WHERE id IN (" + Helper.ArrayToString(ids) + ")",
                rs -> {
                    Map<Integer, StoreDto> map = new HashMap<>();
                    while (rs.next())
                        map.put(rs.getInt("id"),
                                getValue(rs));
                    return map;
                }).toOptional().orElse(Collections.emptyMap());
    }

    default StoreDto getValue(ResultSet rs) throws SQLException {
        return new StoreDto(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("address"));
    }
}
