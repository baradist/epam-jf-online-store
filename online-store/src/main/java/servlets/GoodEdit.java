package servlets;

import dao.dto.GoodDto;
import dao.dto.converters.GoodConverter;
import dao.dto.converters.ProducerConverter;
import dao.interfaces.GoodDao;
import dao.interfaces.ProducerDao;
import lombok.extern.log4j.Log4j;
import model.Good;
import model.Producer;
import service.DaoHandler;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

import static java.lang.Integer.parseInt;

/**
 * Created by Oleg Grigorjev 
 */
 
@Log4j
@WebServlet({"/catalogs/goods/edit"})
public class GoodEdit extends HttpServlet{

    private GoodDao goodDao;
    private ProducerDao producerDao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        goodDao = (GoodDao) DaoHandler.getDaoByClass(Good.class);
        producerDao = (ProducerDao) DaoHandler.getDaoByClass(Producer.class);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Collection<Producer> producers = ProducerConverter.convert(producerDao.getList());
        request.setAttribute("producers", producers);
        if (Boolean.parseBoolean(request.getParameter("isNew"))) {
            // new
            request.setAttribute("isNew", true);
        } else {
            // edit
            Good good = GoodConverter.convert(goodDao.getById(parseInt(request.getParameter("id"))).get());

            request.setAttribute("good", good);
            request.setAttribute("isNew", false);
        }

        request.getRequestDispatcher("/catalogs/goods/edit/index.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (Boolean.parseBoolean(request.getParameter("delete"))) {
            int id = parseInt(request.getParameter("id"));
            goodDao.delete(id);
            log.info("the good id=" + id + " deleted");
        } else if (Boolean.parseBoolean(request.getParameter("isNew"))) {
            // new
            GoodDto goodDto = new GoodDto(
                    request.getParameter("name"),
                    Integer.parseInt(request.getParameter("producerId")), // TODO: choose producer
                    request.getParameter("description")
            );
            goodDao.add(goodDto);
            log.info("a new good added");
        } else {
            // edit
            GoodDto goodDto = new GoodDto(
                    Integer.parseInt(request.getParameter("id")),
                    request.getParameter("name"),
                    Integer.parseInt(request.getParameter("producerId")), // TODO: choose producer
                    request.getParameter("description")
            );
            goodDao.update(goodDto);
            log.info("the good id=" + goodDto.getId() + " edited");
        }

        response.sendRedirect("/catalogs/goods");
    }
}
