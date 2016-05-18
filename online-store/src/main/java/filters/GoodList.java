package filters;

import common.functions.Helper;
import common.servlets.HttpFilter;
import dao.dto.GoodDto;
import dao.dto.converters.GoodConverter;
import dao.interfaces.GoodDao;
import listeners.DbInitializer;
import model.Good;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@WebFilter({"/catalogs/goods", "/catalogs/goods/index.jsp"})
public class GoodList implements HttpFilter {
    private GoodDao goodDao;

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (goodDao == null) {
            goodDao = (GoodDao) DbInitializer.getDaoByClass(Good.class);
        }
        Helper.TwoValues<Integer, Integer> offsetAndRowsOnPage = Helper.longListByPages(request, response, goodDao.getQuantity());

        Collection<GoodDto> itemDtos = goodDao.getList(offsetAndRowsOnPage.first, offsetAndRowsOnPage.second);
        Collection<Good> items = GoodConverter.convert(itemDtos);
        request.setAttribute("items", items);

        chain.doFilter(request, response);
    }
}
