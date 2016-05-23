package filters;

import lombok.extern.log4j.Log4j;
import service.Helper;
import common.servlets.HttpFilter;
import dao.dto.converters.GoodConverter;
import dao.interfaces.GoodDao;
import model.Good;
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
 * Created by Oleg Grigorjev 
 */
 
@Log4j
@WebFilter({"/catalogs/goods/", "/catalogs/goods/index.jsp"})
public class GoodList implements HttpFilter {
    private GoodDao goodDao;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        goodDao = (GoodDao) DaoHandler.getDaoByClass(Good.class);
    }

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        Helper.TwoValues<Integer, Integer> offsetAndRowsOnPage = Helper.longListByPages(request, response, goodDao.getQuantity());
        Collection<Good> items = GoodConverter.convert(goodDao.getList(offsetAndRowsOnPage.first, offsetAndRowsOnPage.second));
        log.info("goods page configured to pages. the offset=" + offsetAndRowsOnPage.first + ", rows on page= " + offsetAndRowsOnPage.second);

        request.setAttribute("items", items);

        chain.doFilter(request, response);
    }
}
