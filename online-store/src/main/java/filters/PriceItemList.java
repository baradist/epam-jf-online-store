package filters;

import service.Helper;
import common.servlets.HttpFilter;
import dao.dto.PriceItemDto;
import dao.dto.converters.PriceItemConverter;
import dao.interfaces.PriceItemDao;
import model.PriceItem;
import service.DaoHandler;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.Collection;

/**
 *  getting the price list and sending it to the main-page
 */

@WebFilter({"/", "/index.jsp"})
public class PriceItemList implements HttpFilter {
    private PriceItemDao priceItemDao;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        priceItemDao = (PriceItemDao) DaoHandler.getDaoByClass(PriceItem.class);
    }

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        Helper.TwoValues<Integer, Integer> offsetAndRowsOnPage = Helper.longListByPages(request, response, priceItemDao.getQuantity());
        Principal userPrincipal = request.getUserPrincipal();
        Collection<PriceItemDto> itemDtos;
        if (userPrincipal == null) {
            itemDtos = priceItemDao.getList(offsetAndRowsOnPage.first, offsetAndRowsOnPage.second);
        } else {
            itemDtos = priceItemDao.getListForPersonByEmail(userPrincipal.getName(), offsetAndRowsOnPage.first, offsetAndRowsOnPage.second);
        }
        Collection<PriceItem> items = PriceItemConverter.convert(itemDtos);
        request.setAttribute("list", items);

        chain.doFilter(request, response);
    }
}