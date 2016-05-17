package dao.interfaces;

import dao.dto.PriceItemDto;

import java.util.Collection;
import java.util.Optional;

public interface PriceItemDao extends Dao {
    int getQuantity();

    Optional<PriceItemDto> getById(int id);

    Collection<PriceItemDto> getList();

    Collection<PriceItemDto> getList(int start, int end);

    Collection<PriceItemDto> getListForPersonsEmail(String email);

    Collection<PriceItemDto> getListForPersonsEmail(String email, int start, int end);
}
