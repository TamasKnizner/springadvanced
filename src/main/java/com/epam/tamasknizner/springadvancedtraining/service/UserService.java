package com.epam.tamasknizner.springadvancedtraining.service;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.epam.tamasknizner.springadvancedtraining.domain.User;

/**
 * @author Yuriy_Tkach
 */
public interface UserService extends AbstractDomainObjectService<User> {

    /**
     * Finding user by email
     * 
     * @param email
     *            Email of the user
     * @return found user or <code>null</code>
     */
    @Nullable User getUserByEmail(@Nonnull String email);

    /**
     * Finding user by id
     *
     * @param id
     *          Id of the user
     * @return found user or <code>null</code>
     */
    @Nullable User getUserById(@Nonnull Long id);
}
