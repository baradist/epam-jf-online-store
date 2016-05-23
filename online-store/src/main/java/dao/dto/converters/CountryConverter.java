package dao.dto.converters;

import dao.dto.CountryDto;
import model.Country;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Oleg Grigorjev on 13.05.2016.
 */
public interface CountryConverter {
    static Country convert(CountryDto countryDto) {
        return new Country(countryDto.getId(), countryDto.getName());
    }

    static CountryDto convert(Country country) {
        return new CountryDto(country.getId(), country.getName());
    }

    static Map<Integer, Country> convert(Map<Integer, CountryDto> countryDtoMap) {
        Map<Integer, Country> map = new HashMap<>();
        for (CountryDto countryDto : countryDtoMap.values()) {
            map.put(countryDto.getId(), convert(countryDto));
        }
        return map;
    }

    static Collection<Country> convert(Collection<CountryDto> countryDtos) {
        Map<Integer, CountryDto> countryDtoMap = new HashMap<>(countryDtos.size());
        for (CountryDto producerDto : countryDtos) {
            countryDtoMap.put(producerDto.getId(), producerDto);
        }
        return convert(countryDtoMap).values();
    }
}
