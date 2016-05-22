package dao.dto.converters;

import dao.dto.StoreDto;
import model.Store;

/**
 * Created by Oleg Grigorjev on 13.05.2016.
 */
public interface StoreConverter {
    static Store convert(StoreDto storeDto) {
        return new Store(storeDto.getId(), storeDto.getName(), storeDto.getAddress());
    }

    static StoreDto convert(Store store) {
        return new StoreDto(store.getId(), store.getName(), store.getAddress());
    }

}
