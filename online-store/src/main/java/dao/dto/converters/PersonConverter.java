package dao.dto.converters;

import dao.dto.PersonDto;
import model.Person;

/**
 * Created by Oleg Grigorjev on 13.05.2016.
 */
public interface PersonConverter {
    static Person convert(PersonDto personDto) {
        return new Person(
                personDto.getId(),
                personDto.getEmail(),
                personDto.getFirstName(),
                personDto.getLastName(),
                personDto.getDob(),
                personDto.getPassword(),
                personDto.getAddress(),
                personDto.getPhone());
    }

    static PersonDto convert(Person person) {
        return new PersonDto(
                person.getId(),
                person.getEmail(),
                person.getFirstName(),
                person.getLastName(),
                person.getDob(),
                person.getPassword(),
                person.getAddress(),
                person.getPhone());
    }

}
