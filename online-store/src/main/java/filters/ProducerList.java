package filters;

import common.servlets.HttpFilter;
import dao.dto.ProducerDto;
import dao.dto.converters.ProducerConverter;
import dao.interfaces.ProducerDao;
import model.Producer;
import service.DaoHandler;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@WebFilter({"/catalogs/producers", "/catalogs/producers/index.jsp"})
public class ProducerList implements HttpFilter {
    private ProducerDao producerDao;

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (producerDao == null) {
            producerDao = (ProducerDao) DaoHandler.getDaoByClass(Producer.class);
        }
        Collection<ProducerDto> producersDto = producerDao.getList();

        request.setAttribute("producers", ProducerConverter.convert(producersDto));

        chain.doFilter(request, response);
    }
}
