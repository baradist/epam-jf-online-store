package dao.interfaces;

import dao.dto.PriceItemDto;

import java.util.Collection;
import java.util.Optional;

public interface PriceItemDao extends Dao {
    Optional<PriceItemDto> getById(int id);

    Collection<PriceItemDto> getList();

    Collection<PriceItemDto> getListForPersonsEmail(String email);
}
