package dao.mysql;

import service.Helper;
import dao.dto.InvoiceDto;
import dao.interfaces.InvoiceDao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.Instant;
import java.util.*;

@FunctionalInterface
public interface MySqlInvoiceDao extends InvoiceDao {

    String SELECT = "SELECT id, number, date, supplier, store, sum, deleted, manager FROM invoice ";

    @Override
    default int getQuantity() {
        return executeQuery(
                "SELECT count(*) AS count FROM invoice",
                rs -> rs.next()
                        ? Integer.valueOf(rs.getInt("count"))
                        : 0
        ).toOptional().get();
    }

    @Override
    default Optional<InvoiceDto> getById(int id) {
        return executeQuery(
                SELECT + " WHERE id = " + id,
//                "SELECT i.id, i.number, i.date, i.supplier, i.store, SUM(ii.price * ii. quantity) AS sum, i.deleted, i.manager FROM invoice i\n" +
//                        "INNER JOIN invoice_item ii on ii.invoice = i.id\n" +
//                        "WHERE i.id = "+ id + " " +
//                        "GROUP BY i.id, i.number, i.date, i.supplier, i.store, i.deleted, i.manager",
                rs -> rs.next()
                        ? getValue(rs)
                        : null
        ).toOptional();
    }

    @Override
    default Collection<InvoiceDto> getList() {
        return getList(-1, 0);
    }

    @Override
    default Collection<InvoiceDto> getList(int offset, int rows) {
        String sql = SELECT +
                " ORDER BY date ";
//        String sql = "SELECT i.id, i.number, i.date, i.supplier, i.store, SUM(ii.price * ii. quantity) AS sum, i.deleted, i.manager " +
//                "FROM invoice i " +
//                "INNER JOIN invoice_item ii on ii.invoice = i.id " +
//                "GROUP BY i.id, i.number, i.date, i.supplier, i.store, i.deleted, i.manager";
        if (offset >= 0 && rows > 0) {
            sql += " LIMIT " + offset + ", " + rows;
        }
        return executeQuery(
                sql,
                rs -> {
                    Map<Integer, InvoiceDto> map = new HashMap<>();
                    while (rs.next())
                        map.put(rs.getInt("id"),
                                getValue(rs));
                    return map;
                }).toOptional().orElse(Collections.emptyMap()).values();
    }

    @Override
    default Collection<InvoiceDto> getListByIds(Collection<Integer> ids) { // TODO
        return getMapByIds(ids).values();
    }

    @Override
    default Map<Integer, InvoiceDto> getMapByIds(Collection<Integer> ids) {
        return executeQuery(
                SELECT + " WHERE id IN (" + Helper.ArrayToString(ids) + ")",
                rs -> {
                    Map<Integer, InvoiceDto> map = new HashMap<>();
                    while (rs.next())
                        map.put(rs.getInt("id"),
                                getValue(rs));
                    return map;
                }).toOptional().orElse(Collections.emptyMap());
    }

    default InvoiceDto getValue(ResultSet rs) throws SQLException {
        Instant deleted = null;
        String rsDeleted = rs.getString("deleted");
        if (rsDeleted != null) {
            deleted = Helper.convertDateTime(rsDeleted);
        }
        return new InvoiceDto(
                rs.getInt("id"),
                rs.getString("number"),
                Helper.convertDateTime(rs.getString("date")),
                rs.getInt("supplier"),
                rs.getInt("store"),
                rs.getDouble("sum"),
                deleted,
                rs.getInt("manager")
        );
    }

    @Override
    default boolean add(InvoiceDto dto) {
        return withPreparedStatement(
                "INSERT INTO invoice (number, date, supplier, store, sum, deleted, manager) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?)",
                preparedStatement -> {
                    setPreparedStatement(preparedStatement, dto);
                    return preparedStatement.executeUpdate() == 1;
                }).getOrThrowUnchecked();
    }

    @Override
    default boolean update(InvoiceDto dto) {
        return withPreparedStatement(
                "UPDATE invoice SET number = ?, date = ?, supplier = ?, store = ?, sum = ?, deleted = ?, manager = ? WHERE id = ?"
                , preparedStatement -> {
                    setPreparedStatement(preparedStatement, dto);
                    preparedStatement.setInt(8, dto.getId());
                    return preparedStatement.executeUpdate() == 1;
                }).getOrThrowUnchecked();
    }

    @Override
    default boolean delete(int id) {
        return withPreparedStatement("DELETE FROM invoice WHERE id = ?", preparedStatement -> {
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() == 1;
        }).getOrThrowUnchecked();
    }

    default void setPreparedStatement(PreparedStatement preparedStatement, InvoiceDto dto) throws SQLException {
        preparedStatement.setString(1, dto.getNumber());
        preparedStatement.setString(2, Helper.convertDateTime(dto.getDate()));
        preparedStatement.setInt(3, dto.getSupplier());
        preparedStatement.setInt(4, dto.getStore());
        preparedStatement.setDouble(5, dto.getSum());

        Instant deleted = dto.getDeleted();
        if (deleted != null) {
            preparedStatement.setString(6, Helper.convertDateTime(deleted));
        } else {
            preparedStatement.setNull(6, Types.NULL);
        }

        preparedStatement.setInt(7, dto.getManager());
    }
}
