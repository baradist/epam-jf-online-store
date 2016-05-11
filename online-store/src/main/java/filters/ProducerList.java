package filters;

import common.servlets.HttpFilter;
import dao.interfaces.ProducerDao;
import listeners.DbInitializer;
import model.Producer;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@WebFilter({"/catalog/producers", "/catalog/producers/index.jsp"})
public class ProducerList implements HttpFilter {
    private ProducerDao producerDao;

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (producerDao == null) {
            producerDao = (ProducerDao) DbInitializer.getDaoByClass(Producer.class);
        }
        Collection<Producer> producers = producerDao.getList();
        request.setAttribute("producers", producers);

        chain.doFilter(request, response);
    }
}
