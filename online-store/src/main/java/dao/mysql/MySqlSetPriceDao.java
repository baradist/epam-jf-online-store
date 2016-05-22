package dao.mysql;

import common.functions.Helper;
import dao.dto.SetPriceDto;
import dao.interfaces.SetPriceDao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@FunctionalInterface
public interface MySqlSetPriceDao extends SetPriceDao {

    String SELECT = "SELECT id, number, date, manager FROM set_price ";

    @Override
    default int getQuantity() {
        return executeQuery(
                "SELECT count(*) AS count FROM set_price",
                rs -> rs.next()
                        ? Integer.valueOf(rs.getInt("count"))
                        : 0
        ).toOptional().get();
    }

    @Override
    default Optional<SetPriceDto> getById(int id) {
        return executeQuery(
                SELECT + " WHERE id = " + id,
                rs -> rs.next()
                        ? getValue(rs)
                        : null
        ).toOptional();
    }

    @Override
    default Collection<SetPriceDto> getList() {
        return getList(-1, 0);
    }

    @Override
    default Collection<SetPriceDto> getList(int offset, int rows) {
        String sql = SELECT +
                " ORDER BY date ";
        if (offset >= 0 && rows > 0) {
            sql += " LIMIT " + offset + ", " + rows;
        }
        return executeQuery(
                sql,
                rs -> {
                    Map<Integer, SetPriceDto> map = new HashMap<>();
                    while (rs.next())
                        map.put(rs.getInt("id"),
                                getValue(rs));
                    return map;
                }).toOptional().orElse(Collections.emptyMap()).values();
    }

    @Override
    default Collection<SetPriceDto> getListByIds(Collection<Integer> ids) { // TODO
        return getMapByIds(ids).values();
    }

    @Override
    default Map<Integer, SetPriceDto> getMapByIds(Collection<Integer> ids) {
        return executeQuery(
                SELECT + " WHERE id IN (" + Helper.ArrayToString(ids) + ")",
                rs -> {
                    Map<Integer, SetPriceDto> map = new HashMap<>();
                    while (rs.next())
                        map.put(rs.getInt("id"),
                                getValue(rs));
                    return map;
                }).toOptional().orElse(Collections.emptyMap());
    }

    default SetPriceDto getValue(ResultSet rs) throws SQLException {
        return new SetPriceDto(
                rs.getInt("id"),
                rs.getString("number"),
                Helper.convertDateTime(rs.getString("date")),
                rs.getInt("manager")
        );
    }

    @Override
    default boolean add(SetPriceDto dto) {
        return withPreparedStatement(
                "INSERT INTO set_price (number, date, manager) " +
                        "VALUES (?, ?, ?)",
                preparedStatement -> {
                    setPreparedStatement(preparedStatement, dto);
                    return preparedStatement.executeUpdate() == 1;
                }).getOrThrowUnchecked();
    }

    @Override
    default boolean update(SetPriceDto dto) {
        return withPreparedStatement(
                "UPDATE set_price SET number = ?, date = ?, manager = ? " +
                        "WHERE id = ?"
                , preparedStatement -> {
                    setPreparedStatement(preparedStatement, dto);
                    preparedStatement.setInt(4, dto.getId());
                    return preparedStatement.executeUpdate() == 1;
                }).getOrThrowUnchecked();
    }

    @Override
    default boolean delete(int id) {
        return withPreparedStatement("DELETE FROM set_price WHERE id = ?", preparedStatement -> {
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() == 1;
        }).getOrThrowUnchecked();
    }

    default void setPreparedStatement(PreparedStatement preparedStatement, SetPriceDto dto) throws SQLException {
        preparedStatement.setString(1, dto.getNumber());
        preparedStatement.setString(2, Helper.convertDateTime(dto.getDate()));
        preparedStatement.setInt(3, dto.getManager());
    }
}
