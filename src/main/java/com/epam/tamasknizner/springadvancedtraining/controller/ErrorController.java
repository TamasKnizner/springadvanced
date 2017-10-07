package com.epam.tamasknizner.springadvancedtraining.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

//@Controller
public class ErrorController {

    private static final String ERROR_VIEW_NAME = "error";

    //@ExceptionHandler(Exception.class)
    public ModelAndView handleError(Exception ex) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("exception", ex.getMessage());
        modelAndView.setViewName(ERROR_VIEW_NAME);
        return modelAndView;
    }

}
