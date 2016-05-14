package dao.mysql;

import dao.dto.LotDto;
import dao.interfaces.LotDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@FunctionalInterface
public interface MySqlLotDao extends LotDao {

    String SELECT = "SELECT ID, STORE, GOOD, DOCUMENT_IN, DOCUMENT_IN_ITEM, QUANTITY, QUANTITY_REST, SUPPLIER, PRICE_SUP, PRICE_SAL FROM LOT ";

    @Override
    default Optional<LotDto> getById(int id) {
        return executeQuery(
                SELECT +
                        " WHERE ID = " + id,
                rs -> rs.next()
                        ? getValue(rs)
                        : null
        ).toOptional();
    }

    @Override
    default Collection<LotDto> getList() {
        return executeQuery(
                SELECT + "WHERE QUANTITY_REST > 0",
                rs -> {
                    Collection<LotDto> map = new ArrayList<>();
                    while (rs.next())
                        map.add(getValue(rs));
                    return map;
                }).toOptional().orElse(Collections.emptySet());
    }

    default LotDto getValue(ResultSet rs) throws SQLException {
        return new LotDto(
                rs.getInt("ID"),
                rs.getInt("STORE"),
                rs.getInt("GOOD"),
                0, // invoice
                0, // invoiceItem
                rs.getDouble("QUANTITY"),
                rs.getDouble("QUANTITY_REST"),
                0, //new Contractor()
                rs.getDouble("PRICE_SUP"),
                rs.getDouble("PRICE_SAL"));
    }
}
