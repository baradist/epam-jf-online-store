package dao.mysql;

import common.functions.Exceptional;
import service.Helper;
import dao.dto.OrderDto;
import dao.interfaces.OrderDao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.Instant;
import java.util.*;

/**
 * Created by Oleg Grigorjev 
 */
 
@FunctionalInterface
public interface MySqlOrderDao extends OrderDao {
    String SELECT = "SELECT id, number, date, customer, state, sum, deleted FROM order_ ";

    @Override
    default Optional<OrderDto> getById(int id) {
        return executeQuery(
                SELECT + " WHERE id = " + id,
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
                        map.put(rs.getInt("id"),
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
                SELECT + " WHERE id IN (" + Helper.ArrayToString(ids) + ")",
                rs -> {
                    Map<Integer, OrderDto> map = new HashMap<>();
                    while (rs.next())
                        map.put(rs.getInt("id"),
                                getValue(rs));
                    return map;
                }).toOptional().orElse(Collections.emptyMap());
    }

    default OrderDto getValue(ResultSet rs) throws SQLException {
        OrderDto orderDto = new OrderDto(
                rs.getInt("id"),
                rs.getString("number"),
                Helper.convertDateTime(rs.getString("date")),
                rs.getInt("customer"),
                rs.getString("state"),
                rs.getDouble("sum"),
                Helper.convertDateTime(rs.getString("deleted"))
        );
        return orderDto;
    }

    @Override
    default boolean add(OrderDto dto) {
        return withPreparedStatement(
                "INSERT INTO order_ (number, date, customer, state, deleted) " +
                        "VALUES (?, ?, ?, ?, ?)",
                preparedStatement -> {
                    setPreparedStatement(dto, preparedStatement);

                    return preparedStatement.executeUpdate() == 1;
                }).getOrThrowUnchecked();
    }

    @Override
    default boolean update(OrderDto dto) {
        return withPreparedStatement(
                "UPDATE order_ SET number = ?, date = ?, customer = ?, state = ?, deleted = ? " +
                        "WHERE id = ?"
                , preparedStatement -> {
                    setPreparedStatement(dto, preparedStatement);
                    preparedStatement.setInt(6, dto.getId());
                    return preparedStatement.executeUpdate() == 1;
                }).getOrThrowUnchecked();
    }

    default void setPreparedStatement(OrderDto dto, PreparedStatement preparedStatement) throws SQLException {
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
    }

    @Override
    default boolean delete(int id) {
        return withPreparedStatement("DELETE FROM order_ WHERE id = ?", preparedStatement -> {
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() == 1;
        }).getOrThrowUnchecked();
    }

    @Override
    default Optional<OrderDto> getPersonsBasket(int personId) {
        return executeQuery(
                SELECT + " WHERE deleted IS NULL AND state = 'NEW' AND customer = " + personId + " ORDER BY id DESC",
                rs -> rs.next()
                        ? getValue(rs)
                        : null
        ).toOptional();
    }

    @Override
    default Optional<OrderDto> getPersonsBasket(String email) {
        return executeQuery(
                "SELECT o.id, o.number, o.date, o.customer, o.state, o.sum, o.deleted FROM order_ o " +
                        "INNER JOIN person p on o.customer = p.id WHERE deleted IS NULL AND state = 'NEW' AND p.email = '"
                        + email + "' ORDER BY o.id DESC",
                rs -> rs.next()
                        ? MySqlOrderDao.this.getValue(rs)
                        : null
        ).toOptional();
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

    @Override
    default Optional<Helper.TwoValues<Float, Float>> getPersonsBasketQuantityAndSum(String email) {
        return executeQuery(
                "SELECT SUM(oo.quantity * oo.price) AS basket_sum, SUM(oo.quantity) AS basket_quantity FROM order_item oo\n" +
                        "INNER JOIN (\n" +
                        "\tSELECT o.id FROM order_ o INNER JOIN person p on o.customer = p.id  \n" +
                        "\tWHERE deleted IS NULL AND state = 'NEW' AND p.email = '" + email + "' ORDER BY o.id DESC LIMIT 1 \n" +
                        ") o_id ON oo.order_ = o_id.id",
                rs -> rs.next()
                        ? new Helper.TwoValues<>(rs.getFloat("basket_quantity"), rs.getFloat("basket_sum"))
                        : null
        ).toOptional();
    }

    @Override
    default Collection<OrderDto> getListByEmailAndState(String email, String state) {
        return executeQuery(
                SELECT + " WHERE deleted IS NULL AND state = '" + state + "' AND customer = (SELECT id FROM person WHERE email = '" + email + "')",
                rs -> {
                    Map<Integer, OrderDto> map = new HashMap<>();
                    while (rs.next())
                        map.put(rs.getInt("id"),
                                getValue(rs));
                    return map;
                }).toOptional().orElse(Collections.emptyMap()).values();
    }
}
