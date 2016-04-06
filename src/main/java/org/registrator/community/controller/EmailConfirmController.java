package org.registrator.community.controller;

import org.registrator.community.dto.json.PasswordResetJson;
import org.registrator.community.dto.json.UsersDataNotConfJson;
import org.registrator.community.service.EmailConfirmService;
import org.registrator.community.service.VerificationTokenService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class EmailConfirmController {


    @Autowired
    private Logger logger;
    @Autowired
    private VerificationTokenService verificationTokenService;
    @Autowired
    EmailConfirmService emailConfirmService;

    @PreAuthorize("hasRole('ROLE_ANONYMOUS') or hasRole('ROLE_ADMIN') or hasRole('ROLE_COMMISSIONER')")
    @RequestMapping(value = {"/manualregistration/confirm_email/{hash}", "/register/confirm_email/{hash}"}, method = RequestMethod.GET)
    public String getConfirmEmailPage(@PathVariable("hash") String hash, Model model) {
        if (verificationTokenService.isExistValidVerificationToken(hash)) {
            emailConfirmService.confirmEmail(hash);
            model.addAttribute("msg", true);
        }
        return "confirm_email";
    }
    
    
    @PreAuthorize("hasRole('ROLE_ADMIN')or hasRole('ROLE_COMMISSIONER')")
    @RequestMapping(value = "/administrator/users/get-all-users/delete-notcomfirmrd-user", method = RequestMethod.POST)
    public @ResponseBody String actionsWithNotConfirmedUsers(@RequestBody UsersDataNotConfJson usersDataNotConfJson) {
        logger.info("Recieve JSON: "+ usersDataNotConfJson);
        return emailConfirmService.actionsWithNotConfirmedUsers(usersDataNotConfJson);
    }


}
