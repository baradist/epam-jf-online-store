package dao.mysql;

import common.functions.Exceptional;
import common.functions.Helper;
import dao.dto.OrderDto;
import dao.interfaces.OrderDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;

@FunctionalInterface
public interface MySqlOrderDao extends OrderDao {
    String SELECT = "SELECT ID, NUMBER, DATE, CUSTOMER, STATE, DELETED FROM ORDER_ ";
    String PATTERN = "yyyy-MM-dd HH:mm:ss";

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
                SELECT,
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
        DateFormat format = new SimpleDateFormat(PATTERN);
        Instant date;
        Instant deleted = null;
        try {
            date = format.parse(rs.getString("DATE")).toInstant();
            java.sql.Date rsDeleted = rs.getDate("DELETED");
            if (rsDeleted != null) {
                deleted = format.parse(String.valueOf(rsDeleted)).toInstant();
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        OrderDto orderDto = new OrderDto(
                rs.getInt("ID"),
                rs.getString("NUMBER"),
                date,
                0, // rs.getInt("CUSTOMER") // TODO Person
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
                    // TODO: dateTime???
                    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(PATTERN);
                    Instant date = dto.getDate();
                    Date dt = Date.from(date);
                    String dateTime = sdf.format(dt);
                    preparedStatement.setString(2, dateTime);

                    preparedStatement.setInt(3, dto.getCustomer());
                    preparedStatement.setString(4, dto.getState());

                    Instant deleted = dto.getDeleted();
                    if (deleted != null) {
                        dateTime = sdf.format(Date.from(deleted));
                        preparedStatement.setString(5, dateTime);
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
                    // TODO: dateTime???
                    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(PATTERN);
                    String dateTime = sdf.format(dto.getDate());
                    preparedStatement.setString(2, dateTime);

                    preparedStatement.setInt(3, dto.getCustomer());
                    preparedStatement.setString(4, dto.getState());

                    dateTime = sdf.format(dto.getDeleted());
                    preparedStatement.setString(5, dateTime);

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
                SELECT + " WHERE CUSTOMER = " + personId,
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
}
