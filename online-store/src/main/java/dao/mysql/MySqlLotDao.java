package dao.mysql;

import dao.dto.LotDto;
import dao.interfaces.LotDao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@FunctionalInterface
public interface MySqlLotDao extends LotDao {

    @Override
    default Optional<LotDto> getById(int id) {
        return executeQuery(
                "SELECT STORE, GOOD, DOCUMENT_IN, DOCUMENT_IN_ITEM, QUANTITY, QUANTITY_REST, SUPPLIER, PRICE_SUP, PRICE_SAL FROM LOT " +
                        "WHERE ID = " + id,
                rs -> rs.next()
                        ? new LotDto(
                        id,
                        rs.getInt("STORE"),
                        rs.getInt("GOOD"),
                        0, // invoice
                        0, // invoiceItem
                        rs.getDouble("QUANTITY"),
                        rs.getDouble("QUANTITY_REST"),
                        0, //new Contractor()
                        rs.getDouble("PRICE_SUP"),
                        rs.getDouble("PRICE_SAL"))
                        : null
        ).toOptional();
    }

    @Override
    default Collection<LotDto> getList() {
        return executeQuery(
                "SELECT ID, STORE, GOOD, DOCUMENT_IN, DOCUMENT_IN_ITEM, QUANTITY, QUANTITY_REST, SUPPLIER, PRICE_SUP, PRICE_SAL FROM LOT " +
                        "WHERE QUANTITY_REST > 0",
                rs -> {
                    Collection<LotDto> lots = new ArrayList<>();
                    while (rs.next())
                        lots.add(new LotDto(
                                rs.getInt("ID"),
                                rs.getInt("STORE"),
                                rs.getInt("GOOD"),
                                0, // invoice
                                0, // invoiceItem
                                0d, // quantity
                                rs.getDouble("QUANTITY_REST"),
                                0, //new Contractor()
                                0d, // priceSup
                                rs.getDouble("PRICE_SAL")));
                    return lots;
                }).toOptional().orElse(Collections.emptySet());
    }
}
