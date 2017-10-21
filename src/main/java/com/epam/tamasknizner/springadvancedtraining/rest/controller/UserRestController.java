package com.epam.tamasknizner.springadvancedtraining.rest.controller;

import com.epam.tamasknizner.springadvancedtraining.domain.User;
import com.epam.tamasknizner.springadvancedtraining.rest.domain.UserRequest;
import com.epam.tamasknizner.springadvancedtraining.rest.transformer.UserTransformer;
import com.epam.tamasknizner.springadvancedtraining.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/api/user")
public class UserRestController {

    private final UserService userService;
    private final UserTransformer userTransformer;

    @Autowired
    public UserRestController(final UserService userService, final UserTransformer userTransformer) {
        this.userService = userService;
        this.userTransformer = userTransformer;
    }
    /*
    Works here: http://localhost:8080/springadvancedtraining/api/user/
    Example Request:
    {
        "firstName": "New",
        "lastName": "User",
        "email": "newuser@mail.com",
        "birthday": "1980-01-01",
        "roles": "ROLE_REGISTERED_USER"
    }
    */
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public User createEvent(@RequestBody UserRequest userRequest) {
        return userService.save(userTransformer.transform(userRequest));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public User updateEvent(@PathVariable Long id, @RequestBody UserRequest userRequest) {
        return userService.save(userTransformer.transform(id, userRequest));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteEvent(@PathVariable Long id) {
        userService.remove(userService.getById(id));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public User getEventById(@PathVariable Long id) {
        return userService.getById(id);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public Collection<User> getAllEvent() {
        return userService.getAll();
    }

}
