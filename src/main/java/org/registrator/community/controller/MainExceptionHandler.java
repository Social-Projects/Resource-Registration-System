package org.registrator.community.controller;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Exception handler for application
 */
@ControllerAdvice
public class MainExceptionHandler {
    @Autowired
    private Logger logger;

    public static final String DEFAULT_ERROR_VIEW = "error";

//    @ExceptionHandler(Exception.class)
    public ModelAndView resourceNotFoundHandler(HttpServletRequest req, Exception exception) {
        logger.error("Request: " + req.getRequestURL() + " raised " + exception.getMessage());
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", exception);
        mav.addObject("url", req.getRequestURL());
        mav.setViewName(DEFAULT_ERROR_VIEW);
        return mav;
    }
}
