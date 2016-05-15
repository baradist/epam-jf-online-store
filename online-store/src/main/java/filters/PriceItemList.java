package filters;

import common.servlets.HttpFilter;
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

@WebFilter({"/", "/index.jsp"})
public class PriceItemList implements HttpFilter {
    private PriceItemDao priceItemDao;

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (priceItemDao == null) {
            priceItemDao = (PriceItemDao) DbInitializer.getDaoByClass(PriceItem.class);
        }
        request.setAttribute("list", PriceItemConverter.convert(priceItemDao.getList()));

        chain.doFilter(request, response);
    }
}
