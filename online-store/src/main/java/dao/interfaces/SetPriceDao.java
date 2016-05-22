package dao.interfaces;

import dao.dto.SetPriceDto;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public interface SetPriceDao extends Dao {

    int getQuantity();

    Optional<SetPriceDto> getById(int id);

    Collection<SetPriceDto> getList();

    Collection<SetPriceDto> getList(int start, int end);

    Collection<SetPriceDto> getListByIds(Collection<Integer> ids);

    Map<Integer, SetPriceDto> getMapByIds(Collection<Integer> ids);

    boolean add(SetPriceDto setPriceDto);

    boolean update(SetPriceDto setPriceDto);

    boolean delete(int id);
}
