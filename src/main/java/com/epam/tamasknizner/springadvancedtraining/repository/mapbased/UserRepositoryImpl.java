package com.epam.tamasknizner.springadvancedtraining.repository.mapbased;

import com.epam.tamasknizner.springadvancedtraining.domain.User;
import com.epam.tamasknizner.springadvancedtraining.repository.UserRepository;

import java.util.Collection;

public class UserRepositoryImpl extends MapBasedIdentityRepository<User> implements UserRepository {

    public UserRepositoryImpl() {
    }

    public UserRepositoryImpl(Collection<User> users) {
        super(users);
    }
}
