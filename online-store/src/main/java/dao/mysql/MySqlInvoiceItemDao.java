package dao.mysql;

import common.functions.Helper;
import dao.dto.InvoiceItemDto;
import dao.interfaces.InvoiceItemDao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@FunctionalInterface
public interface MySqlInvoiceItemDao extends InvoiceItemDao {

    String SELECT = "SELECT id, invoice, good, quantity, price FROM invoice_item ";

    @Override
    default int getQuantity(int invoiceId) {
        return executeQuery(
                "SELECT count(*) AS count FROM invoice_item WHERE invoice = " + invoiceId,
                rs -> rs.next()
                        ? Integer.valueOf(rs.getInt("count"))
                        : 0
        ).toOptional().get();
    }

    @Override
    default Optional<InvoiceItemDto> getById(int id) {
        return executeQuery(
                SELECT + " WHERE id = " + id,
                rs -> rs.next()
                        ? getValue(rs)
                        : null
        ).toOptional();
    }

    @Override
    default Collection<InvoiceItemDto> getList() {
        return getList(-1, -1, 0);
    }

    @Override
    default Collection<InvoiceItemDto> getList(int invoiceId) {
        return getList(invoiceId, -1, 0);
    }

    @Override
    default Collection<InvoiceItemDto> getList(int invoiceId, int offset, int rows) {
        String sql = SELECT;
        if (invoiceId >= 0) {
            sql += " WHERE invoice = " + invoiceId;
        }
        if (offset >= 0 && rows > 0) {
            sql += " LIMIT " + offset + ", " + rows;
        }
        return executeQuery(
                sql,
                rs -> {
                    Map<Integer, InvoiceItemDto> map = new HashMap<>();
                    while (rs.next())
                        map.put(rs.getInt("id"),
                                getValue(rs));
                    return map;
                }).toOptional().orElse(Collections.emptyMap()).values();
    }

    @Override
    default Collection<InvoiceItemDto> getListByIds(Collection<Integer> ids) { // TODO
        return getMapByIds(ids).values();
    }

    @Override
    default Map<Integer, InvoiceItemDto> getMapByIds(Collection<Integer> ids) {
        return executeQuery(
                SELECT + " WHERE id IN (" + Helper.ArrayToString(ids) + ")",
                rs -> {
                    Map<Integer, InvoiceItemDto> map = new HashMap<>();
                    while (rs.next())
                        map.put(rs.getInt("id"),
                                getValue(rs));
                    return map;
                }).toOptional().orElse(Collections.emptyMap());
    }

    default InvoiceItemDto getValue(ResultSet rs) throws SQLException {
        return new InvoiceItemDto(
                rs.getInt("id"),
                rs.getInt("invoice"),
                rs.getInt("good"),
                rs.getDouble("quantity"),
                rs.getDouble("price")
        );
    }

    @Override
    default boolean add(InvoiceItemDto dto) {
        return withPreparedStatement(
                "INSERT INTO invoice_item (invoice, good, quantity, price) " +
                        "VALUES (?, ?, ?, ?)",
                preparedStatement -> {
                    setPreparedStatement(preparedStatement, dto);
                    return preparedStatement.executeUpdate() == 1;
                }).getOrThrowUnchecked();
    }

    @Override
    default boolean update(InvoiceItemDto dto) {
        return withPreparedStatement(
                "UPDATE invoice_item SET invoice = ?, good = ?, quantity = ?, price = ? WHERE id = ?"
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

    default void setPreparedStatement(PreparedStatement preparedStatement, InvoiceItemDto dto) throws SQLException {
        preparedStatement.setInt(1, dto.getInvoice());
        preparedStatement.setInt(2, dto.getGood());
        preparedStatement.setDouble(3, dto.getQuantity());
        preparedStatement.setDouble(4, dto.getPrice());
    }
}
