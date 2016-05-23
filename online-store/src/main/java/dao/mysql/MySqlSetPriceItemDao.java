package dao.mysql;

import service.Helper;
import dao.dto.SetPriceItemDto;
import dao.interfaces.SetPriceItemDao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@FunctionalInterface
public interface MySqlSetPriceItemDao extends SetPriceItemDao {

    String SELECT = "SELECT id, set_price, lot, increase, price_sal FROM set_price_item ";

    @Override
    default int getQuantity(int invoiceId) {
        return executeQuery(
                "SELECT count(*) AS count FROM set_price_item",
                rs -> rs.next()
                        ? Integer.valueOf(rs.getInt("count"))
                        : 0
        ).toOptional().get();
    }

    @Override
    default Optional<SetPriceItemDto> getById(int id) {
        return executeQuery(
                SELECT + " WHERE id = " + id,
                rs -> rs.next()
                        ? getValue(rs)
                        : null
        ).toOptional();
    }

    @Override
    default Collection<SetPriceItemDto> getList() {
        return getList(-1, -1, 0);
    }

    @Override
    default Collection<SetPriceItemDto> getList(int setPriceId) {
        return getList(setPriceId, -1, 0);
    }

    @Override
    default Collection<SetPriceItemDto> getList(int setPriceId, int offset, int rows) {
        String sql = SELECT;
        if (setPriceId >= 0) {
            sql += " WHERE set_price = " + setPriceId;
        }
        if (offset >= 0 && rows > 0) {
            sql += " LIMIT " + offset + ", " + rows;
        }
        return executeQuery(
                sql,
                rs -> {
                    Map<Integer, SetPriceItemDto> map = new HashMap<>();
                    while (rs.next())
                        map.put(rs.getInt("id"),
                                getValue(rs));
                    return map;
                }).toOptional().orElse(Collections.emptyMap()).values();
    }

    @Override
    default Collection<SetPriceItemDto> getListByIds(Collection<Integer> ids) { // TODO
        return getMapByIds(ids).values();
    }

    @Override
    default Map<Integer, SetPriceItemDto> getMapByIds(Collection<Integer> ids) {
        return executeQuery(
                SELECT + " WHERE id IN (" + Helper.ArrayToString(ids) + ")",
                rs -> {
                    Map<Integer, SetPriceItemDto> map = new HashMap<>();
                    while (rs.next())
                        map.put(rs.getInt("id"),
                                getValue(rs));
                    return map;
                }).toOptional().orElse(Collections.emptyMap());
    }

    default SetPriceItemDto getValue(ResultSet rs) throws SQLException {
        return new SetPriceItemDto(
                rs.getInt("id"),
                rs.getInt("set_price"),
                rs.getInt("lot"),
                rs.getDouble("increase"),
                rs.getDouble("price_sal")
        );
    }

    @Override
    default boolean add(SetPriceItemDto dto) {
        return withPreparedStatement(
                "INSERT INTO set_price_item " +
                        "(set_price, lot, increase, price_sal) VALUES (?, ?, ?, ?)",
                preparedStatement -> {
                    setPreparedStatement(preparedStatement, dto);
                    return preparedStatement.executeUpdate() == 1;
                }).getOrThrowUnchecked();
    }

    @Override
    default boolean update(SetPriceItemDto dto) {
        return withPreparedStatement(
                "UPDATE set_price_item SET set_price = ?, lot = ?, increase = ?, price_sal = ? \n" +
                        "WHERE id = ?"
                , preparedStatement -> {
                    setPreparedStatement(preparedStatement, dto);
                    preparedStatement.setInt(5, dto.getId());
                    return preparedStatement.executeUpdate() == 1;
                }).getOrThrowUnchecked();
    }

    @Override
    default boolean delete(int id) {
        return withPreparedStatement("DELETE FROM invoice_item WHERE id = ?", preparedStatement -> {
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() == 1;
        }).getOrThrowUnchecked();
    }

    default void setPreparedStatement(PreparedStatement preparedStatement, SetPriceItemDto dto) throws SQLException {
        preparedStatement.setInt(1, dto.getSetPrice());
        preparedStatement.setInt(2, dto.getLot());
        preparedStatement.setDouble(3, dto.getIncrease());
        preparedStatement.setDouble(4, dto.getPriceSal());
    }
}
