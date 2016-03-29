package org.registrator.community.controller;

import org.registrator.community.exceptions.RegistratorException;
import org.registrator.community.exceptions.ResourceEntityNotFound;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by roman.golyuk on 22.03.2016.
 */
@ControllerAdvice
public class MainExceptionHandler {
    @Autowired
    private Logger logger;

    public static final String DEFAULT_ERROR_VIEW = "error";

    @ExceptionHandler(Exception.class)
    public ModelAndView resourceNotFoundHandler(HttpServletRequest req, RegistratorException exception) {
        logger.error("Request: " + req.getRequestURL() + " raised " + exception);
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", exception);
//        mav.addObject("message", exception.getMessageKey());
        mav.addObject("url", req.getRequestURL());
        mav.setViewName("error");
        return mav;
    }
}
