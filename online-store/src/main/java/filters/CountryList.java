package filters;

import common.servlets.HttpFilter;
import dao.interfaces.CountryDao;
import listeners.DbInitializer;
import model.Country;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@WebFilter({"/catalog/countries", "/catalog/countries/index.jsp"})
public class CountryList implements HttpFilter {
    private CountryDao countryDao;

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (countryDao == null) {
            countryDao = (CountryDao) DbInitializer.getDaoByClass(Country.class);
        }
        Collection<Country> countries = countryDao.getList();
        request.setAttribute("countries", countries);

        chain.doFilter(request, response);
    }
}
