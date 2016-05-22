package filters;

import common.functions.Helper;
import common.servlets.HttpFilter;
import dao.dto.converters.PersonConverter;
import dao.interfaces.PersonDao;
import listeners.DbInitializer;
import model.Person;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@WebFilter({"/catalogs/persons/", "/catalogs/persons/index.jsp"})
public class PersonList implements HttpFilter {
    private PersonDao personDao;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        personDao = (PersonDao) DbInitializer.getDaoByClass(Person.class);
    }

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        Helper.TwoValues<Integer, Integer> offsetAndRowsOnPage = Helper.longListByPages(request, response, personDao.getQuantity());

        Collection<Person> items = PersonConverter.convert(personDao.getList(offsetAndRowsOnPage.first, offsetAndRowsOnPage.second));

        request.setAttribute("items", items);

        chain.doFilter(request, response);
    }
}