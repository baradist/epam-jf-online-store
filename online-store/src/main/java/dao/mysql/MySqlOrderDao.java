package dao.mysql;

import common.functions.Exceptional;
import common.functions.Helper;
import dao.dto.OrderDto;
import dao.interfaces.OrderDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.Instant;
import java.util.*;

@FunctionalInterface
public interface MySqlOrderDao extends OrderDao {
    String SELECT = "SELECT id, number, date, customer, state, deleted FROM order_ ";

    @Override
    default Optional<OrderDto> getById(int id) {
        return executeQuery(
                SELECT + " WHERE ID = " + id,
                rs -> rs.next()
                        ? getValue(rs)
                        : null
        ).toOptional();
    }

    @Override
    default Collection<OrderDto> getList() {
        return executeQuery(
                SELECT /*+ " WHERE deleted IS NULL "*/,
                rs -> {
                    Map<Integer, OrderDto> map = new HashMap<>();
                    while (rs.next())
                        map.put(rs.getInt("ID"),
                                getValue(rs));
                    return map;
                }).toOptional().orElse(Collections.emptyMap()).values();
    }

    @Override
    default Collection<OrderDto> getListByIds(Collection<Integer> ids) { // TODO
        return getMapByIds(ids).values();
    }

    @Override
    default Map<Integer, OrderDto> getMapByIds(Collection<Integer> ids) {
        return executeQuery(
                SELECT + " WHERE ID IN (" + Helper.ArrayToString(ids) + ")",
                rs -> {
                    Map<Integer, OrderDto> map = new HashMap<>();
                    while (rs.next())
                        map.put(rs.getInt("ID"),
                                getValue(rs));
                    return map;
                }).toOptional().orElse(Collections.emptyMap());
    }

    default OrderDto getValue(ResultSet rs) throws SQLException {
        Instant deleted = null;
            String rsDeleted = rs.getString("DELETED");
            if (rsDeleted != null) {
                deleted = Helper.convertDateTime(rsDeleted);
            }

        OrderDto orderDto = new OrderDto(
                rs.getInt("ID"),
                rs.getString("NUMBER"),
                Helper.convertDateTime(rs.getString("DATE")),
                rs.getInt("CUSTOMER"),
                rs.getString("STATE"),
                deleted
        );
        return orderDto;
    }

    @Override
    default boolean add(OrderDto dto) {
        return withPreparedStatement(
                "INSERT INTO order_ (number, date, customer, state, deleted) " +
                        "VALUES (?, ?, ?, ?, ?)",
                preparedStatement -> {
                    preparedStatement.setString(1, dto.getNumber());
                    preparedStatement.setString(2, Helper.convertDateTime(dto.getDate()));

                    preparedStatement.setInt(3, dto.getCustomer());
                    preparedStatement.setString(4, dto.getState());

                    Instant deleted = dto.getDeleted();
                    if (deleted != null) {
                        preparedStatement.setString(5, Helper.convertDateTime(deleted));
                    } else {
                        preparedStatement.setNull(5, Types.NULL);
                    }

                    return preparedStatement.executeUpdate() == 1;
                }).getOrThrowUnchecked();
    }

    @Override
    default boolean update(OrderDto dto) {
        return withPreparedStatement(
                "UPDATE order_ SET number = ?, date = ?, customer = ?, state = ?, deleted = ? " +
                        "WHERE id = ?"
                , preparedStatement -> {
                    preparedStatement.setString(1, dto.getNumber());
                    preparedStatement.setString(2, Helper.convertDateTime(dto.getDate()));
                    preparedStatement.setInt(3, dto.getCustomer());
                    preparedStatement.setString(4, dto.getState());
                    preparedStatement.setString(5, Helper.convertDateTime(dto.getDeleted()));
                    preparedStatement.setInt(6, dto.getId());
                    return preparedStatement.executeUpdate() == 1;
                }).getOrThrowUnchecked();
    }

    @Override
    default boolean delete(int id) {
        return withPreparedStatement("DELETE FROM ORDER_ WHERE ID = ?", preparedStatement -> {
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() == 1;
        }).getOrThrowUnchecked();
    }

    @Override
    default Optional<OrderDto> getPersonsBasket(int personId) {
        Exceptional<OrderDto, SQLException> orderDtoSQLExceptionExceptional = executeQuery(
                SELECT + " WHERE deleted IS NULL AND CUSTOMER = " + personId + " ORDER BY id DESC",
                rs -> rs.next()
                        ? MySqlOrderDao.this.getValue(rs)
                        : null
        );
        if (orderDtoSQLExceptionExceptional == null) {

        }
        return orderDtoSQLExceptionExceptional.toOptional();
    }

    @Override
    default Optional<OrderDto> getPersonsBasket(String email) {
        Exceptional<OrderDto, SQLException> orderDtoSQLExceptionExceptional = executeQuery(
                "SELECT o.id, o.number, o.date, o.customer, o.state, o.deleted FROM order_ o " +
                        "INNER JOIN person p on o.customer = p.id WHERE deleted IS NULL AND p.email = '"
                + email + "' ORDER BY o.id DESC",
                rs -> rs.next()
                        ? MySqlOrderDao.this.getValue(rs)
                        : null
        );
        if (orderDtoSQLExceptionExceptional == null) {

        }
        return orderDtoSQLExceptionExceptional.toOptional();
    }

    @Override
    default boolean deleteIfEmpty(int orderId) { // TODO
        return withPreparedStatement(
                "SELECT o.id " +
                "FROM order_ o " +
                "LEFT OUTER JOIN order_item oi ON o.id = oi.order_ " +
                "WHERE o.id = ? AND oi.order_ IS NULL",
                preparedStatement -> {
            preparedStatement.setInt(1, orderId);
            return preparedStatement.executeUpdate() == 1;
        }).getOrThrowUnchecked();
    }

    @Override
    default boolean markDeleted(int orderId) {
        return withPreparedStatement(
                "UPDATE order_ SET deleted = ? " +
                        "WHERE id = ?"
                , preparedStatement -> {

                    preparedStatement.setString(1, Helper.convertDateTime(Instant.now()));
                    preparedStatement.setInt(2, orderId);
                    return preparedStatement.executeUpdate() == 1;
                }).getOrThrowUnchecked();
    }
}
