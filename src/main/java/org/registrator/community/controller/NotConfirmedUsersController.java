package org.registrator.community.controller;

import org.registrator.community.dto.json.UsersDataNotConfJson;
import org.registrator.community.service.NotConfirmedUsersService;
import org.registrator.community.service.VerificationTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class NotConfirmedUsersController {
    private static final Logger logger = LoggerFactory.getLogger(NotConfirmedUsersController.class);
    
    @Autowired
    private VerificationTokenService verificationTokenService;
    @Autowired
    private NotConfirmedUsersService emailConfirmService;

    @PreAuthorize("hasRole('ROLE_ANONYMOUS') or hasRole('ROLE_ADMIN') or hasRole('ROLE_COMMISSIONER')")
    @RequestMapping(value = {"/manualregistration/confirm_email/{hash}", "/register/confirm_email/{hash}"}, method = RequestMethod.GET)
    public String getConfirmEmailPage(@PathVariable("hash") String hash, Model model) {
        if (verificationTokenService.isExistValidVerificationToken(hash)) {
            model.addAttribute("msg", emailConfirmService.confirmEmail(hash));
        }
        return "confirm_email";
    }
    
    
    @PreAuthorize("hasRole('ROLE_ADMIN')or hasRole('ROLE_COMMISSIONER')")
    @RequestMapping(value = "/administrator/users/get-all-users/notcomfirmrd-user", method = RequestMethod.POST)
    public @ResponseBody String actionsWithNotConfirmedUsers(@RequestBody UsersDataNotConfJson usersDataNotConfJson) {
        logger.debug("Received JSON: {}", usersDataNotConfJson);
        return emailConfirmService.actionsWithNotConfirmedUsers(usersDataNotConfJson);
    }


}
