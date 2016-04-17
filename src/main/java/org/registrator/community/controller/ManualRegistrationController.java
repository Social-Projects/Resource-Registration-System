package org.registrator.community.controller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.registrator.community.dto.UserRegistrationDTO;
import org.registrator.community.entity.TerritorialCommunity;
import org.registrator.community.service.CommunityService;
import org.registrator.community.service.NotConfirmedUsersService;
import org.registrator.community.service.UserService;
import org.registrator.community.validator.UserDataValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ManualRegistrationController {
    private static final Logger logger = LoggerFactory.getLogger(ManualRegistrationController.class);
    
    @Autowired
    private UserService userService;
    @Autowired
    private CommunityService communityService;
    @Autowired
    private NotConfirmedUsersService emailConfirmService;
    @Autowired
    private UserDataValidator validator;

    /**
     * Method for loading form for adding new user
     * @param model
     * @param request 
     * @return register.jsp
     */
    @PreAuthorize("hasRole('ROLE_COMMISSIONER') or hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/manualregistration", method = RequestMethod.GET)
    public String showNewUserRegisterForm(Model model) {
        List<TerritorialCommunity> territorialCommunities = communityService.findAllByAsc();
        model.addAttribute("territorialCommunities", territorialCommunities);
        model.addAttribute("registrationForm", new UserRegistrationDTO());
        logger.info("Loaded registration form");
        return "regForComm";
    }
    /**
     * Method for saving new user in the database if inputed data is correct
     * @param registrationForm
     * @param result
     * @param model
     * @return register.jsp if inputed data is incorrect or redirect to page for showing and
     * editing users in another way
     */
    @PreAuthorize("hasRole('ROLE_COMMISSIONER') or hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/manualregistration", method = RequestMethod.POST)
    public String processNewUserData(@Valid UserRegistrationDTO registrationForm, BindingResult result, HttpServletRequest request, Model model) {
        validator.validate(registrationForm, result);   
        if (result.hasErrors()) {
            List<TerritorialCommunity> territorialCommunities = communityService.findAllByAsc();
            model.addAttribute("territorialCommunities", territorialCommunities);
            model.addAttribute("registrationForm", registrationForm);
            logger.warn("Registration form sent to server with following errors: \n {} "
                    + "\n Error messages displayed to admin or commissioner.", result.getFieldErrors());
            return "regForComm";
        }
        userService.registerUser(registrationForm);
        String baseLink = (request.getRequestURL()).toString().split("confirm_email")[0];
        emailConfirmService.sendConfirmEmailFirstTime(registrationForm.getLogin(), baseLink);

        logger.info("Successfully registered new commissioner/user: {}", registrationForm.getLogin());
        return "redirect:/administrator/users/get-all-users?statusType=notcomfirmed";
    }
}