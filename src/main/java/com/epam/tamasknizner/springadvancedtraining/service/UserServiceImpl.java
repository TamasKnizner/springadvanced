package com.epam.tamasknizner.springadvancedtraining.service;

import com.epam.tamasknizner.springadvancedtraining.domain.User;
import com.epam.tamasknizner.springadvancedtraining.repository.DomainObjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Service
public class UserServiceImpl extends RepositoryBasedDomainObjectService<User> implements UserService {

    @Autowired
    public UserServiceImpl(DomainObjectRepository<User> userRepository) {
        super(userRepository);
    }

    @Nullable
    @Override
    public User getUserByEmail(@Nonnull String email) {
        return repository.findSingleOrDefault(u -> email.equalsIgnoreCase(u.getEmail()));
    }

    @Nullable
    @Override
    public User getUserById(@Nonnull Long id) {
        return repository.findById(id);
    }
}
