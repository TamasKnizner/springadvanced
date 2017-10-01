package com.epam.tamasknizner.springadvancedtraining.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ErrorController {

    private static final String ERROR_VIEW_NAME = "error";

    @ExceptionHandler(Exception.class)
    public ModelAndView handleError(HttpServletRequest req, Exception ex) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("exception", ex.getMessage());
        modelAndView.addObject("url", req.getRequestURL());
        modelAndView.setViewName(ERROR_VIEW_NAME);
        return modelAndView;
    }

}
