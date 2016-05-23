package dao.interfaces;

import dao.dto.ProducerDto;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

/**
 * Created by Oleg Grigorjev 
 */
 
public interface ProducerDao extends Dao {
    Optional<ProducerDto> getById(int id);

    Collection<ProducerDto> getList();

    Collection<ProducerDto> getListByIds(Collection<Integer> ids);

    Map<Integer, ProducerDto> getMapByIds(Collection<Integer> ids);
}
