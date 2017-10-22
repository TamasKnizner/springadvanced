package com.epam.tamasknizner.springadvancedtraining;

import com.epam.tamasknizner.springadvancedtraining.service.EventService;
import com.epam.tamasknizner.springadvancedtraining.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    private final UserService userService;
    private final EventService eventService;

    @Autowired
    public IndexController(final UserService userService, final EventService eventService) {
        this.userService = userService;
        this.eventService = eventService;
    }

    @RequestMapping("/")
    public String index(final Model model) {
        model.addAttribute("user", userService.getById(1L));
        return "index";
    }

    @RequestMapping("/vardump")
    public String vardump(final Model model) {
        model.addAttribute("users", userService.getAll());
        model.addAttribute("events", eventService.getAll());
        return "vardump";
    }

}
