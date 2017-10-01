package com.epam.tamasknizner.springadvancedtraining.repository.mapbased;

import com.epam.tamasknizner.springadvancedtraining.repository.Repository;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class MapBasedRepository<TEntity, TKey> implements Repository<TEntity, TKey> {
    private final Function<TEntity, TKey> idGetter;
    private final BiConsumer<TEntity, TKey> idSetter;
    protected final ConcurrentHashMap<TKey, TEntity> entitiesMap = new ConcurrentHashMap<>();

    protected MapBasedRepository(@Nonnull Function<TEntity, TKey> idGetter, @Nonnull BiConsumer<TEntity, TKey> idSetter) {
        this.idGetter = idGetter;
        this.idSetter = idSetter;
    }

    protected MapBasedRepository(@Nonnull Function<TEntity, TKey> idGetter, @Nonnull BiConsumer<TEntity, TKey> idSetter, @Nonnull Collection<TEntity> entities) {
        this(idGetter, idSetter);
        for (TEntity entity : entities) {
            add(idGetter.apply(entity), entity);
        }
    }

    @Override
    public TEntity add(TEntity entity) {
        return add(idGetter.apply(entity), entity);
    }

    final TEntity add(TKey id, TEntity entity) {
        return entitiesMap.compute(id, (key, value) -> {
            if (value != null) {
                throw new RuntimeException(String.format("Duplicate entity id: %s", key));
            }
            idSetter.accept(entity, key);
            return entity;
        });
    }

    @Override
    public TEntity saveOrUpdate(TEntity entity) {
        return saveOrUpdate(idGetter.apply(entity), entity);
    }

    TEntity saveOrUpdate(TKey id, TEntity entity) {
        return entitiesMap.compute(id, (key, value) -> {
            if (value == null) {
                idSetter.accept(entity, key);
            }
            return entity;
        });
    }

    @Override
    public void update(TEntity entity) {
        TKey id = idGetter.apply(entity);
        if (id == null) {
            throw new RuntimeException(String.format("Entity id not specified: %s", entity));
        }
        TEntity existingEntity = entitiesMap.computeIfPresent(id, (key, value) -> value);
        if (existingEntity == null) {
            throw new RuntimeException(String.format("Entity with id %s is not present in the repository", id));
        }
    }

    @Override
    public List<TEntity> getAll() {
        return new ArrayList<>(entitiesMap.values());
    }

    @Override
    public void remove(TEntity entity) {
        TKey id = idGetter.apply(entity);
        if (entitiesMap.remove(id) == null) {
            throw new RuntimeException(String.format("Entity with id %s was not present in the repository", id));
        }
    }

    @Override
    public TEntity findById(TKey id) {
        if (id == null) {
            throw new RuntimeException("An id has not been specified");
        }
        return entitiesMap.get(id);
    }

    @Override
    @Nonnull
    public Set<TEntity> find(Predicate<TEntity> predicate) {
        return entitiesMap.values().stream()
                .filter(predicate)
                .collect(Collectors.toSet());
    }
}
