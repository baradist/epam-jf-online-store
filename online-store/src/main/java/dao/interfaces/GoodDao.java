package dao.interfaces;

import dao.dto.GoodDto;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

/**
 * Created by Oleg Grigorjev 
 */
 
public interface GoodDao extends Dao {

    int getQuantity();

    Optional<GoodDto> getById(int id);

    Collection<GoodDto> getList();

    Collection<GoodDto> getList(int start, int end);

    Collection<GoodDto> getListByIds(Collection<Integer> ids);

    Map<Integer, GoodDto> getMapByIds(Collection<Integer> ids);

    boolean add(GoodDto goodDto);

    boolean update(GoodDto goodDto);

    boolean delete(int id);
}
