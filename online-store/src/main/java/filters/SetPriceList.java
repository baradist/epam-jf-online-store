package filters;

import lombok.extern.log4j.Log4j;
import service.Helper;
import common.servlets.HttpFilter;
import dao.dto.converters.SetPriceConverter;
import dao.interfaces.SetPriceDao;
import model.SetPrice;
import service.DaoHandler;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@Log4j
@WebFilter({"/documents/sets_price/", "/documents/sets_price/index.jsp"})
public class SetPriceList implements HttpFilter {
    private SetPriceDao setPriceDao;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        setPriceDao = (SetPriceDao) DaoHandler.getDaoByClass(SetPrice.class);
    }

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        Helper.TwoValues<Integer, Integer> offsetAndRowsOnPage = Helper.longListByPages(request, response, setPriceDao.getQuantity());

        Collection<SetPrice> items = SetPriceConverter.convert(setPriceDao.getList(offsetAndRowsOnPage.first, offsetAndRowsOnPage.second));
        request.setAttribute("items", items);
        log.info("sets price list add to the request");

        chain.doFilter(request, response);
    }
}
