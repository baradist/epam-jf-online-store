package dao.interfaces;

import dao.dto.StoreDto;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public interface StoreDao extends Dao {
    Optional<StoreDto> getById(int id);

    Collection<StoreDto> getList();

    Collection<StoreDto> getListByIds(Collection<Integer> ids);

    Map<Integer, StoreDto> getMapByIds(Collection<Integer> ids);
}
