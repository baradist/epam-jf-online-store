package dao.interfaces;

import dao.dto.InvoiceDto;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

/**
 * Created by Oleg Grigorjev 
 */
 
public interface InvoiceDao extends Dao {

    int getQuantity();

    Optional<InvoiceDto> getById(int id);

    Collection<InvoiceDto> getList();

    Collection<InvoiceDto> getList(int start, int end);

    Collection<InvoiceDto> getListByIds(Collection<Integer> ids);

    Map<Integer, InvoiceDto> getMapByIds(Collection<Integer> ids);

    boolean add(InvoiceDto invoiceDto);

    boolean update(InvoiceDto invoiceDto);

    boolean delete(int id);
}
