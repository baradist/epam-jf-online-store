package dao.mysql;

import common.functions.Helper;
import dao.dto.GoodDto;
import dao.interfaces.GoodDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@FunctionalInterface
public interface MySqlGoodDao extends GoodDao {

    String SELECT = "SELECT id, name, producer, description FROM good";

    @Override
    default int getQuantity() {
        return executeQuery(
                "SELECT count(*) AS count FROM good",
                rs -> rs.next()
                        ? Integer.valueOf(rs.getInt("count"))
                        : 0
        ).toOptional().get();
    }

    @Override
    default Optional<GoodDto> getById(int id) {
        return executeQuery(
                SELECT + " WHERE id = " + id,
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
                        map.put(rs.getInt("id"),
                                getValue(rs));
                    return map;
                }).toOptional().orElse(Collections.emptyMap()).values();
    }

    @Override
    default Collection<GoodDto> getList(int start, int end) {
        return getList(); // TODO
    }

    @Override
    default Collection<GoodDto> getListByIds(Collection<Integer> ids) { // TODO
        return getMapByIds(ids).values();
    }

    @Override
    default Map<Integer, GoodDto> getMapByIds(Collection<Integer> ids) {
        return executeQuery(
                SELECT + " WHERE id IN (" + Helper.ArrayToString(ids) + ")",
                rs -> {
                    Map<Integer, GoodDto> map = new HashMap<>();
                    while (rs.next())
                        map.put(rs.getInt("id"),
                                getValue(rs));
                    return map;
                }).toOptional().orElse(Collections.emptyMap());
    }

    default GoodDto getValue(ResultSet rs) throws SQLException {
        return new GoodDto(
                rs.getInt("id"), rs.getString("name"), rs.getInt("producer"), rs.getString("description")
        );
    }

    @Override
    default boolean add(GoodDto goodDto) {
        return withPreparedStatement(
                "INSERT INTO GOOD (NAME, PRODUCER, DESCRIPTION) " +
                        "VALUES (?, ?, ?)",
                preparedStatement -> {
                    preparedStatement.setString(1, goodDto.getName());
                    preparedStatement.setInt(2, goodDto.getProducer());
                    preparedStatement.setString(3, goodDto.getDescription());
                    return preparedStatement.executeUpdate() == 1;
                }).getOrThrowUnchecked();
    }

    @Override
    default boolean update(GoodDto goodDto) {
        return withPreparedStatement(
                "UPDATE GOOD SET NAME = ?, PRODUCER = ?, DESCRIPTION = ? " +
                        "WHERE ID = ?"
                , preparedStatement -> {
                    preparedStatement.setString(1, goodDto.getName());
                    preparedStatement.setInt(2, goodDto.getProducer());
                    preparedStatement.setString(3, goodDto.getDescription());
                    preparedStatement.setInt(4, goodDto.getId());
                    return preparedStatement.executeUpdate() == 1;
                }).getOrThrowUnchecked();
    }

    @Override
    default boolean delete(int id) {
        return withPreparedStatement("DELETE FROM GOOD WHERE ID = ?", preparedStatement -> {
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() == 1;
        }).getOrThrowUnchecked();
    }
}
