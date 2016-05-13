package filters;

import common.servlets.HttpFilter;
import dao.dto.converters.LotConverter;
import dao.interfaces.LotDao;
import listeners.DbInitializer;
import model.Lot;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter({"/", "/index.jsp"})
public class LotList implements HttpFilter {
    private LotDao lotDao;

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (lotDao == null) {
            lotDao = (LotDao) DbInitializer.getDaoByClass(Lot.class);
        }
        request.setAttribute("lots", LotConverter.convert(lotDao.getList()));

        chain.doFilter(request, response);
    }
}
