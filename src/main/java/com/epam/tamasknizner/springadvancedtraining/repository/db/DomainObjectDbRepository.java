package com.epam.tamasknizner.springadvancedtraining.repository.db;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import com.epam.tamasknizner.springadvancedtraining.domain.DomainObject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class DomainObjectDbRepository<TEntity extends DomainObject>
        extends DbRepository<TEntity, Long> {
    protected static final String ID_COLUMN_NAME = "id";

    protected DomainObjectDbRepository(
            String tableName,
            Collection<String> columnNames) {
        super(tableName, ID_COLUMN_NAME, DomainObject::getId,
                getColumnNamesWithId(columnNames));
    }

    @Override
    protected final TEntity convertToEntity(ResultSet resultSet) throws SQLException {
        TEntity entity = convertToEntityInternal(resultSet);
        entity.setId(resultSet.getLong(ID_COLUMN_NAME));
        return entity;
    }

    protected abstract TEntity convertToEntityInternal(ResultSet resultSet) throws SQLException;

    private static Collection<String> getColumnNamesWithId(Collection<String> columnNames) {
        return Stream.concat(columnNames.stream(),
                Stream.of("id"))
                .collect(Collectors.toSet());
    }

    @Override
    protected final MapSqlParameterSource getParamValues(TEntity entity, boolean forInsert) {
        MapSqlParameterSource paramValues = new MapSqlParameterSource();
        fillParamValues(paramValues, entity);
        if (forInsert) {
            if (paramValues.hasValue(ID_COLUMN_NAME)) {
                throw new IllegalStateException();
            }
        } else {
            paramValues.addValue("id", entity.getId());
        }
        return paramValues;
    }

    protected abstract void fillParamValues(MapSqlParameterSource paramValues, TEntity entity);

    @Override
    protected final void handleGeneratedKey(TEntity entity, GeneratedKeyHolder generatedKeyHolder) {
        entity.setId((long)generatedKeyHolder.getKey());
    }

    protected final Map<Long, TEntity> mapById(Collection<TEntity> entities) {
        return entities.stream()
                .collect(Collectors.toMap(DomainObject::getId, evt -> evt));
    }
}
