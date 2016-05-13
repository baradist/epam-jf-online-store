package dao.interfaces;

import dao.dto.CountryDto;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public interface CountryDao extends Dao {
    Optional<CountryDto> getById(int id);

    Collection<CountryDto> getList();

    Collection<CountryDto> getListByIds(Collection<Integer> ids);

    Map<Integer, CountryDto> getMapByIds(Collection<Integer> ids);
}
