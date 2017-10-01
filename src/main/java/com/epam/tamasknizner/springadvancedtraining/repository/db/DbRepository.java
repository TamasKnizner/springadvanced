package com.epam.tamasknizner.springadvancedtraining.repository.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import com.epam.tamasknizner.springadvancedtraining.repository.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class DbRepository<TEntity, TKey> implements Repository<TEntity, TKey> {

    protected JdbcTemplate jdbcTemplate;
    protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    protected final String tableName;
    protected final String idColumnName;
    protected final Function<TEntity, TKey> idGetter;
    protected final List<String> columnNames;

    protected DbRepository(
            String tableName,
            String idColumnName,
            Function<TEntity, TKey> idGetter,
            Collection<String> columnNames
    ) {
        this.tableName = tableName;
        this.idColumnName = idColumnName;
        this.idGetter = idGetter;
        this.columnNames = Collections.unmodifiableList(new ArrayList<>(columnNames));
    }

    protected List<String> getColumnNames() {
        return columnNames;
    }

    protected abstract MapSqlParameterSource getParamValues(TEntity entity, boolean forInsert);

    protected abstract TEntity convertToEntity(ResultSet resultSet) throws SQLException;

    @Override
    @Transactional
    public TEntity add(TEntity entity) {
        NamedParameterJdbcTemplate tpl = new NamedParameterJdbcTemplate(jdbcTemplate);
        MapSqlParameterSource paramValues = getParamValues(entity, true);
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        int rows = tpl.update(buildInsertQuery(paramValues), paramValues, generatedKeyHolder);
        if (rows != 1) {
            throw new RuntimeException(String.format("Expected exactly one row to be inserted, yet %d were",
                    rows));
        }
        handleGeneratedKey(entity, generatedKeyHolder);
        addReferencedEntities(entity);
        return entity;
    }

    protected void handleGeneratedKey(TEntity entity, GeneratedKeyHolder generatedKeyHolder) {
    }

    @Override
    @Transactional
    public void update(TEntity entity) {
        NamedParameterJdbcTemplate tpl = new NamedParameterJdbcTemplate(jdbcTemplate);
        MapSqlParameterSource paramValues = getParamValues(entity, false);
        int rows = tpl.update(buildUpdateQuery(), paramValues);
        if (rows != 1) {
            throw new RuntimeException(String.format("Expected exactly one row to be inserted, yet %d were",
                    rows));
        }
        updateReferencedEntities(entity);
    }

    @Override
    @Transactional
    public TEntity saveOrUpdate(TEntity entity) {
        TKey id = idGetter.apply(entity);
        if (id == null) {
            return add(entity);
        }
        TEntity existingEntity = findById(id);
        if (existingEntity != null) {
            update(entity);
            return entity;
        } else {
            return add(entity);
        }
    }

    @Override
    @Transactional
    public void remove(TEntity entity) {
        removeReferencedEntities(entity);
        NamedParameterJdbcTemplate tpl = new NamedParameterJdbcTemplate(jdbcTemplate);
        Map<String, Object> paramValues = new HashMap<>();
        paramValues.put(idColumnName, idGetter.apply(entity));
        int rows = tpl.update(buildDeleteByIdQuery(), paramValues);
        if (rows != 1) {
            throw new RuntimeException(String.format("Expected exactly one row to be deleted, " +
                    "but deleted %d instead", rows));
        }
    }

    @Override
    public TEntity findById(TKey id) {
        NamedParameterJdbcTemplate tpl = new NamedParameterJdbcTemplate(jdbcTemplate);
        Map<String, Object> paramValues = new HashMap<>();
        paramValues.put(idColumnName, id);
        final List<TEntity> entities = new ArrayList<>(1);
        tpl.query(buildSelectByIdQuery(), paramValues, rs -> {
            if (!entities.isEmpty()) {
                throw new RuntimeException(String.format(
                        "Too many records found in table %s by id %s",
                        tableName, id));
            }
            TEntity entity = convertToEntity(rs);
            entities.add(entity);
        });
        return entities.isEmpty() ? null : fetchReferencedEntities(entities.get(0));
    }

    @Override
    public Collection<TEntity> getAll() {
        NamedParameterJdbcTemplate tpl = new NamedParameterJdbcTemplate(jdbcTemplate);
        final List<TEntity> entities = new ArrayList<>();
        tpl.query(buildSelectAllQuery(), rs -> {
            entities.add(convertToEntity(rs));
        });
        return fetchReferencedEntities(entities);
    }

    private TEntity fetchReferencedEntities(TEntity entity) {
        return fetchReferencedEntities(Collections.singletonList(entity)).iterator().next();
    }

    protected Collection<TEntity> fetchReferencedEntities(Collection<TEntity> entities) {
        return entities;
    }

    protected void addReferencedEntities(TEntity entity) {
    }

    protected void removeReferencedEntities(TEntity entity) {
    }

    protected void updateReferencedEntities(TEntity entity) {
        removeReferencedEntities(entity);
        addReferencedEntities(entity);
    }

    protected String buildSelectAllQuery() {
        return String.format("SELECT %s FROM %s",
                String.join(", ", getColumnNames()), tableName);
    }

    protected String buildSelectByIdQuery() {
        return String.format("SELECT %s FROM %s%n" +
                        "WHERE %s = :%s",
                String.join(", ", getColumnNames()), tableName,
                idColumnName, idColumnName);
    }

    protected String buildInsertQuery(MapSqlParameterSource paramValues) {
        Collection<String> columnNames = paramValues.getValues().keySet();
        return String.format("INSERT INTO %s (%s)%n" +
                        "VALUES(%s)",
                tableName, String.join(",", columnNames),
                String.join(",", columnNames.stream()
                        .map(cn -> ":" + cn)
                        .collect(Collectors.toList())));
    }

    protected String buildUpdateQuery() {
        Collection<String> columnNames = getColumnNames();
        return String.format("UPDATE %s SET%n" +
                        "%s%n" +
                        "WHERE %s = :%s", tableName,
                String.join(",", columnNames.stream()
                        .filter(cn -> !idColumnName.equalsIgnoreCase(cn))
                        .map(cn -> String.format("%s = :%s%n", cn, cn))
                        .collect(Collectors.toList())),
                idColumnName, idColumnName);
    }

    protected String buildDeleteByIdQuery() {
        return String.format("DELETE FROM %s WHERE %s = :%s",
                tableName, idColumnName, idColumnName);
    }

    protected static LocalDate getDate(ResultSet resultSet, String fieldName) throws SQLException {
        return deserializeDate(resultSet.getString(fieldName));
    }

    protected static LocalDateTime getDateTime(ResultSet resultSet, String fieldName) throws SQLException {
        return deserializeDateTime(resultSet.getString(fieldName));
    }

    protected static LocalDate deserializeDate(String dateStr) {
        return StringUtils.isEmpty(dateStr) ? null : LocalDate.parse(dateStr, DateTimeFormatter.ISO_DATE);
    }

    protected static LocalDateTime deserializeDateTime(String dateTimeStr) {
        return StringUtils.isEmpty(dateTimeStr) ? null : LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ISO_DATE_TIME);
    }

    protected static String serializeDate(LocalDate date) {
        return date == null ? null : date.format(DateTimeFormatter.ISO_DATE);
    }

    protected static String serializeDateTime(LocalDateTime dateTime) {
        return dateTime == null ? null : dateTime.format(DateTimeFormatter.ISO_DATE_TIME);
    }

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Autowired
    public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }
}
