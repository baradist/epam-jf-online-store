package dao.interfaces;

import dao.dto.ContractorDto;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public interface ContractorDao extends Dao {

    int getQuantity();

    Optional<ContractorDto> getById(int id);

    Collection<ContractorDto> getList();

    Collection<ContractorDto> getList(int start, int end);

    Collection<ContractorDto> getListByIds(Collection<Integer> ids);

    Map<Integer, ContractorDto> getMapByIds(Collection<Integer> ids);

    boolean add(ContractorDto contractorDto);

    boolean update(ContractorDto contractorDto);

    boolean delete(int id);
}
