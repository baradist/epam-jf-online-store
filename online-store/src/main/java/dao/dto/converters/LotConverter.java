package dao.dto.converters;

import dao.dto.LotDto;
import dao.interfaces.GoodDao;
import model.Good;
import model.Lot;
import service.DaoHandler;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Oleg Grigorjev on 13.05.2016.
 */
public interface LotConverter {
    static Lot convert(LotDto lotDto) { // TODO
        return new Lot(
                lotDto.getId(),
                null, //lotDto.getStore(),
                GoodConverter.convert(((GoodDao) DaoHandler.getDaoByClass(Good.class)).getById(lotDto.getGood()).get()),
                null, //lotDto.getInvoice(),
                null, //lotDto.getInvoiceItem(),
                lotDto.getQuantity(),
                lotDto.getQuantityRest(),
                null, //lotDto.getSupplier(),
                lotDto.getPriceSup(),
                lotDto.getPriceSal()
        );
    }

    static Collection<Lot> convert(Collection<LotDto> lotDtos) { // TODO
        Collection<Lot> lots = new ArrayList<>();
        for (LotDto lotDto : lotDtos) {
            lots.add(convert(lotDto));
        }
        return lots;
    }

    static LotDto convert(Lot lot) {
        return new LotDto(
                lot.getId(),
                lot.getStore().getId(),
                lot.getGood().getId(),
                lot.getInvoice().getId(),
                lot.getInvoiceItem().getId(),
                lot.getQuantity(),
                lot.getQuantityRest(),
                lot.getSupplier().getId(),
                lot.getPriceSup(),
                lot.getPriceSal()
        );
    }
}
