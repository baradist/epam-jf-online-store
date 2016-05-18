package filters;

import common.functions.Helper;
import common.servlets.HttpFilter;
import dao.dto.PriceItemDto;
import dao.dto.converters.PriceItemConverter;
import dao.interfaces.PriceItemDao;
import listeners.DbInitializer;
import model.PriceItem;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.Collection;

@WebFilter({"/", "/index.jsp"})
public class PriceItemList implements HttpFilter {
    private PriceItemDao priceItemDao;

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (priceItemDao == null) {
            priceItemDao = (PriceItemDao) DbInitializer.getDaoByClass(PriceItem.class);
        }

        Helper.TwoValues<Integer, Integer> offsetAndRowsOnPage = Helper.longListByPages(request, response, priceItemDao.getQuantity());
        Principal userPrincipal = request.getUserPrincipal();
        Collection<PriceItemDto> itemDtos;
        if (userPrincipal == null) {
            itemDtos = priceItemDao.getList(offsetAndRowsOnPage.first, offsetAndRowsOnPage.second);
        } else {
            itemDtos = priceItemDao.getListForPersonsEmail(userPrincipal.getName(), offsetAndRowsOnPage.first, offsetAndRowsOnPage.second);
        }
        Collection<PriceItem> items = PriceItemConverter.convert(itemDtos);
        request.setAttribute("list", items);

        // TODO: put basket info

        chain.doFilter(request, response);
    }
}