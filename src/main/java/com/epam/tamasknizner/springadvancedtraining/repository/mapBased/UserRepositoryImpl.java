package com.epam.tamasknizner.springadvancedtraining.repository.mapBased;

import com.epam.tamasknizner.springadvancedtraining.domain.User;
import com.epam.tamasknizner.springadvancedtraining.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class UserRepositoryImpl
        extends MapBasedIdentityRepository<User>
        implements UserRepository {
    public UserRepositoryImpl() {
    }

    public UserRepositoryImpl(Collection<User> users) {
        super(users);
    }
}
