package com.epam.tamasknizner.springadvancedtraining.repository.mapBased;

import com.epam.tamasknizner.springadvancedtraining.domain.DomainObject;
import com.epam.tamasknizner.springadvancedtraining.repository.DomainObjectRepository;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicLong;

public abstract class MapBasedIdentityRepository<TEntity extends DomainObject>
        extends MapBasedRepository<TEntity, Long>
        implements DomainObjectRepository<TEntity> {

    private final AtomicLong maxId = new AtomicLong();

    protected MapBasedIdentityRepository() {
        super(DomainObject::getId, DomainObject::setId);
    }

    protected MapBasedIdentityRepository(Collection<TEntity> entities) {
        super(DomainObject::getId, DomainObject::setId);
        for (TEntity entity : entities) {
            Long id = entity.getId();
            add(id == null ? maxId.getAndIncrement() : id, entity);
        }
    }

//    protected MapBasedIdentityRepository(ObjectLoader<Collection<TEntity>> loader) {
//        this(loader.load());
//    }

    @Override
    public TEntity add(TEntity entity) {
        return super.add(maxId.getAndIncrement(), entity);
    }

    @Override
    public TEntity saveOrUpdate(TEntity entity) {
        return super.saveOrUpdate(entity.getId() == null ? maxId.getAndIncrement() : entity.getId(), entity);
    }

}
