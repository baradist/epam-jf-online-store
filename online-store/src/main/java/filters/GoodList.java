package filters;

import common.servlets.HttpFilter;
import dao.dto.GoodDto;
import dao.dto.converters.GoodConverter;
import dao.interfaces.GoodDao;
import listeners.DbInitializer;
import model.Good;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@WebFilter({"/catalog/goods", "/catalog/goods/index.jsp"})
public class GoodList implements HttpFilter {
    private GoodDao goodDao;

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (goodDao == null) {
            goodDao = (GoodDao) DbInitializer.getDaoByClass(Good.class);
        }
        Collection<GoodDto> goods = goodDao.getList();
        request.setAttribute("goods", GoodConverter.convert(goods));

        chain.doFilter(request, response);
    }
}
