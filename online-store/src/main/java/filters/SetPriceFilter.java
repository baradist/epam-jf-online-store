package filters;

import service.Helper;
import common.servlets.HttpFilter;
import dao.dto.converters.SetPriceConverter;
import dao.dto.converters.SetPriceItemConverter;
import dao.interfaces.SetPriceDao;
import dao.interfaces.SetPriceItemDao;
import model.SetPrice;
import model.SetPriceItem;
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
 * Created by Oleg Grigorjev on 22.05.2016.
 */

@WebFilter({"/documents/sets_price/edit/", "/documents/sets_price/edit/index.jsp"})
public class SetPriceFilter implements HttpFilter {
    SetPriceDao setPriceDao;
    SetPriceItemDao setPriceItemDao;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        setPriceDao = (SetPriceDao) DaoHandler.getDaoByClass(SetPrice.class);
        setPriceItemDao = (SetPriceItemDao) DaoHandler.getDaoByClass(SetPriceItem.class);
    }

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        SetPrice setPrice = SetPriceConverter.convert(setPriceDao.getById(Integer.parseInt(request.getParameter("id"))).get());
        request.setAttribute("setPrice", setPrice);

        Helper.TwoValues<Integer, Integer> offsetAndRowsOnPage = Helper.longListByPages(request, response, setPriceItemDao.getQuantity(setPrice.getId()));
        Collection<SetPriceItem> items = SetPriceItemConverter.convert(setPriceItemDao.getList(setPrice.getId(), offsetAndRowsOnPage.first, offsetAndRowsOnPage.second));
        request.setAttribute("items", items);

        request.setAttribute("isNew", false);

        chain.doFilter(request, response);
    }
}
