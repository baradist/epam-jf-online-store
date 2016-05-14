package dao.mysql;

import common.functions.Helper;
import dao.dto.ProducerDto;
import dao.interfaces.ProducerDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@FunctionalInterface
public interface MySqlProducerDao extends ProducerDao {

    String SELECT = "SELECT ID, NAME, COUNTRY FROM PRODUCER ";

    @Override
    default Optional<ProducerDto> getById(int id) {
        return executeQuery(
                SELECT + " WHERE ID = " + id,
                rs -> rs.next()
                        ? getValue(rs)
                        : null
        ).toOptional();
    }

    @Override
    default Collection<ProducerDto> getList() {
        return executeQuery(
                SELECT,
                rs -> {
                    Map<Integer, ProducerDto> map = new HashMap<>();
                    while (rs.next())
                        map.put(rs.getInt("ID"),
                                getValue(rs));
                    return map;
                }).toOptional().orElse(Collections.emptyMap()).values();
    }

    @Override
    default Collection<ProducerDto> getListByIds(Collection<Integer> ids) { // TODO
        return getMapByIds(ids).values();
    }

    @Override
    default Map<Integer, ProducerDto> getMapByIds(Collection<Integer> ids) {
        return executeQuery(
                SELECT + "WHERE ID IN (" + Helper.ArrayToString(ids) + ")",
                rs -> {
                    Map<Integer, ProducerDto> map = new HashMap<>();
                    while (rs.next())
                        map.put(rs.getInt("ID"),
                                getValue(rs));

                    return map;
                }).toOptional().orElse(Collections.emptyMap());
    }

    default ProducerDto getValue(ResultSet rs) throws SQLException {
        return new ProducerDto(
                rs.getInt("ID"),
                rs.getString("NAME"),
                rs.getInt("COUNTRY"));
    }
}
