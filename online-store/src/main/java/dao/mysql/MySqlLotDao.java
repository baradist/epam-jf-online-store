package dao.mysql;

import dao.interfaces.GoodDao;
import dao.interfaces.LotDao;
import dao.interfaces.StoreDao;
import listeners.DbInitializer;
import model.Good;
import model.Lot;
import model.Producer;
import model.Store;

import java.util.*;

@FunctionalInterface
public interface MySqlLotDao extends LotDao {

    @Override
    default Optional<Good> getById(int id) { // TODO
        return executeQuery(
                "SELECT name, caliber FROM Gun WHERE id = " + id,
                rs -> rs.next()
                        ? new Good(id, rs.getString("name"), new Producer(0, "", null), rs.getString("description"))
                        : null
        ).toOptional();
    }

    @Override
    default Collection<Lot> getList() {
        return executeQuery(
                "SELECT \n" +
                        "L.ID AS ID,\n" +
                        "L.STORE AS STORE," +
                        "ST.NAME AS ST_NAME,\n" +
                        "ST.ADDRESS AS ST_ADDRESS,\n" +
                        "L.GOOD AS GOOD,\n" +
                        "L.QUANTITY_REST AS QUANTITY_REST,\n" +
                        "L.PRICE_SAL AS PRICE_SAL\n" +
                        "FROM LOT L\n" +
                        "INNER JOIN STORE ST ON L.STORE = ST.ID\n" +
                        "INNER JOIN CONTRACTOR S ON L.SUPPLIER = S.ID\n" +
                        "WHERE L.QUANTITY_REST > 0",
                rs -> {
                    Collection<Lot> lots = new ArrayList<>();
                    Set<Integer> goodIds = new HashSet<>();
                    Set<Integer> storeIds = new HashSet<>();
                    while (rs.next()) {
                        goodIds.add(rs.getInt("GOOD"));
                        storeIds.add(rs.getInt("STORE"));
                    }
                    GoodDao goodDao = (GoodDao) DbInitializer.getDaoByClass(Good.class);
                    Map<Integer, Good> goodMap = goodDao.getMapByIds(goodIds);
                    StoreDao storeDao = (StoreDao) DbInitializer.getDaoByClass(Store.class);
                    Map<Integer, Store> storeMap = storeDao.getMapByIds(storeIds);

                    rs.beforeFirst();
                    while (rs.next())
                        lots.add(new Lot(
                                rs.getInt("ID"),
                                storeMap.get(rs.getInt("STORE")),
                                goodMap.get(rs.getInt("GOOD")),
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
