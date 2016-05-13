package dao.mysql;

import common.functions.Helper;
import dao.dto.GoodDto;
import dao.interfaces.GoodDao;

import java.util.*;

@FunctionalInterface
public interface MySqlGoodDao extends GoodDao {

    @Override
    default Optional<GoodDto> getById(int id) {
        return executeQuery(
                "SELECT NAME, PRODUCER, DESCRIPTION FROM GOOD WHERE ID = " + id,
                rs -> rs.next()
                        ? new GoodDto(
                            id, rs.getString("NAME"), rs.getInt("PRODUCER"), rs.getString("DESCRIPTION"))
                        : null
        ).toOptional();
    }

    @Override
    default Collection<GoodDto> getList() {
        return executeQuery(
                "SELECT ID, NAME, PRODUCER, DESCRIPTION FROM GOOD ",
                rs -> {
                    Map<Integer, GoodDto> goodMap = new HashMap<>();
                    while (rs.next())
                        goodMap.put(rs.getInt("ID"),
                                new GoodDto(
                                        rs.getInt("ID"), rs.getString("NAME"), rs.getInt("PRODUCER"), rs.getString("DESCRIPTION")
                                ));
                    return goodMap;
                }).toOptional().orElse(Collections.emptyMap()).values();
    }

    @Override
    default Collection<GoodDto> getListByIds(Collection<Integer> ids) { // TODO
        return getMapByIds(ids).values();
    }

    @Override
    default Map<Integer, GoodDto> getMapByIds(Collection<Integer> ids) {
        return executeQuery(
                "SELECT ID, NAME, PRODUCER, DESCRIPTION FROM GOOD " +
                        "WHERE ID IN (" + Helper.ArrayToString(ids) + ")",
                rs -> {
                    Map<Integer, GoodDto> goodMap = new HashMap<>();
                    while (rs.next())
                        goodMap.put(rs.getInt("ID"),
                                new GoodDto(
                                        rs.getInt("ID"), rs.getString("NAME"), rs.getInt("PRODUCER"), rs.getString("DESCRIPTION")
                                ));
                    return goodMap;
                }).toOptional().orElse(Collections.emptyMap());
    }
}
