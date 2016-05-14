package dao.mysql;

import common.functions.Helper;
import dao.dto.OrderDto;
import dao.interfaces.OrderDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@FunctionalInterface
public interface MySqlOrderDao extends OrderDao {
    String SELECT = "SELECT NUMBER, DATE, CUSTOMER, STATE, DELETED FROM ORDER_ ";

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
        return new OrderDto(
                rs.getInt("ID"),
                rs.getString("NUMBER"),
                rs.getDate("DATE").toInstant(),
                0, // rs.getInt("CUSTOMER") // TODO Person
                rs.getString("STATE"),
                rs.getDate("DELETED").toInstant()
        );
    }

    @Override
    default boolean add(OrderDto dto) {
        return withPreparedStatement(
                "INSERT INTO order_ (number, date, customer, state, deleted) " +
                        "VALUES (?, ?, ?, ?, ?)"
//                "INSERT INTO GOOD (NAME, PRODUCER, DESCRIPTION) " +
//                        "VALUES (?, ?, ?)"
                ,
                preparedStatement -> {
                    preparedStatement.setString(1, dto.getNumber());
                    // TODO: dateTime???
                    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String dateTime = sdf.format(dto.getDate());
                    preparedStatement.setString(2, dateTime);

                    preparedStatement.setInt(3, dto.getCustomer());
                    preparedStatement.setString(4, dto.getState());

                    dateTime = sdf.format(dto.getDeleted());
                    preparedStatement.setString(5, dateTime);

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
                    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
}
