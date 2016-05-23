package dao.dto.converters;

import dao.dto.SetPriceItemDto;
import dao.interfaces.LotDao;
import dao.interfaces.SetPriceDao;
import model.Lot;
import model.SetPrice;
import model.SetPriceItem;
import service.DaoHandler;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Oleg Grigorjev on 13.05.2016.
 */
 
public interface SetPriceItemConverter {
    static SetPriceItem convert(SetPriceItemDto dto) {
        return new SetPriceItem(
                dto.getId(),
                SetPriceConverter.convert(((SetPriceDao) DaoHandler.getDaoByClass(SetPrice.class)).getById(dto.getSetPrice()).get()),
                LotConverter.convert(((LotDao) DaoHandler.getDaoByClass(Lot.class)).getById(dto.getLot()).get()),
                dto.getIncrease(),
                dto.getPriceSal()
        );
    }

    static Collection<SetPriceItem> convert(Collection<SetPriceItemDto> dtos) { // TODO
        Collection<SetPriceItem> orders = new ArrayList<>();
        for (SetPriceItemDto dto : dtos) {
            orders.add(convert(dto));
        }
        return orders;
    }

    static SetPriceItemDto convert(SetPriceItem invoice) {
        return new SetPriceItemDto(
                invoice.getId(),
                invoice.getSetPrice().getId(),
                invoice.getLot().getId(),
                invoice.getIncrease(),
                invoice.getPriceSal()
        );
    }
}
