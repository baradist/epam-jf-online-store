package dao.mysql;

import common.functions.Helper;
import dao.interfaces.PersonDao;
import dao.dto.PersonDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.util.*;

@FunctionalInterface
public interface MySqlPersonDao extends PersonDao {

    String SELECT = "SELECT id, email, first_name, last_name, dob, password, address, phone FROM person ";

    @Override
    default Optional<PersonDto> getById(int id) {
        return executeQuery(
                SELECT + " WHERE id = " + id,
                rs -> rs.next()
                        ? getValue(rs)
                        : null
        ).toOptional();
    }

    @Override
    default Optional<PersonDto> getByEmail(String email) {
        return executeQuery(
                SELECT + " WHERE email = '" + email + "'",
                rs -> rs.next()
                        ? getValue(rs)
                        : null
        ).toOptional();
    }

    @Override
    default Collection<PersonDto> getListByIds(Collection<Integer> ids) { // TODO
        return getMapByIds(ids).values();
    }

    @Override
    default Collection<PersonDto> getList() {
        return executeQuery(
                SELECT,
                rs -> {
                    Map<Integer, PersonDto> map = new HashMap<>();
                    while (rs.next())
                        map.put(rs.getInt("ID"),
                                getValue(rs));
                    return map;
                }).toOptional().orElse(Collections.emptyMap()).values();
    }

    @Override
    default Map<Integer, PersonDto> getMapByIds(Collection<Integer> ids) {
        return executeQuery(
                SELECT + " WHERE id IN (" + Helper.ArrayToString(ids) + ")",
                rs -> {
                    Map<Integer, PersonDto> map = new HashMap<>();
                    while (rs.next())
                        map.put(rs.getInt("ID"),
                                getValue(rs));
                    return map;
                }).toOptional().orElse(Collections.emptyMap());
    }

    default PersonDto getValue(ResultSet rs) throws SQLException {
        return new PersonDto(
                rs.getInt("id"),
                rs.getString("email"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getDate("dob").toLocalDate(),
                rs.getString("password"), // TODO
                rs.getString("address"),
                rs.getString("phone")
        );
    }

    @Override
    default boolean delete(int id) {
        return withPreparedStatement("DELETE FROM person WHERE id = ?", preparedStatement -> {
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() == 1;
        }).getOrThrowUnchecked();
    }

    @Override
    default boolean deleteByEmail(String email) {
        return withPreparedStatement("DELETE FROM person WHERE email = ?", preparedStatement -> {
            preparedStatement.setString(1, email);
            return preparedStatement.executeUpdate() == 1;
        }).getOrThrowUnchecked();
    }

    @Override
    default boolean update(PersonDto personDto) {
        return withPreparedStatement(
                "UPDATE person SET email = ?, first_name = ?, last_name = ?, dob = ?, password = ?, address = ?, phone = ? " +
                        "WHERE id = ?"
                , preparedStatement -> {
                    preparedStatement.setString(1, personDto.getEmail());
                    preparedStatement.setString(2, personDto.getFirstName());
                    preparedStatement.setString(3, personDto.getLastName());
                    preparedStatement.setDate(4, java.sql.Date.valueOf(personDto.getDob()));
                    preparedStatement.setString(5, personDto.getPassword());
                    preparedStatement.setString(6, personDto.getAddress());
                    preparedStatement.setString(7, personDto.getPhone());
                    preparedStatement.setInt(8, personDto.getId());
                    return preparedStatement.executeUpdate() == 1;
                }).getOrThrowUnchecked();
    }

    @Override
    default boolean add(PersonDto personDto) {
        return withPreparedStatement(
                "INSERT INTO person (email, first_name, last_name, dob, password, address, phone) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?)",
                preparedStatement -> {
                    preparedStatement.setString(1, personDto.getEmail());
                    preparedStatement.setString(2, personDto.getFirstName());
                    preparedStatement.setString(3, personDto.getLastName());
                    LocalDate dob = personDto.getDob();
                    if (dob == null) {
                        preparedStatement.setNull(4, Types.DATE);
                    } else {
                        preparedStatement.setDate(4, java.sql.Date.valueOf(dob));
                    }
                    preparedStatement.setString(5, personDto.getPassword());
                    preparedStatement.setString(6, personDto.getAddress());
                    preparedStatement.setString(7, personDto.getPhone());
                    return preparedStatement.executeUpdate() == 1;
                }).getOrThrowUnchecked();
    }

    @Override
    default boolean addRole(String email, String role) {
        return withPreparedStatement(
                "INSERT INTO roles (email, role) VALUES (?, ?)",
                preparedStatement -> {
                    preparedStatement.setString(1, email);
                    preparedStatement.setString(2, role);
                    return preparedStatement.executeUpdate() == 1;
                }).getOrThrowUnchecked();
    }
}
