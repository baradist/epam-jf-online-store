package filters;

import common.servlets.HttpFilter;
import dao.dto.CountryDto;
import dao.interfaces.CountryDao;
import model.Country;
import service.DaoHandler;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@WebFilter({"/catalogs/countries/", "/catalogs/countries/index.jsp"})
public class CountryList implements HttpFilter {
    private CountryDao countryDao;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        countryDao = (CountryDao) DaoHandler.getDaoByClass(Country.class);
    }

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        Collection<CountryDto> countries = countryDao.getList();
        request.setAttribute("countries", countries);

        chain.doFilter(request, response);
    }
}
