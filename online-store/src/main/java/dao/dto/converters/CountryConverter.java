package dao.dto.converters;

import dao.dto.CountryDto;
import model.Country;

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

}
