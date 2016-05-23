package service;

import dao.interfaces.Dao;
import dao.mysql.*;
import listeners.DbInitializer;
import model.*;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Oleg Grigorjev on 23.05.2016.
 */
 
public class DaoHandler {
    private static Map<Class, Dao> daoMap = new HashMap<>();


    public static void put(Class<? extends Object> clazz, Dao dao) {
        daoMap.put(clazz, dao);
    }

    public static Dao getDaoByClass(Class clazz) {
        return daoMap.get(clazz);
    }

    public static void initDaos(DataSource ds) {
        put(Lot.class, (MySqlLotDao) ds::getConnection);
        put(Lot.class, (MySqlLotDao) ds::getConnection);
        put(Good.class, (MySqlGoodDao) ds::getConnection);
        put(Producer.class, (MySqlProducerDao) ds::getConnection);
        put(Country.class, (MySqlCountryDao) ds::getConnection);
        put(Store.class, (MySqlStoreDao) ds::getConnection);
        put(Order.class, (MySqlOrderDao) ds::getConnection);
        put(OrderItem.class, (MySqlOrderItemDao) ds::getConnection);
        put(PriceItem.class, (MySqlPriceItemDao) ds::getConnection);
        put(Person.class, (MySqlPersonDao) ds::getConnection);
        put(Invoice.class, (MySqlInvoiceDao) ds::getConnection);
        put(InvoiceItem.class, (MySqlInvoiceItemDao) ds::getConnection);
        put(SetPrice.class, (MySqlSetPriceDao) ds::getConnection);
        put(SetPriceItem.class, (MySqlSetPriceItemDao) ds::getConnection);
        put(Contractor.class, (MySqlContractorDao) ds::getConnection);
    }
}
