package org.registrator.community.controller;


import org.registrator.community.dto.UserRegistrationDTO;
import org.registrator.community.entity.TerritorialCommunity;
import org.registrator.community.enumeration.RegistrationMethod;
import org.registrator.community.service.CommunityService;
import org.registrator.community.service.EmailConfirmService;
import org.registrator.community.service.SettingsService;
import org.registrator.community.service.UserService;
import org.registrator.community.validator.UserDataValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;


@Controller
public class RegisterController {

    private static final Logger log = LoggerFactory.getLogger(RegisterController.class);
    @Autowired
    private UserService userService;

    @Autowired
    private SettingsService settingsService;
    
    @Autowired
    private CommunityService communityService;
    
    @Autowired
    private EmailConfirmService emailConfirmService;
    
    @Autowired
    UserDataValidator validator;

    @PreAuthorize("hasRole('ROLE_ANONYMOUS')")
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String showNewUserRegisterForm(Model model, HttpServletRequest request) {
        List<TerritorialCommunity> territorialCommunities = communityService.findAllByAsc(); 
        model.addAttribute("territorialCommunities", territorialCommunities);
        model.addAttribute("registrationForm", new UserRegistrationDTO());
        log.info("Loaded 'New user registration form' " + request.getRemoteAddr());
        if (settingsService.getRegistrationMethod() == RegistrationMethod.MANUAL){
            return "redirect:/";
        }
        return "register";
    }

    @PreAuthorize("hasRole('ROLE_ANONYMOUS')")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String processNewUserData(@Valid UserRegistrationDTO registrationForm, BindingResult result, Model model, HttpServletRequest request) {
        validator.validate(registrationForm, result);
        if (result.hasErrors()) {
            List<TerritorialCommunity> territorialCommunities = communityService.findAllByAsc();
            model.addAttribute("territorialCommunities", territorialCommunities);
            model.addAttribute("registrationForm", registrationForm);
            log.warn("Registration form sent to server with following errors: \n" + result.getFieldErrors()
                    + "\n Error messages displayed to user.");
            return "register";
        }
        userService.registerUser(registrationForm);
        String baseLink = (request.getRequestURL()).toString().split("confirm_email")[0];
        emailConfirmService.sendConfirmEmailFirstTime(registrationForm.getLogin(), baseLink);


        log.info("Successfully registered new user: " + registrationForm.getLogin());
        return "thanks-for-registration";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String showLoginForm(Model model) {
        if (userService.isAuthenticated()) {
            return "redirect:/";
        }
        model.addAttribute("registrationMethod", settingsService.getRegistrationMethod());
        return "login";
    }

    @RequestMapping(value = "/logout")
    public String logout(Model map, HttpServletRequest req) {
        req.getSession().invalidate();
        SecurityContextHolder.clearContext();
        return "redirect:/login";
    }

    // Frequently Asked Questions (FAQ)
    @RequestMapping(value = "/faq", method = RequestMethod.GET)
    public String showFAQpage() {
        return "faq";
    }

    @RequestMapping(value = "/help", method = RequestMethod.GET)
    public String showContactAdminPage() {
        return "help";
    }

}
