package filters;

import common.servlets.HttpFilter;
import dao.interfaces.GoodDao;
import model.Lot;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

import static listeners.DbInitializer.LOT_DAO;

@WebFilter({"/", "/index.jsp"})
public class GoodList implements HttpFilter {
    private GoodDao goodDao;

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        goodDao = (GoodDao) request.getServletContext().getAttribute(LOT_DAO);
        Collection<Lot> lots = goodDao.getLot();
        request.setAttribute("lots", lots);

        chain.doFilter(request, response);
    }
}
