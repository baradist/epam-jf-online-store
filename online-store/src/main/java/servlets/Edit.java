package servlets;

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
import java.util.Optional;

import static java.lang.Integer.parseInt;

@WebServlet({"/catalog/goods/edit"})
public class Edit extends HttpServlet{

    private GoodDao goodDao;
    private static Map<String, Good> editingGoods = new HashMap<>();

    @Override
    public void init(ServletConfig config) throws ServletException {
        goodDao = (GoodDao) DbInitializer.getDaoByClass(Good.class);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (Boolean.parseBoolean(request.getParameter("isNew"))) {
            request.setAttribute("good", new Good(-1, "", null, ""));
            request.setAttribute("isNew", true);
        } else if (Boolean.parseBoolean(request.getParameter("delete"))) {
            // TODO: delete
            request.getRequestDispatcher("/catalog/goods/index.jsp").forward(request, response);
            return;
        } else {
            final int id = parseInt(request.getParameter("id"));
            final Optional<Good> goodOptional = goodDao.getById(id); //.orElseThrow(RuntimeException::new);

            Good good = goodOptional.get();

            editingGoods.put(request.getSession().getId(), good);

            request.setAttribute("good", good);
            request.setAttribute("isNew", false);
        }

        // noinspection InjectedReferences
        request.getRequestDispatcher("/catalog/goods/edit/index.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (Boolean.parseBoolean(request.getParameter("isNew"))) {
            // TODO: make new Good
//            int i = goodDao.add(good);

        } else {
            String sessionId = request.getSession().getId();
            Good good = editingGoods.get(sessionId);
            editingGoods.remove(sessionId);

            good.setName(request.getParameter("name"));
            good.setDescription(request.getParameter("description"));
//            boolean b = goodDao.update(good); TODO
        }

        response.sendRedirect("/catalog/goods");
    }
}
