package servlets;

import dao.dto.GoodDto;
import dao.dto.converters.GoodConverter;
import dao.dto.converters.ProducerConverter;
import dao.interfaces.GoodDao;
import dao.interfaces.ProducerDao;
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
import java.util.HashMap;
import java.util.Map;

import static java.lang.Integer.parseInt;

@WebServlet({/*"/catalogs/goods/edit/", */"/catalogs/goods/edit"/*, "/catalogs/goods/edit/index.jsp"*/})
public class GoodEdit extends HttpServlet{

    private GoodDao goodDao;
    private ProducerDao producerDao;
//    private static Map<String, Good> editingGoods = new HashMap<>();

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
//            editingGoods.put(request.getSession().getId(), good);

            request.setAttribute("good", good);
            request.setAttribute("isNew", false);
        }

        // noinspection InjectedReferences
        request.getRequestDispatcher("/catalogs/goods/edit/index.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (Boolean.parseBoolean(request.getParameter("delete"))) {
            // delete
            goodDao.delete(parseInt(request.getParameter("id")));
        } else if (Boolean.parseBoolean(request.getParameter("isNew"))) {
            // new
            GoodDto goodDto = new GoodDto(
                    request.getParameter("name"),
                    Integer.parseInt(request.getParameter("producerId")), // TODO: choose producer
                    request.getParameter("description")
            );
            goodDao.add(goodDto);

        } else {
            // edit
            GoodDto goodDto = new GoodDto(
                    Integer.parseInt(request.getParameter("id")),
                    request.getParameter("name"),
                    Integer.parseInt(request.getParameter("producerId")), // TODO: choose producer
                    request.getParameter("description")
            );
            goodDao.update(goodDto);

//            goodDao.update(GoodConverter.convert(good));
        }

        response.sendRedirect("/catalogs/goods");
    }
}
