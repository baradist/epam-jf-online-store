package dao.dto.converters;

import dao.dto.InvoiceDto;
import dao.interfaces.ContractorDao;
import dao.interfaces.PersonDao;
import dao.interfaces.StoreDao;
import model.Contractor;
import model.Invoice;
import model.Person;
import model.Store;
import service.DaoHandler;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Oleg Grigorjev on 13.05.2016.
 */
public interface InvoiceConverter {
    static Invoice convert(InvoiceDto dto) {
        return new Invoice(
                dto.getId(),
                dto.getNumber(),
                dto.getDate(),
                ContractorConverter.convert(((ContractorDao) DaoHandler.getDaoByClass(Contractor.class)).getById(dto.getSupplier()).get()),
                StoreConverter.convert(((StoreDao) DaoHandler.getDaoByClass(Store.class)).getById(dto.getStore()).get()),
                dto.getSum(),
                dto.getDeleted(),
                PersonConverter.convert(((PersonDao) DaoHandler.getDaoByClass(Person.class)).getById(dto.getManager()).get())
        );
    }

    static Collection<Invoice> convert(Collection<InvoiceDto> dtos) { // TODO
        Collection<Invoice> orders = new ArrayList<>();
        for (InvoiceDto dto : dtos) {
            orders.add(convert(dto));
        }
        return orders;
    }

    static InvoiceDto convert(Invoice invoice) {
        return new InvoiceDto(
                invoice.getId(),
                invoice.getNumber(),
                invoice.getDate(),
                invoice.getSupplier().getId(),
                invoice.getStore().getId(),
                invoice.getSum(),
                invoice.getDeleted(),
                invoice.getManager().getId()
        );
    }
}
