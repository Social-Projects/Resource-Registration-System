package org.registrator.community.controller;

import org.registrator.community.dto.PasswordChangeDTO;
import org.registrator.community.service.PasswordChangeService;
import org.registrator.community.validator.PasswordChangeValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class PasswordChangeController {

    @Autowired
    private PasswordChangeService passwordChangeService;

    @Autowired
    private PasswordChangeValidator passwordChangeValidator;


    @RequestMapping(value = "/change_password", method = RequestMethod.GET)
    public String getChangePasswordPage() {
        return "change_password";
    }

    @RequestMapping(params = "cancel", value = "/change_password", method = RequestMethod.POST)
    public String cancelChangePassword() {
        return "redirect:/";
    }

    @RequestMapping(params = "update", value = "/change_password", method = RequestMethod.POST)
    public String handleChangePassword(@ModelAttribute("passwordChangeDTO") @Valid PasswordChangeDTO passwordChangeDTO,
                                       BindingResult bindingResult, Model model) {

        if (!model.containsAttribute("passwordChangeDTO")) {
            model.addAttribute("passwordChangeDTO", new PasswordChangeDTO());
        }

        passwordChangeValidator.validate(passwordChangeDTO, bindingResult);
        if(bindingResult.hasErrors()){
            model.addAttribute("passwordChangeDTO", passwordChangeDTO);
            return "change_password";
        }

        boolean changePasswordResult = passwordChangeService.changePasswordByOldPassword(passwordChangeDTO.getNewPassword());
        if(changePasswordResult){
            model.addAttribute("msg",true);
        }
        return "change_password";
    }

}
