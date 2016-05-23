package dao.dto.converters;

import dao.dto.GoodDto;
import dao.interfaces.ProducerDao;
import model.Good;
import model.Producer;
import service.DaoHandler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

/**
 * Created by Oleg Grigorjev on 13.05.2016.
 */
 
public interface GoodConverter {
    ProducerDao producerDao = (ProducerDao) DaoHandler.getDaoByClass(Producer.class);

    static Good convert(GoodDto goodDto) {

        return new Good(
                goodDto.getId(),
                goodDto.getName(),
                ProducerConverter.convert(producerDao.getById(goodDto.getProducer()).get()),
                goodDto.getDescription());
    }

    static Good convert(GoodDto goodDto, Producer producer) {

        return new Good(
                goodDto.getId(),
                goodDto.getName(),
                producer,
                goodDto.getDescription());
    }

    static GoodDto convert(Good good) {
        return new GoodDto(
                good.getId(),
                good.getName(),
                good.getProducer().getId(),
                good.getDescription());
    }

    static Collection<Good> convert(Collection<GoodDto> goodDtos) {
        Collection<Integer> produserIds = new HashSet<>();
        for (GoodDto goodDto : goodDtos) {
            produserIds.add(goodDto.getProducer());
        }
        Map<Integer, Producer> map = ProducerConverter.convert(producerDao.getMapByIds(produserIds));
        Collection<Good> goods = new ArrayList<>();
        for (GoodDto goodDto : goodDtos) {
            goods.add(convert(goodDto, map.get(goodDto.getProducer())));
        }
        return goods;
    }
}
