package dao.dto.converters;

import dao.dto.ProducerDto;
import dao.interfaces.CountryDao;
import model.Country;
import model.Producer;
import service.DaoHandler;

import java.util.*;

/**
 * Created by Oleg Grigorjev on 13.05.2016.
 */
public interface ProducerConverter {
    CountryDao countryDao = (CountryDao) DaoHandler.getDaoByClass(Country.class);
    static Producer convert(ProducerDto producerDto) {
        return new Producer(
                producerDto.getId(),
                producerDto.getName(),
                CountryConverter.convert(countryDao.getById(producerDto.getCountry()).get()));
    }

    static Producer convert(ProducerDto producerDto, Country country) {
        return new Producer(
                producerDto.getId(),
                producerDto.getName(),
                country);
    }

    static ProducerDto convert(Producer producer) {
        return new ProducerDto(
                producer.getId(),
                producer.getName(),
                producer.getCountry().getId());
    }

    static Collection<Producer> convert(Collection<ProducerDto> producerDtos) {
        Map<Integer, ProducerDto> producerDtoMap = new HashMap<>(producerDtos.size());
        for (ProducerDto producerDto : producerDtos) {
            producerDtoMap.put(producerDto.getId(), producerDto);
        }
        return convert(producerDtoMap).values();
    }

    static Map<Integer, Producer> convert(Map<Integer, ProducerDto> producerDtoMap) {
        Collection<Integer> countryIds = new HashSet<>();
        for (ProducerDto producerDto : producerDtoMap.values()) {
            countryIds.add(producerDto.getCountry());
        }
        Map<Integer, Country> map = CountryConverter.convert(countryDao.getMapByIds(countryIds));
        Map<Integer, Producer> producerMap = new HashMap<>();
        for (ProducerDto producerDto : producerDtoMap.values()) {
            producerMap.put(producerDto.getId(), convert(producerDto, map.get(producerDto.getCountry())));
        }
        return producerMap;
    }
}
