package filters;

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

        int quantity = priceItemDao.getQuantity();
        request.setAttribute("quantity", quantity);

        String rowsOnPageString = request.getParameter("rowsOnPage");
        int rowsOnPage;
        if (rowsOnPageString != null) {
            rowsOnPage = Integer.parseInt(rowsOnPageString);
        } else {
            rowsOnPage = 10;
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
        request.setAttribute("offset", offset); // TODO: ??
        request.setAttribute("pageNumber", pageNumber);

        // TODO: put values into cookies
        // TODO: go on
//        List<Integer> startRowList = new ArrayList<>();
//        for (int i = 1; i < quantity; i+= rowsOnPage) {
//            startRowList.add(i);
//        }
//        request.setAttribute("startRowList", startRowList);


        Principal userPrincipal = request.getUserPrincipal();
        Collection<PriceItemDto> priceItemDtos;
        if (userPrincipal == null) {
            priceItemDtos = priceItemDao.getList(offset, rowsOnPage);
        } else {
            priceItemDtos = priceItemDao.getListForPersonsEmail(userPrincipal.getName(), offset, rowsOnPage);
        }
        Collection<PriceItem> priceItems = PriceItemConverter.convert(priceItemDtos);
        request.setAttribute("list", priceItems);

        // TODO: put basket info

        chain.doFilter(request, response);
    }
}
