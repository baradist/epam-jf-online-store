package dao.dto.converters;

import dao.dto.ContractorDto;
import model.Contractor;

/**
 * Created by Oleg Grigorjev on 13.05.2016.
 */
public interface ContractorConverter {
    static Contractor convert(ContractorDto dto) {
        return new Contractor(dto.getId(), dto.getName());
    }

    static ContractorDto convert(Contractor contractor) {
        return new ContractorDto(contractor.getId(), contractor.getName());
    }

}
