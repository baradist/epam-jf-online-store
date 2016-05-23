package filters;

import service.Helper;
import common.servlets.HttpFilter;
import dao.dto.converters.InvoiceConverter;
import dao.dto.converters.InvoiceItemConverter;
import dao.interfaces.InvoiceDao;
import dao.interfaces.InvoiceItemDao;
import model.Invoice;
import model.InvoiceItem;
import service.DaoHandler;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

/**
 * Created by Oleg Grigorjev on 22.05.2016.
 */

@WebFilter({"/documents/invoices/edit/", "/documents/invoices/edit/index.jsp"})
public class InvoiceFilter implements HttpFilter {
    InvoiceDao invoiceDao;
    InvoiceItemDao invoiceItemDao;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        invoiceDao = (InvoiceDao) DaoHandler.getDaoByClass(Invoice.class);
        invoiceItemDao = (InvoiceItemDao) DaoHandler.getDaoByClass(InvoiceItem.class);
    }

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        Invoice invoice = InvoiceConverter.convert(invoiceDao.getById(Integer.parseInt(request.getParameter("id"))).get());
        request.setAttribute("invoice", invoice);

        Helper.TwoValues<Integer, Integer> offsetAndRowsOnPage = Helper.longListByPages(request, response, invoiceItemDao.getQuantity(invoice.getId()));
        Collection<InvoiceItem> items = InvoiceItemConverter.convert(invoiceItemDao.getList(invoice.getId(), offsetAndRowsOnPage.first, offsetAndRowsOnPage.second));
        request.setAttribute("items", items);

        request.setAttribute("isNew", false);

        chain.doFilter(request, response);
    }
}
