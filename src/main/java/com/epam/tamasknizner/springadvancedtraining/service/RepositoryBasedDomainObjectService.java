package com.epam.tamasknizner.springadvancedtraining.service;

import com.epam.tamasknizner.springadvancedtraining.domain.DomainObject;
import com.epam.tamasknizner.springadvancedtraining.repository.DomainObjectRepository;

import javax.annotation.Nonnull;
import java.util.Collection;

public abstract class RepositoryBasedDomainObjectService<TEntity extends DomainObject>
        implements AbstractDomainObjectService<TEntity> {

    protected final DomainObjectRepository<TEntity> repository;

    protected RepositoryBasedDomainObjectService(DomainObjectRepository<TEntity> repository) {
        this.repository = repository;
    }

    @Override
    public TEntity save(@Nonnull TEntity object) {
        return repository.saveOrUpdate(object);
    }

    @Override
    public void remove(@Nonnull TEntity object) {
        repository.remove(object);
    }

    @Override
    public TEntity getById(@Nonnull Long id) {
        return repository.findById(id);
    }

    @Nonnull
    @Override
    public Collection<TEntity> getAll() {
        return repository.getAll();
    }
}
