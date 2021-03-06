package dao.interfaces;

import dao.dto.PersonDto;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

/**
 * Created by Oleg Grigorjev 
 */
 
public interface PersonDao extends Dao {
    int getQuantity();

    Optional<PersonDto> getById(int id);

    Optional<PersonDto> getByEmail(String email);

    Collection<PersonDto> getList();

    Collection<PersonDto> getList(int start, int end);

    Collection<PersonDto> getListByIds(Collection<Integer> ids);

    Map<Integer, PersonDto> getMapByIds(Collection<Integer> ids);

    boolean add(PersonDto personDto);

    boolean update(PersonDto personDto);

    boolean delete(int id);

    boolean deleteByEmail(String email);

    boolean addRole(String email, String role);
}
