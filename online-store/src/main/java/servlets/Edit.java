package servlets;

import dao.interfaces.GunDao;
import model.Gun;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.lang.Integer.parseInt;
import static listeners.DbInitializer.GUN_DAO;

@WebServlet("/guns/edit")
public class Edit extends HttpServlet{

    public static final String GUN = "gun";
    private GunDao gunDao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        gunDao = (GunDao) config.getServletContext().getAttribute(GUN_DAO);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!Boolean.parseBoolean(request.getParameter("new"))) {
            final int id = parseInt(request.getParameter("id"));
            final Gun gun = gunDao.getGunById(id).orElseThrow(RuntimeException::new);

            request.setAttribute(GUN, gun);
        } else {
            request.setAttribute(GUN, new Gun(-1, null, 0d));
            request.setAttribute("new", 1); // TODO: attribute "new" doesn't work
        }

        // noinspection InjectedReferences
        request.getRequestDispatcher("/guns/edit/index.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Gun gun = new Gun(Integer.parseInt(request.getParameter("id")),
                request.getParameter("name"),
                Double.parseDouble(request.getParameter("caliber")));
        if (gun.getId() == -1) {
//            int i = gunDao.addGun(gun); // TODO: gunDao.addGun(gun);
        } else {
            boolean b = gunDao.updateGun(gun);
        }

        response.sendRedirect("/guns");
    }
}
