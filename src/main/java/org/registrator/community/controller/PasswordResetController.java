package org.registrator.community.controller;

import org.registrator.community.dto.json.PasswordResetJson;
import org.registrator.community.service.PasswordResetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/administrator/users/")
public class PasswordResetController {

    @Autowired
    private PasswordResetService passwordResetService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/get-all-users/batch-password-reset", method = RequestMethod.POST)
    public @ResponseBody String resetPasswordForUsers(@RequestBody PasswordResetJson passwordResetJson) {
        String msg = passwordResetService.batchPasswordReset(passwordResetJson);

        return msg;
    }
}
