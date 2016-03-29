package org.registrator.community.controller;

import org.registrator.community.service.EmailConfirmService;
import org.registrator.community.service.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class EmailConfirmController {
	
	        
	    @Autowired
	    private VerificationTokenService verificationTokenService;
	    @Autowired
	    EmailConfirmService emailConfirmService;
	    
	    @PreAuthorize("hasRole('ROLE_ANONYMOUS')")
	    @RequestMapping(value = {"/manualregistration/confirm_email/{hash}", "/register/confirm_email/{hash}"}, method = RequestMethod.GET)
	    public String getConfirmEmailPage(@PathVariable("hash")String hash,Model model){
	    	if(verificationTokenService.isExistValidVerificationToken(hash)){
	    		emailConfirmService.confirmEmail(hash);
	    		model.addAttribute("msg",true);
	    	}
	    	return "confirm_email";
	    }
	    
	    

}
