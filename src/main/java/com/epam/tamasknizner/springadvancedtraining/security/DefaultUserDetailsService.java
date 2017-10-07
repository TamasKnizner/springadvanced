package com.epam.tamasknizner.springadvancedtraining.security;

import com.epam.tamasknizner.springadvancedtraining.domain.User;
import com.epam.tamasknizner.springadvancedtraining.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("defaultUserDetailsService")
public class DefaultUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public DefaultUserDetailsService(final UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        User foundUser = userService.getUserByEmail(email);
        if (foundUser == null) {
            throw new UsernameNotFoundException("User not found with email " + email);
        }
        return new UserPrincipal(foundUser);
    }
}
