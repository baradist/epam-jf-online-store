package filters;

import common.servlets.HttpFilter;
import dao.interfaces.LotDao;
import listeners.DbInitializer;
import model.Lot;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@WebFilter({"/", "/index.jsp"})
public class GoodList implements HttpFilter {
    private LotDao lotDao;

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
//        lotDao = (GoodDao) request.getServletContext().getAttribute(LOT_DAO);
        lotDao = (LotDao) DbInitializer.getDaoByClass(Lot.class);
        Collection<Lot> lots = lotDao.getList();
        request.setAttribute("lots", lots);

        chain.doFilter(request, response);
    }
}
