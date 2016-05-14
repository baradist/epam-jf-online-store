package servlets;

import dao.dto.GoodDto;
import dao.dto.converters.GoodConverter;
import dao.interfaces.GoodDao;
import listeners.DbInitializer;
import model.Good;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Integer.parseInt;

@WebServlet({"/catalogs/goods/edit"})
public class GoodEdit extends HttpServlet{

    private GoodDao goodDao;
    private static Map<String, Good> editingGoods = new HashMap<>();

    @Override
    public void init(ServletConfig config) throws ServletException {
        goodDao = (GoodDao) DbInitializer.getDaoByClass(Good.class);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (Boolean.parseBoolean(request.getParameter("isNew"))) {
            // new
            request.setAttribute("isNew", true);
        } else {
            // edit
            Good good = GoodConverter.convert(goodDao.getById(parseInt(request.getParameter("id"))).get());
            editingGoods.put(request.getSession().getId(), good);

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
                    Integer.parseInt(request.getParameter("producer")), // TODO: choose producer
                    request.getParameter("description")
            );
            goodDao.add(goodDto);

        } else {
            // edit
            String sessionId = request.getSession().getId();
            Good good = editingGoods.get(sessionId);
            editingGoods.remove(sessionId);

            good.setName(request.getParameter("name"));
            good.setDescription(request.getParameter("description"));

            goodDao.update(GoodConverter.convert(good));
        }

        response.sendRedirect("/catalogs/goods");
    }
}