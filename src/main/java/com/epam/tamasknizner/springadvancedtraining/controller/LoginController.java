package com.epam.tamasknizner.springadvancedtraining.controller;

import com.epam.tamasknizner.springadvancedtraining.domain.request.LoginRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    private static final String LOGIN_MAPPING = "/login";
    private static final String LOGIN_VIEW_NAME = "login";

    @RequestMapping(value = LOGIN_MAPPING, method = RequestMethod.GET)
    public String login() {
        return LOGIN_VIEW_NAME;
    }

    @RequestMapping(value = LOGIN_MAPPING, method = RequestMethod.POST)
    public String doLogin(@ModelAttribute LoginRequest loginRequest) {
        LOGGER.info("{}", loginRequest);
        return "redirect:/";
    }
}
