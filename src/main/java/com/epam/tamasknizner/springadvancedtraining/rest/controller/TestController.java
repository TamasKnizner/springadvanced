package com.epam.tamasknizner.springadvancedtraining.rest.controller;

import com.epam.tamasknizner.springadvancedtraining.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

@RestController
public class TestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestController.class);
    private static final String TEST_GET_ALL_USERS = "/test/allusers";

    @RequestMapping(value = TEST_GET_ALL_USERS, method = RequestMethod.GET)
    public void testGetAllUsers() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setMessageConverters(Collections.singletonList(new MappingJackson2HttpMessageConverter()));
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
        Collection<User> users = new HashSet<>();
        ResponseEntity<? extends Collection> responseEntity = restTemplate.exchange("http://localhost:8080/springadvancedtraining/api/user", HttpMethod.GET, entity, users.getClass());
        responseEntity.getBody().forEach(user -> LOGGER.info(user.toString()));
    }

}
