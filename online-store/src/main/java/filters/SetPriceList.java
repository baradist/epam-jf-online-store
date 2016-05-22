package filters;

import common.functions.Helper;
import common.servlets.HttpFilter;
import dao.dto.converters.SetPriceConverter;
import dao.interfaces.SetPriceDao;
import listeners.DbInitializer;
import model.SetPrice;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@WebFilter({"/documents/sets_price/", "/documents/sets_price/index.jsp"})
public class SetPriceList implements HttpFilter {
    private SetPriceDao setPriceDao;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        setPriceDao = (SetPriceDao) DbInitializer.getDaoByClass(SetPrice.class);
    }

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        Helper.TwoValues<Integer, Integer> offsetAndRowsOnPage = Helper.longListByPages(request, response, setPriceDao.getQuantity());

        Collection<SetPrice> items = SetPriceConverter.convert(setPriceDao.getList(offsetAndRowsOnPage.first, offsetAndRowsOnPage.second));
        request.setAttribute("items", items);

        chain.doFilter(request, response);
    }
}
