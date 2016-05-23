package listeners;

import service.DaoHandler;

import javax.annotation.Resource;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;

/**
 * Created by Oleg Grigorjev 
 */
 
@WebListener
public class DbInitializer implements ServletContextListener {

    @Resource(name="jdbc/ProdDB")
    private static DataSource ds;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        DaoHandler.initDaos(ds);
    }


}
