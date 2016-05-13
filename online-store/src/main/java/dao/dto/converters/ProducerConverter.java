package dao.dto.converters;

import dao.dto.ProducerDto;
import dao.interfaces.CountryDao;
import listeners.DbInitializer;
import model.Country;
import model.Producer;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Oleg Grigorjev on 13.05.2016.
 */
public interface ProducerConverter {
    static Producer convert(ProducerDto producerDto) {
        return new Producer(
                producerDto.getId(),
                producerDto.getName(),
                CountryConverter.convert(((CountryDao) DbInitializer.getDaoByClass(Country.class)).getById(producerDto.getCountry()).get())
        );
    }

    static Collection<Producer> convert(Collection<ProducerDto> producerDtos) { // TODO
        Collection<Producer> producers = new ArrayList<>();
        for (ProducerDto producerDto : producerDtos) {
            producers.add(convert(producerDto));
        }
        return producers;
    }

    static ProducerDto convert(Producer producer) {
        return new ProducerDto(
                producer.getId(),
                producer.getName(),
                producer.getCountry().getId()
        );
    }
}
