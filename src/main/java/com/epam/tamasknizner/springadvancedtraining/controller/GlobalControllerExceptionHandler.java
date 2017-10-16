package com.epam.tamasknizner.springadvancedtraining.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

/**
 * TODO.
 *
 * @author Tamas_Knizner
 */
@ControllerAdvice
public class GlobalControllerExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorInfo handleBadRequest(HttpServletRequest req, Exception ex) {
        return new ErrorInfo(req.getRequestURL(), ex);
    }

    private class ErrorInfo {
        StringBuffer requestURL;
        Exception ex;
        ErrorInfo(StringBuffer requestURL, Exception ex) {
        }
    }
}
