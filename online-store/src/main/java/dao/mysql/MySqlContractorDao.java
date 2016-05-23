package dao.mysql;

import service.Helper;
import dao.dto.ContractorDto;
import dao.interfaces.ContractorDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by Oleg Grigorjev 
 */
 
@FunctionalInterface
public interface MySqlContractorDao extends ContractorDao {

    String SELECT = "SELECT id, name FROM contractor ";

    @Override
    default int getQuantity() {
        return executeQuery(
                "SELECT count(*) AS count FROM contractor",
                rs -> rs.next()
                        ? Integer.valueOf(rs.getInt("count"))
                        : 0
        ).toOptional().get();
    }

    @Override
    default Optional<ContractorDto> getById(int id) {
        return executeQuery(
                SELECT + " WHERE id = " + id,
                rs -> rs.next()
                        ? getValue(rs)
                        : null
        ).toOptional();
    }

    @Override
    default Collection<ContractorDto> getList() {
        return getList(-1, 0);
    }

    @Override
    default Collection<ContractorDto> getList(int offset, int rows) {
        String sql = SELECT +
                " ORDER BY name ";
        if (offset >= 0 && rows > 0) {
            sql += " LIMIT " + offset + ", " + rows;
        }
        return executeQuery(
                sql,
                rs -> {
                    Map<Integer, ContractorDto> map = new HashMap<>();
                    while (rs.next())
                        map.put(rs.getInt("id"),
                                getValue(rs));
                    return map;
                }).toOptional().orElse(Collections.emptyMap()).values();
    }

    @Override
    default Collection<ContractorDto> getListByIds(Collection<Integer> ids) { // TODO
        return getMapByIds(ids).values();
    }

    @Override
    default Map<Integer, ContractorDto> getMapByIds(Collection<Integer> ids) {
        return executeQuery(
                SELECT + " WHERE id IN (" + Helper.ArrayToString(ids) + ")",
                rs -> {
                    Map<Integer, ContractorDto> map = new HashMap<>();
                    while (rs.next())
                        map.put(rs.getInt("id"),
                                getValue(rs));
                    return map;
                }).toOptional().orElse(Collections.emptyMap());
    }

    default ContractorDto getValue(ResultSet rs) throws SQLException {
        return new ContractorDto(
                rs.getInt("id"), rs.getString("name")
        );
    }

    @Override
    default boolean add(ContractorDto goodDto) {
        return withPreparedStatement(
                "INSERT INTO contractor (name) VALUES (?)",
                preparedStatement -> {
                    preparedStatement.setString(1, goodDto.getName());
                    return preparedStatement.executeUpdate() == 1;
                }).getOrThrowUnchecked();
    }

    @Override
    default boolean update(ContractorDto goodDto) {
        return withPreparedStatement(
                "UPDATE contractor SET name = ? WHERE id = ?"
                , preparedStatement -> {
                    preparedStatement.setString(1, goodDto.getName());
                    preparedStatement.setInt(2, goodDto.getId());
                    return preparedStatement.executeUpdate() == 1;
                }).getOrThrowUnchecked();
    }

    @Override
    default boolean delete(int id) {
        return withPreparedStatement("DELETE FROM contractor ID = ?", preparedStatement -> {
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() == 1;
        }).getOrThrowUnchecked();
    }
}
