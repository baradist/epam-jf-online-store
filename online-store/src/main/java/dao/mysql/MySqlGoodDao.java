package dao.mysql;

import dao.interfaces.GoodDao;
import model.*;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;

@FunctionalInterface
public interface MySqlGoodDao extends GoodDao {

    @Override
    default Optional<Good> getGoodById(int id) { // TODO
        return executeQuery(
                "SELECT name, caliber FROM Gun WHERE id = " + id,
                rs -> rs.next()
                        ? new Good(id, rs.getString("name"), new Producer(0, "", null), rs.getString("description"))
                        : null
        ).toOptional();
    }

    @Override
    default Collection<Lot> getLot() { // TODO
        return executeQuery(
                "SELECT \n" +
                        "L.ID AS ID,\n" +
                        "ST.ID AS ST_ID,\n" +
                        "ST.NAME AS ST_NAME,\n" +
                        "ST.ADDRESS AS ST_ADDRESS,\n" +
                        "G.ID AS G_ID,\n" +
                        "G.NAME AS G_NAME,\n" +
                        "P.ID AS G_P_ID,\n" +
                        "P.NAME AS G_P_NAME,\n" +
                        "C.ID AS G_P_C_ID,\n" +
                        "C.NAME AS G_P_C_NAME,\n" +
                        "L.QUANTITY_REST AS QUANTITY_REST,\n" +
                        "L.PRICE_SAL AS PRICE_SAL\n" +
                        "FROM LOT L\n" +
                        "INNER JOIN STORE ST ON L.STORE = ST.ID\n" +
                        "INNER JOIN GOOD G ON L.GOOD = G.ID\n" +
                        "INNER JOIN CONTRACTOR S ON L.SUPPLIER = S.ID\n" +
                        "INNER JOIN PRODUCER P ON G.PRODUCER = P.ID\n" +
                        "INNER JOIN COUNTRY C ON P.COUNTRY = C.ID\n" +
                        "WHERE L.QUANTITY_REST > 0",
                rs -> {
                    Collection<Lot> lots = new HashSet<>();
                    while (rs.next())
                        lots.add(new Lot(
                                rs.getInt("ID"),
                                new Store(rs.getInt("ST_ID"), rs.getString("ST_NAME"), rs.getString("ST_ADDRESS")),
                                new Good(rs.getInt("G_ID"), rs.getString("G_NAME"),
                                        new Producer(rs.getInt("G_P_ID"), rs.getString("G_P_NAME"),
                                                new Country(rs.getInt("G_P_C_ID"), rs.getString("G_P_C_NAME"))), null),
                                null, // invoice
                                null, // invoiceItem
                                0d, // quantity
                                rs.getDouble("QUANTITY_REST"),
                                null, //new Contractor()
                                0d, // price_sup
                                rs.getDouble("PRICE_SAL")
                        ));

                    return lots;
                }).toOptional().orElse(Collections.emptySet());
    }
}
