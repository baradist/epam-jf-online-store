package filters;

import common.servlets.HttpFilter;
import dao.dto.StoreDto;
import dao.interfaces.StoreDao;
import lombok.extern.log4j.Log4j;
import model.Store;
import service.DaoHandler;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

/**
 * Created by Oleg Grigorjev on 21.05.2016.
 */
@Log4j
@WebFilter({"/catalogs/stores/", "/catalogs/stores/index.jsp"})
public class StoreList  implements HttpFilter {
    private StoreDao storeDao;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        storeDao = (StoreDao) DaoHandler.getDaoByClass(Store.class);
    }

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        Collection<StoreDto> stores = storeDao.getList();
        request.setAttribute("stores", stores);
        log.info("stores list add to the request");

        chain.doFilter(request, response);
    }
}
