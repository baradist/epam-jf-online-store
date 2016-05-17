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

        rowsByPages(request, response, priceItemDao);

        // TODO: put basket info

        chain.doFilter(request, response);
    }

    public static void rowsByPages(HttpServletRequest request, HttpServletResponse response, PriceItemDao dao) {
        int quantity = dao.getQuantity();
        request.setAttribute("quantity", quantity);

        String rowsOnPageString = request.getParameter("rowsOnPage");
        int rowsOnPage = 10;

        if (rowsOnPageString != null) {
            Helper.putCookie(response, "/", "rowsOnPage", rowsOnPageString);
            rowsOnPage = Integer.parseInt(rowsOnPageString);
        } else {
            rowsOnPageString = Helper.getCookieValue(request, "/", "rowsOnPage");
            if (rowsOnPageString != null) {
                rowsOnPage = Integer.parseInt(rowsOnPageString);
            }
        }
        request.setAttribute("rowsOnPage", rowsOnPage);

        String offsetString = request.getParameter("offset");
        String pageNumberString = request.getParameter("pageNumber");
        int offset;
        int pageNumber;
        if (offsetString != null && pageNumberString != null) {
            offset = Integer.parseInt(offsetString);
            pageNumber = Integer.parseInt(pageNumberString);
        } else {
            offset = 0;
            pageNumber = 1;
        }
//        request.setAttribute("offset", offset);
        request.setAttribute("pageNumber", pageNumber);
        // TODO ??? go on
        Principal userPrincipal = request.getUserPrincipal();
        Collection<PriceItemDto> itemDtos;
        if (userPrincipal == null) {
            itemDtos = dao.getList(offset, rowsOnPage);
        } else {
            itemDtos = dao.getListForPersonsEmail(userPrincipal.getName(), offset, rowsOnPage);
        }
        Collection<PriceItem> items = PriceItemConverter.convert(itemDtos);
        request.setAttribute("list", items);
    }
}
