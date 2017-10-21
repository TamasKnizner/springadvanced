package com.epam.tamasknizner.springadvancedtraining.rest.transformer;

import com.epam.tamasknizner.springadvancedtraining.domain.User;
import com.epam.tamasknizner.springadvancedtraining.rest.domain.UserRequest;
import org.springframework.stereotype.Component;

@Component
public class UserTransformer {

    public User transform(final Long id, final UserRequest userRequest) {
        User user = new User();
        user.setId(id);
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setRoles(userRequest.getRoles());
        user.setBirthday(userRequest.getBirthday());
        return user;
    }

    public User transform(final UserRequest userRequest) {
        User user = new User();
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setRoles(userRequest.getRoles());
        user.setBirthday(userRequest.getBirthday());
        return user;
    }

}
