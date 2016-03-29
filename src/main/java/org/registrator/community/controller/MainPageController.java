package org.registrator.community.controller;

import javax.servlet.http.HttpSession;

import org.registrator.community.components.AdminSettings;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainPageController {

    @Autowired
    Logger logger;
    @Autowired
    AdminSettings adminSettings;

    /**
     * This method return homepage if credentials are correct according to the
     * method of registration and redirect to login page in other case
     * 
     * @param model
     * @param session
     * @return homepage
     */
    @RequestMapping("/")
    public String welcome(Model model, HttpSession session) {
        logger.info("begin");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (("anonymousUser").equals(auth.getName())) {
            logger.info("end: incorrect credentials");
            return "redirect:/login";
        }
        session.setAttribute("registrationMethod", adminSettings.getRegistrationMethod());
        logger.info("end: correct credentials");
        return "homepage";
    }

    @RequestMapping("/denied")
    public String denied(Model model) {
        return "accessDenied";
    }

    @RequestMapping("/home")
    public String home() {
        return "homepage";
    }

}