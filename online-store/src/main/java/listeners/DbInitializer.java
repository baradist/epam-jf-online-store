package listeners;

import dao.mysql.MySqlGoodDao;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;

@WebListener
public class DbInitializer implements ServletContextListener {

    @Resource(name="jdbc/ProdDB")
    private static DataSource ds;

    public static final String LOT_DAO = "lotDao";
    // ...

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        final ServletContext servletContext = sce.getServletContext();

        servletContext.setAttribute(LOT_DAO, (MySqlGoodDao) ds::getConnection);
        // ...
    }
}
