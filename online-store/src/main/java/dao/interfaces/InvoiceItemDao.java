package dao.interfaces;

import dao.dto.InvoiceItemDto;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

/**
 * Created by Oleg Grigorjev 
 */
 
public interface InvoiceItemDao extends Dao {

    int getQuantity(int invoiceId);

    Optional<InvoiceItemDto> getById(int id);

    Collection<InvoiceItemDto> getList();

    Collection<InvoiceItemDto> getList(int invoiceId);

    Collection<InvoiceItemDto> getList(int invoiceId, int start, int end);

    Collection<InvoiceItemDto> getListByIds(Collection<Integer> ids);

    Map<Integer, InvoiceItemDto> getMapByIds(Collection<Integer> ids);

    boolean add(InvoiceItemDto invoiceItemDto);

    boolean update(InvoiceItemDto invoiceItemDto);

    boolean delete(int id);
}
