package com.epam.tamasknizner.springadvancedtraining.repository;

import com.epam.tamasknizner.springadvancedtraining.domain.DomainObject;

public interface DomainObjectRepository<TEntity extends DomainObject> extends Repository<TEntity, Long> {
}
