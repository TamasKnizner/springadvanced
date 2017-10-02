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

    private static final String UPLOAD_VIEW = "upload";

    @Autowired
    private UserService userService;

    @Autowired
    private EventService eventService;

    @RequestMapping(value = "/uploadEvents", method = RequestMethod.POST)
    public String uploadEvents(@RequestParam("file") MultipartFile multipartFile, Model model) throws IOException {
        if (!multipartFile.isEmpty()) {
            ObjectMapper objectMapper = new ObjectMapper();
            Set<Event> events = objectMapper.readValue(multipartFile.getInputStream(), new TypeReference<Set<Event>>(){});
            events.forEach(eventService::save);
            model.addAttribute("uploadMessage", "Upload Success!");
        } else {
            model.addAttribute("uploadMessage", "Upload Failure!");
        }
        return UPLOAD_VIEW;
    }

    @RequestMapping(value = "/uploadUsers", method = RequestMethod.POST)
    public String uploadUsers(@RequestParam("file") MultipartFile multipartFile, Model model) throws IOException {
        if (!multipartFile.isEmpty()) {
            ObjectMapper objectMapper = new ObjectMapper();
            Set<User> users = objectMapper.readValue(multipartFile.getInputStream(), new TypeReference<Set<User>>(){});
            users.forEach(userService::save);
            model.addAttribute("uploadMessage", "Upload Success!");
        } else {
            model.addAttribute("uploadMessage", "Upload Failure!");
        }
        return UPLOAD_VIEW;
    }

    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    public String upload() {
        return UPLOAD_VIEW;
    }

}
