package org.registrator.community.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.registrator.community.dto.PasswordRecoveryDTO;
import org.registrator.community.entity.User;
import org.registrator.community.entity.VerificationToken;
import org.registrator.community.enumeration.TokenType;
import org.registrator.community.service.PasswordRecoveryService;
import org.registrator.community.service.SettingsService;
import org.registrator.community.service.UserService;
import org.registrator.community.service.VerificationTokenService;
import org.registrator.community.validator.PasswordRecoveryValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PasswordRecoveryController {

    @Autowired
    private PasswordRecoveryService passwordRecoveryService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private VerificationTokenService verificationTokenService;
    
    @Autowired
    private PasswordRecoveryValidator passwordRecoveryValidator;
    
    @Autowired
    private SettingsService settingsService;


    @PreAuthorize("hasRole('ROLE_ANONYMOUS')")
    @RequestMapping(value = "/forgot_password", method = RequestMethod.GET)
    public String getForgotPasswordPage(HttpSession session) {
        session.setAttribute("registrationMethod", settingsService.getRegistrationMethod());
        return "forgot_password";
    }
    
    @PreAuthorize("hasRole('ROLE_ANONYMOUS')")
    @RequestMapping(value = "/forgot_password", method = RequestMethod.POST)
    public String handleForgotPasswordEmail(@RequestParam("email") String email, HttpServletRequest request, Model model) {
        String baseLink = (request.getRequestURL()).toString().split("/forgot_password")[0];
        passwordRecoveryService.sendRecoverPasswordEmail(email,baseLink);
        model.addAttribute("msg",true);
        return "forgot_password";
    }   
   
    @PreAuthorize("hasRole('ROLE_ANONYMOUS')")
    @RequestMapping(value = "/password_recovery/{hash}", method = RequestMethod.GET)
    public String getPasswordRecoveryPage(@PathVariable("hash")String hash,Model model){
        if(hash==null || hash.isEmpty()){
            return "redirect:/";
        }
        if(verificationTokenService.isExistValidVerificationToken(hash)){
            model.addAttribute("hash", hash);
            VerificationToken token = verificationTokenService.findVerificationTokenByTokenAndTokenType(hash, TokenType.RECOVER_PASSWORD);
            List<User> userList = userService.findUsersByEmail(token.getUserEmail());
            List<String> logins = new ArrayList<String>();
            for(User user: userList){
                logins.add(user.getLogin());
            }
            model.addAttribute("loginList", logins);
            model.addAttribute("loginListSize", logins.size());
            if (!model.containsAttribute("passwordRecoveryDTO")) {
                model.addAttribute("passwordRecoveryDTO", new PasswordRecoveryDTO());
            }
            return "password_recovery";
        }
        return "redirect:/";
    }
    
    @RequestMapping(value = "/password_recovery", method = RequestMethod.GET)
    public String redirectToLogin(){
        return "redirect:/";
    }
    
    @PreAuthorize("hasRole('ROLE_ANONYMOUS')")
    @RequestMapping(value = "/password_recovery", method = RequestMethod.POST)
    public String handlePasswordRecoveryForm(@ModelAttribute("passwordRecoveryDTO") PasswordRecoveryDTO passwordRecoveryDTO, BindingResult bindingResult,Model model,
            RedirectAttributes attr,HttpServletRequest request){
        passwordRecoveryValidator.validate(passwordRecoveryDTO, bindingResult);
        if(bindingResult.hasErrors()){
            attr.addFlashAttribute("org.springframework.validation.BindingResult.passwordRecoveryDTO", bindingResult);
            attr.addFlashAttribute("passwordRecoveryDTO", passwordRecoveryDTO);
            return "redirect:"+request.getHeader("Referer");
        }
        boolean changePasswordResult=passwordRecoveryService.recoverPasswordByEmailLink(passwordRecoveryDTO.getHash(), passwordRecoveryDTO.getLogin(), passwordRecoveryDTO.getPassword());
        if(changePasswordResult){
            model.addAttribute("msg",true);
            return "password_recovery";
        }
        return "redirect:"+request.getHeader("Referer");
    }

}
