package dao.dto.converters;

import dao.dto.GoodDto;
import dao.interfaces.ProducerDao;
import listeners.DbInitializer;
import model.Good;
import model.Producer;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Oleg Grigorjev on 13.05.2016.
 */
public interface GoodConverter {
    static Good convert(GoodDto goodDto) {
        return new Good(
                goodDto.getId(),
                goodDto.getName(),
                ProducerConverter.convert(((ProducerDao) DbInitializer.getDaoByClass(Producer.class)).getById(goodDto.getProducer()).get()),
                goodDto.getDescription()
        );
    }

    static Collection<Good> convert(Collection<GoodDto> goodDtos) { // TODO
        Collection<Good> goods = new ArrayList<>();
        for (GoodDto goodDto : goodDtos) {
            goods.add(convert(goodDto));
        }
        return goods;
    }

    static GoodDto convert(Good good) {
        return new GoodDto(
                good.getId(),
                good.getName(),
                good.getProducer().getId(),
                good.getDescription()
        );
    }
}
