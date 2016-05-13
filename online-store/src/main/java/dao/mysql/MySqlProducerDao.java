package dao.mysql;

import common.functions.Helper;
import dao.dto.ProducerDto;
import dao.interfaces.ProducerDao;

import java.util.*;

@FunctionalInterface
public interface MySqlProducerDao extends ProducerDao {

    @Override
    default Optional<ProducerDto> getById(int id) {
        return executeQuery(
                "SELECT ID, NAME, COUNTRY FROM PRODUCER WHERE ID = " + id,
                rs -> rs.next()
                        ? new ProducerDto(id, rs.getString("NAME"), rs.getInt("COUNTRY"))
                        : null
        ).toOptional();
    }

    @Override
    default Collection<ProducerDto> getList() {
        return executeQuery(
                "SELECT ID, NAME, COUNTRY FROM PRODUCER ",
                rs -> {
                    Map<Integer, ProducerDto> producerMap = new HashMap<>();
                    while (rs.next())
                        producerMap.put(rs.getInt("ID"),
                                new ProducerDto(
                                        rs.getInt("ID"),
                                        rs.getString("NAME"),
                                        rs.getInt("COUNTRY")));
                    return producerMap;
                }).toOptional().orElse(Collections.emptyMap()).values();
    }

    @Override
    default Collection<ProducerDto> getListByIds(Collection<Integer> ids) { // TODO
        return getMapByIds(ids).values();
    }

    @Override
    default Map<Integer, ProducerDto> getMapByIds(Collection<Integer> ids) {
        return executeQuery(
                "SELECT ID, NAME, COUNTRY FROM PRODUCER " +
                        "WHERE ID IN (" + Helper.ArrayToString(ids) + ")",
                rs -> {
                    Map<Integer, ProducerDto> producerMap = new HashMap<>();
                    while (rs.next())
                        producerMap.put(rs.getInt("ID"),
                                new ProducerDto(
                                        rs.getInt("ID"),
                                        rs.getString("NAME"),
                                        rs.getInt("COUNTRY")
                                ));

                    return producerMap;
                }).toOptional().orElse(Collections.emptyMap());
    }
}
