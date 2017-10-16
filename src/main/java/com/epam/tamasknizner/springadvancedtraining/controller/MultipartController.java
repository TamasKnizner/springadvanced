package com.epam.tamasknizner.springadvancedtraining.controller;

import com.epam.tamasknizner.springadvancedtraining.domain.Event;
import com.epam.tamasknizner.springadvancedtraining.domain.User;
import com.epam.tamasknizner.springadvancedtraining.service.EventService;
import com.epam.tamasknizner.springadvancedtraining.service.UserService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Set;

/**
 * @author Tamas_Knizner
 */
@Controller
public class MultipartController {

    private static final String UPLOAD_EVENTS_REQUEST_MAPPING = "/uploadEvents";
    private static final String UPLOAD_USERS_REQUEST_MAPPING = "/uploadUsers";
    private static final String UPLOAD_PAGE_REQUEST_MAPPING = "/upload";

    private static final String UPLOAD_MESSAGE = "uploadMessage";
    private static final String UPLOAD_MESSAGE_SUCCESS = "Upload Success!";
    private static final String UPLOAD_MESSAGE_FAILURE = "Upload Failure!";
    private static final String UPLOAD_VIEW = "upload";

    private final UserService userService;
    private final EventService eventService;

    @Autowired
    public MultipartController(final UserService userService, final EventService eventService) {
        this.userService = userService;
        this.eventService = eventService;
    }

    @RequestMapping(value = UPLOAD_EVENTS_REQUEST_MAPPING, method = RequestMethod.POST)
    public String uploadEvents(@RequestParam("file") MultipartFile multipartFile, Model model) throws IOException {
        if (!multipartFile.isEmpty()) {
            // Note for myself:  move these to a service
            ObjectMapper objectMapper = new ObjectMapper();
            Set<Event> events = objectMapper.readValue(multipartFile.getInputStream(), new TypeReference<Set<Event>>(){});
            events.forEach(eventService::save);
            addSuccessView(model);
        } else {
            addFailureView(model);
        }
        return UPLOAD_VIEW;
    }

    @RequestMapping(value = UPLOAD_USERS_REQUEST_MAPPING, method = RequestMethod.POST)
    public String uploadUsers(@RequestParam("file") MultipartFile multipartFile, Model model) throws IOException {
        if (!multipartFile.isEmpty()) {
            // Note for myself:  move these to a service
            ObjectMapper objectMapper = new ObjectMapper();
            Set<User> users = objectMapper.readValue(multipartFile.getInputStream(), new TypeReference<Set<User>>(){});
            users.forEach(userService::save);
            addSuccessView(model);
        } else {
            addFailureView(model);
        }
        return UPLOAD_VIEW;
    }


    @RequestMapping(value = UPLOAD_PAGE_REQUEST_MAPPING, method = RequestMethod.GET)
    public String upload() {
        return UPLOAD_VIEW;
    }

    private void addSuccessView(final Model model) {
        model.addAttribute(UPLOAD_MESSAGE, UPLOAD_MESSAGE_SUCCESS);
    }

    private void addFailureView(final Model model) {
        model.addAttribute(UPLOAD_MESSAGE, UPLOAD_MESSAGE_FAILURE);
    }

}
