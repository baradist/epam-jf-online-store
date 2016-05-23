package dao.dto.converters;

import dao.dto.SetPriceDto;
import dao.interfaces.PersonDao;
import model.Person;
import model.SetPrice;
import service.DaoHandler;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Oleg Grigorjev on 13.05.2016.
 */
 
public interface SetPriceConverter {
    static SetPrice convert(SetPriceDto dto) {
        return new SetPrice(
                dto.getId(),
                dto.getNumber(),
                dto.getDate(),
                PersonConverter.convert(((PersonDao) DaoHandler.getDaoByClass(Person.class)).getById(dto.getManager()).get())
        );
    }

    static Collection<SetPrice> convert(Collection<SetPriceDto> dtos) { // TODO
        Collection<SetPrice> orders = new ArrayList<>();
        for (SetPriceDto dto : dtos) {
            orders.add(convert(dto));
        }
        return orders;
    }

    static SetPriceDto convert(SetPrice invoice) {
        return new SetPriceDto(
                invoice.getId(),
                invoice.getNumber(),
                invoice.getDate(),
                invoice.getManager().getId()
        );
    }
}
