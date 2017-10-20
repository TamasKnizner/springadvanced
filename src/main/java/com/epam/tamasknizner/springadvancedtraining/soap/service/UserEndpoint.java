package com.epam.tamasknizner.springadvancedtraining.soap.service;

import com.epam.tamasknizner.springadvancedtraining.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;

/**
 * TODO.
 *
 * @author Tamas_Knizner
 */
@Endpoint
public class UserEndpoint {

    private static final String NAMESPACE_URI = "";

    private UserService userService;

    @Autowired
    public UserEndpoint(UserService userService) {
        this.userService = userService;
    }
}
