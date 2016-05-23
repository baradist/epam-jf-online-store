package dao.dto.converters;

import dao.dto.InvoiceItemDto;
import dao.interfaces.*;
import model.*;
import service.DaoHandler;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Oleg Grigorjev on 13.05.2016.
 */
 
public interface InvoiceItemConverter {
    static InvoiceItem convert(InvoiceItemDto dto) {
        return new InvoiceItem(
                dto.getId(),
                InvoiceConverter.convert(((InvoiceDao) DaoHandler.getDaoByClass(Invoice.class)).getById(dto.getInvoice()).get()),
                GoodConverter.convert(((GoodDao) DaoHandler.getDaoByClass(Good.class)).getById(dto.getGood()).get()),
                dto.getQuantity(),
                dto.getPrice()
        );
    }

    static Collection<InvoiceItem> convert(Collection<InvoiceItemDto> dtos) { // TODO
        Collection<InvoiceItem> orders = new ArrayList<>();
        for (InvoiceItemDto dto : dtos) {
            orders.add(convert(dto));
        }
        return orders;
    }

    static InvoiceItemDto convert(InvoiceItem invoice) {
        return new InvoiceItemDto(
                invoice.getId(),
                invoice.getInvoice().getId(),
                invoice.getGood().getId(),
                invoice.getQuantity(),
                invoice.getPrice()
        );
    }
}
