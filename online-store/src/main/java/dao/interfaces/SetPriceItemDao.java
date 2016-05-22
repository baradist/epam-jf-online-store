package dao.interfaces;

import dao.dto.SetPriceItemDto;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public interface SetPriceItemDao extends Dao {

    int getQuantity(int invoiceId);

    Optional<SetPriceItemDto> getById(int id);

    Collection<SetPriceItemDto> getList();

    Collection<SetPriceItemDto> getList(int invoiceId);

    Collection<SetPriceItemDto> getList(int invoiceId, int start, int end);

    Collection<SetPriceItemDto> getListByIds(Collection<Integer> ids);

    Map<Integer, SetPriceItemDto> getMapByIds(Collection<Integer> ids);

    boolean add(SetPriceItemDto setPriceItemDto);

    boolean update(SetPriceItemDto setPriceItemDto);

    boolean delete(int id);
}
