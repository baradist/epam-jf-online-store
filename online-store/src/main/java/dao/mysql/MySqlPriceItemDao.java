package dao.mysql;

import dao.dto.PriceItemDto;
import dao.interfaces.PriceItemDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@FunctionalInterface
public interface MySqlPriceItemDao extends PriceItemDao {

    String SELECT = "SELECT id, store, good, document_in, document_in_item, quantity, quantity_rest, supplier, price_sup, price_sal FROM lot ";

    @Override
    default int getQuantity() {
        return executeQuery(
                "SELECT count(*) AS count FROM lot WHERE quantity_rest > 0",
                rs -> rs.next()
                        ? Integer.valueOf(rs.getInt("count"))
                        : 0
        ).toOptional().get();
    }

    @Override
    default Optional<PriceItemDto> getById(int id) {
        return executeQuery(
                SELECT +
                        " WHERE id = " + id,
                rs -> rs.next()
                        ? getValue(rs)
                        : null
        ).toOptional();
    }

    @Override
    default Collection<PriceItemDto> getList() {
        return getList(-1, 0);
    }

    @Override
    default Collection<PriceItemDto> getList(int offset, int rows) {
        String sql = "SELECT \n" +
                "  l.good,\n" +
                "  l.quantity_rest AS quantity,\n" +
                "  0 AS quantity_ordered,\n" +
                "  l.price_sal AS price\n" +
                "FROM lot l\n" +
                "INNER JOIN good g ON l.good = g.id\n" +
                "WHERE l.quantity_rest > 0\n" +
                "ORDER BY g.name ";
        if (offset >= 0 && rows > 0) {
            sql += " LIMIT " + offset + ", " + rows;
        }
        return executeQuery(
                sql,
                rs -> {
                    Collection<PriceItemDto> map = new ArrayList<>();
                    while (rs.next())
                        map.add(getValue(rs));
                    return map;
                }).toOptional().orElse(Collections.emptySet());
    }

    @Override
    default Collection<PriceItemDto> getListForPersonsEmail(String email) {
        return getListForPersonsEmail(email, -1, 0);
    }

    @Override
    default Collection<PriceItemDto> getListForPersonsEmail(String email, int offset, int rows) {
        String sql = "SELECT \n" +
                "  l.good,\n" +
                "  l.quantity_rest AS quantity,\n" +
                "  oi.quantity AS quantity_ordered,\n" +
                "  l.price_sal AS price\n" +
                "FROM lot l\n" +
                "INNER JOIN good g ON l.good = g.id\n" +
                "LEFT OUTER JOIN (SELECT good, quantity\n" +
                "  FROM order_item WHERE order_ = (\n" +
                "      SELECT o.id FROM order_ o\n" +
                "      INNER JOIN person p on p.id = o.customer\n" +
                "      WHERE deleted IS NULL AND p.email = '" + email + "' AND state = 'NEW' ORDER BY o.id DESC LIMIT 1\n" +
                "    )\n" +
                "  ) oi ON l.good = oi.good\n" +
                "WHERE l.quantity_rest > 0\n" +
                "ORDER BY g.name\n";
        if (offset >= 0 && rows > 0) {
            sql += " LIMIT " + offset + ", " + rows;
        }
        return executeQuery(
                sql,
                rs -> {
                    Collection<PriceItemDto> map = new ArrayList<>();
                    while (rs.next())
                        map.add(getValue(rs));
                    return map;
                }).toOptional().orElse(Collections.emptySet());
    }

    default PriceItemDto getValue(ResultSet rs) throws SQLException {
        return new PriceItemDto(
//                rs.getInt("ID"),
                rs.getInt("good"),
                rs.getDouble("quantity"),
                rs.getDouble("quantity_ordered"),
                rs.getDouble("price"));
    }
}
