package com.epam.tamasknizner.springadvancedtraining.repository.mapbased;

import com.epam.tamasknizner.springadvancedtraining.domain.UserAccount;
import com.epam.tamasknizner.springadvancedtraining.repository.UserAccountRepository;
import org.springframework.stereotype.Component;

@Component
public class UserAccountRepositoryImpl extends MapBasedIdentityRepository<UserAccount> implements UserAccountRepository {

}
