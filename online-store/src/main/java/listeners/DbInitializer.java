package listeners;

import dao.interfaces.Dao;
import dao.mysql.*;
import model.*;

import javax.annotation.Resource;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@WebListener
public class DbInitializer implements ServletContextListener {

    @Resource(name="jdbc/ProdDB")
    private static DataSource ds;

    private static Map<Class, Dao> daoMap = new HashMap<>();

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        daoMap.put(Lot.class, (MySqlLotDao) ds::getConnection);
        daoMap.put(Good.class, (MySqlGoodDao) ds::getConnection);
        daoMap.put(Producer.class, (MySqlProducerDao) ds::getConnection);
        daoMap.put(Country.class, (MySqlCountryDao) ds::getConnection);
        daoMap.put(Store.class, (MySqlStoreDao) ds::getConnection);
        daoMap.put(Order.class, (MySqlOrderDao) ds::getConnection);
        daoMap.put(OrderItem.class, (MySqlOrderItemDao) ds::getConnection);
        daoMap.put(PriceItem.class, (MySqlPriceItemDao) ds::getConnection);
        daoMap.put(Person.class, (MySqlPersonDao) ds::getConnection);
        daoMap.put(Invoice.class, (MySqlInvoiceDao) ds::getConnection);
        daoMap.put(InvoiceItem.class, (MySqlInvoiceItemDao) ds::getConnection);
        daoMap.put(Contractor.class, (MySqlContractorDao) ds::getConnection);
    }

    public static Dao getDaoByClass(Class clazz) {
        return daoMap.get(clazz);
    }
}
