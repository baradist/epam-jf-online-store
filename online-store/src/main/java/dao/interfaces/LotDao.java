package dao.interfaces;

import dao.dto.LotDto;

import java.util.Collection;
import java.util.Optional;

public interface LotDao extends Dao {
    Optional<LotDto> getById(int id);

    Collection<LotDto> getList();
}
