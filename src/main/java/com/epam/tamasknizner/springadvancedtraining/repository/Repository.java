package com.epam.tamasknizner.springadvancedtraining.repository;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public interface Repository<TEntity, TId> {
    TEntity add(TEntity entity);

    void update(TEntity entity);

    TEntity saveOrUpdate(TEntity entity);

    void remove(TEntity entity);

    Collection<TEntity> getAll();

    TEntity findById(TId id);

    default Set<TEntity> find(Predicate<TEntity> predicate) {
        return getAll().stream()
                .filter(predicate)
                .collect(Collectors.toSet());
    }

    @Nullable
    default TEntity findSingleOrDefault(Predicate<TEntity> predicate) {
        Set<TEntity> found = find(predicate);
        if (found.isEmpty()) {
            return null;
        }
        if (found.size() > 1) {
            throw new RuntimeException(String.format("Expected either 0 or 1, but %d found", found.size()));
        }
        return found.iterator().next();
    }
}
