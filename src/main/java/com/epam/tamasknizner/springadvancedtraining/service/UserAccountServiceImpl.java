package com.epam.tamasknizner.springadvancedtraining.service;

import com.epam.tamasknizner.springadvancedtraining.domain.UserAccount;
import com.epam.tamasknizner.springadvancedtraining.repository.DomainObjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.util.Collection;

@Service
public class UserAccountServiceImpl extends RepositoryBasedDomainObjectService<UserAccount> implements UserAccountService {

    @Autowired
    public UserAccountServiceImpl(final DomainObjectRepository<UserAccount> repository) {
        super(repository);
    }

    @Override
    public UserAccount save(@Nonnull final UserAccount userAccount) {
        return repository.add(userAccount);
    }

    @Override
    public void remove(@Nonnull final UserAccount userAccount) {
        repository.remove(userAccount);
    }

    @Override
    public UserAccount getById(@Nonnull final Long id) {
        return repository.findById(id);
    }

    @Nonnull
    @Override
    public Collection<UserAccount> getAll() {
        return repository.getAll();
    }
}
