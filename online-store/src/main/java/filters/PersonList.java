package filters;

import lombok.extern.log4j.Log4j;
import service.Helper;
import common.servlets.HttpFilter;
import dao.dto.converters.PersonConverter;
import dao.interfaces.PersonDao;
import model.Person;
import service.DaoHandler;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@Log4j
@WebFilter({"/catalogs/persons/", "/catalogs/persons/index.jsp"})
public class PersonList implements HttpFilter {
    private PersonDao personDao;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        personDao = (PersonDao) DaoHandler.getDaoByClass(Person.class);
    }

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        Helper.TwoValues<Integer, Integer> offsetAndRowsOnPage = Helper.longListByPages(request, response, personDao.getQuantity());
        Collection<Person> items = PersonConverter.convert(personDao.getList(offsetAndRowsOnPage.first, offsetAndRowsOnPage.second));
        request.setAttribute("items", items);
        log.info("persons list add to the request");

        chain.doFilter(request, response);
    }
}
