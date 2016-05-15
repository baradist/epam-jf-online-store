package dao.dto.converters;

import dao.dto.PriceItemDto;
import dao.interfaces.GoodDao;
import listeners.DbInitializer;
import model.Good;
import model.PriceItem;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Oleg Grigorjev on 13.05.2016.
 */
public interface PriceItemConverter {
    static PriceItem convert(PriceItemDto itemDto) {
        return new PriceItem(
                GoodConverter.convert(((GoodDao) DbInitializer.getDaoByClass(Good.class)).getById(itemDto.getGood()).get()),
                itemDto.getQuantity(),
                itemDto.getQuantityOrdered(),
                itemDto.getPrice()
        );
    }

    static Collection<PriceItem> convert(Collection<PriceItemDto> lotDtos) { // TODO
        Collection<PriceItem> items = new ArrayList<>();
        for (PriceItemDto lotDto : lotDtos) {
            items.add(convert(lotDto));
        }
        return items;
    }

    static PriceItemDto convert(PriceItem item) {
        return new PriceItemDto(
                item.getGood().getId(),
                item.getQuantity(),
                item.getQuantityOrdered(),
                item.getPrice()
        );
    }
}
