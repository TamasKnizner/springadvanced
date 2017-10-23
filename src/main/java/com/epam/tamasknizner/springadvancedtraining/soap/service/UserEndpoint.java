package com.epam.tamasknizner.springadvancedtraining.soap.service;

import com.epam.tamasknizner.springadvancedtraining.service.UserService;
import com.epam.tamasknizner.springadvancedtraining.soap.domain.User;
import com.epam.tamasknizner.springadvancedtraining.soap.transformer.SoapUserTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author Tamas_Knizner
 */
@Endpoint
public class UserEndpoint {

    private final UserService userService;
    private final SoapUserTransformer userTransformer;

    @Autowired
    public UserEndpoint(final UserService userService, final SoapUserTransformer userTransformer) {
        this.userService = userService;
        this.userTransformer = userTransformer;
    }

    @PayloadRoot(namespace = "http://localhost:8080/springadvancedtraining", localPart = "users")
    @ResponsePayload
    public Collection<User> getAllUsers() {
       return userService.getAll().stream().map(userTransformer::transform).collect(Collectors.toSet());
    }
}
