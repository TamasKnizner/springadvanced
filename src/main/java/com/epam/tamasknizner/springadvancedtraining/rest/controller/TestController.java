package com.epam.tamasknizner.springadvancedtraining.rest.controller;

import com.epam.tamasknizner.springadvancedtraining.domain.User;
import com.epam.tamasknizner.springadvancedtraining.rest.factory.RestTemplateFactory;
import com.epam.tamasknizner.springadvancedtraining.rest.factory.RestTemplateWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

@RestController
public class TestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestController.class);
    private static final String TEST_GET_ALL_USERS = "/test/allusers";
    private static final String TEST_GET_USER_BY_ID = "/test/userById";
//    private static final String TEST_CREATE_USER = "/test/createUser";

    private RestTemplateFactory restTemplateFactory;

    @Autowired
    public TestController(final RestTemplateFactory restTemplateFactory) {
        this.restTemplateFactory = restTemplateFactory;
    }

    @RequestMapping(value = TEST_GET_ALL_USERS, method = RequestMethod.GET)
    public void testGetAllUsers(HttpServletRequest request) {
        RestTemplateWrapper templateWrapper = createWrapper(request);
        Collection<User> users = new HashSet<>();
        ResponseEntity<? extends Collection> responseEntity = templateWrapper.getRestTemplate()
                .exchange("http://localhost:8080/springadvancedtraining/api/user", HttpMethod.GET, templateWrapper.getEntity(), users.getClass());
        LOGGER.info("{}", responseEntity.getBody());
    }

    @RequestMapping(value = TEST_GET_USER_BY_ID, method = RequestMethod.GET)
    public void testGetUserById(HttpServletRequest request) {
        RestTemplateWrapper templateWrapper = createWrapper(request);
        Map<String, String> pathVariables = new HashMap<>();
        pathVariables.put("userId", "53");
        ResponseEntity<User> responseEntity = templateWrapper.getRestTemplate()
                .exchange("http://localhost:8080/springadvancedtraining/api/user/{userId}", HttpMethod.GET, templateWrapper.getEntity(), User.class, pathVariables);
        LOGGER.info("{}", responseEntity.getBody());
    }
// I wasn't able to make this work, because it always fails with bad request.
//    @RequestMapping(value = TEST_CREATE_USER, method = RequestMethod.GET)
//    public void testCreateUser(HttpServletRequest request) {
//        UserRequest userRequest = new UserRequest();
//        userRequest.setFirstName("Test");
//        userRequest.setLastName("User");
//        userRequest.setEmail("test@mail.com");
//        userRequest.setBirthday(LocalDate.parse("1985-10-10"));
//        userRequest.setRoles("ROLE_REGISTERED_USER,ROLE_BOOKING_MANAGER");
//        RestTemplateWrapper wrapper = restTemplateFactory.createRestTemplate(request.getHeader("Cookie"), userRequest);
//        ResponseEntity<User> responseEntity = wrapper.getRestTemplate()
//                .exchange("http://localhost:8080/springadvancedtraining/api/user", HttpMethod.POST, wrapper.getEntity(), User.class);
//        LOGGER.info("{}", responseEntity.getBody());
//    }

    private RestTemplateWrapper createWrapper(final HttpServletRequest request) {
        return restTemplateFactory.createRestTemplate(request.getHeader("Cookie"));
    }

}
