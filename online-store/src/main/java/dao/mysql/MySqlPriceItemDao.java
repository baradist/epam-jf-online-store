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

    String SELECT = "SELECT ID, STORE, GOOD, DOCUMENT_IN, DOCUMENT_IN_ITEM, QUANTITY, QUANTITY_REST, SUPPLIER, PRICE_SUP, PRICE_SAL FROM LOT ";

    @Override
    default Optional<PriceItemDto> getById(int id) {
        return executeQuery(
                SELECT +
                        " WHERE ID = " + id,
                rs -> rs.next()
                        ? getValue(rs)
                        : null
        ).toOptional();
    }

    @Override
    default Collection<PriceItemDto> getList() {
        return executeQuery(
                // TODO: o.customer = 7 AND o.state = 'NEW'
                "SELECT \n" +
                        "l.good,\n" +
                        "l.quantity_rest AS quantity,\n" +
                        "oi.quantity AS quantity_ordered,\n" +
                        "l.price_sal AS price\n" +
                        "FROM lot l\n" +
                        "LEFT OUTER JOIN (SELECT good, quantity\n" +
                        "   FROM order_item WHERE order_ = (SELECT id FROM order_ WHERE deleted IS NULL AND customer = 7 AND state = 'NEW' ORDER BY id DESC LIMIT 1)\n" +
                        "   ) oi ON l.good = oi.good\n" +
                        "WHERE l.quantity_rest > 0",
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
