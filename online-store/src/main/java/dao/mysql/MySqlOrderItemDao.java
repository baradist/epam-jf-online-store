package dao.mysql;

import common.functions.Helper;
import dao.dto.OrderItemDto;
import dao.interfaces.OrderItemDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@FunctionalInterface
public interface MySqlOrderItemDao extends OrderItemDao {

    String SELECT = "SELECT id, order_, good, quantity, price FROM order_item ";

    @Override
    default Optional<OrderItemDto> getById(int id) {
        return executeQuery(
                SELECT + " WHERE id = " + id,
                rs -> rs.next()
                        ? getValue(rs)
                        : null
        ).toOptional();
    }

    @Override
    default Collection<OrderItemDto> getList() {
        return executeQuery(
                SELECT,
                rs -> {
                    Map<Integer, OrderItemDto> map = new HashMap<>();
                    while (rs.next())
                        map.put(rs.getInt("id"),
                                getValue(rs));
                    return map;
                }).toOptional().orElse(Collections.emptyMap()).values();
    }

    @Override
    default Collection<OrderItemDto> getListByIds(Collection<Integer> ids) { // TODO
        return getMapByIds(ids).values();
    }

    @Override
    default Map<Integer, OrderItemDto> getMapByIds(Collection<Integer> ids) {
        return executeQuery(
                SELECT + " WHERE ID IN (" + Helper.ArrayToString(ids) + ")",
                rs -> {
                    Map<Integer, OrderItemDto> map = new HashMap<>();
                    while (rs.next())
                        map.put(rs.getInt("ID"),
                                getValue(rs));

                    return map;
                }).toOptional().orElse(Collections.emptyMap());
    }

    default OrderItemDto getValue(ResultSet rs) throws SQLException {
        return new OrderItemDto(
                rs.getInt("id"),
                rs.getInt("invoice"),
                rs.getInt("good"),
                rs.getDouble("quantity"),
                rs.getDouble("price"));
    }

    @Override
    default boolean add(OrderItemDto orderItemDto) {
        return withPreparedStatement(
                "INSERT INTO order_item (order_, good, quantity, price) " +
                        "VALUES (?, ?, ?, ?)",
                preparedStatement -> {
                    preparedStatement.setInt(1, orderItemDto.getOrder());
                    preparedStatement.setInt(2, orderItemDto.getGood());
                    preparedStatement.setDouble(3, orderItemDto.getQuantity());
                    preparedStatement.setDouble(4, orderItemDto.getPrice());
                    return preparedStatement.executeUpdate() == 1;
                }).getOrThrowUnchecked();
    }

    @Override
    default boolean update(OrderItemDto orderItemDto) {
        return withPreparedStatement(
                "UPDATE order_item SET order_ = ?, good = ?, quantity = ?, price = ? " +
                        "WHERE id = ?", preparedStatement -> {
                    preparedStatement.setInt(1, orderItemDto.getOrder());
                    preparedStatement.setInt(2, orderItemDto.getGood());
                    preparedStatement.setDouble(3, orderItemDto.getQuantity());
                    preparedStatement.setDouble(4, orderItemDto.getPrice());
                    preparedStatement.setInt(5, orderItemDto.getId());
                    return preparedStatement.executeUpdate() == 1;
                }).getOrThrowUnchecked();
    }

    @Override
    default boolean delete(int id) {
        return withPreparedStatement("DELETE FROM order_item WHERE id = ?", preparedStatement -> {
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() == 1;
        }).getOrThrowUnchecked();
    }

    @Override
    default boolean deleteFromBasket(int orderId, int goodId) {
        return withPreparedStatement("DELETE FROM order_item WHERE order_ = ? AND good = ?",
                preparedStatement -> {
            preparedStatement.setInt(1, orderId);
            preparedStatement.setInt(2, goodId);
            return preparedStatement.executeUpdate() == 1;
        }).getOrThrowUnchecked();
    }
}
