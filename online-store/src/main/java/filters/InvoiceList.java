package filters;

import lombok.extern.log4j.Log4j;
import service.Helper;
import common.servlets.HttpFilter;
import dao.dto.InvoiceDto;
import dao.dto.converters.InvoiceConverter;
import dao.interfaces.InvoiceDao;
import model.Invoice;
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
 * Created by Oleg Grigorjev 
 */
 
@Log4j
@WebFilter({"/documents/invoices/", "/documents/invoices/index.jsp"})
public class InvoiceList implements HttpFilter {
    private InvoiceDao invoice;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        invoice = (InvoiceDao) DaoHandler.getDaoByClass(Invoice.class);
    }

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        Helper.TwoValues<Integer, Integer> offsetAndRowsOnPage = Helper.longListByPages(request, response, invoice.getQuantity());

        Collection<InvoiceDto> itemDtos = invoice.getList(offsetAndRowsOnPage.first, offsetAndRowsOnPage.second);
        Collection<Invoice> items = InvoiceConverter.convert(itemDtos);
        request.setAttribute("items", items);

        log.info("invoice list add to the request");
        chain.doFilter(request, response);
    }
}
