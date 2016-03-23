package org.registrator.community.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(
		@RequestParam(value = "error", required = false) String error) {

		ModelAndView model = new ModelAndView();
		if (error != null) {
			model.addObject("error", "Invalid username and password!");
		}

		model.setViewName("homepage");

		return model;

	}
	
		
	
	@RequestMapping(value = "/logout")
	public String logout(Model map, HttpServletRequest req) {
	 req.getSession().invalidate();
		 SecurityContextHolder.clearContext();
		
		 return "redirect:/login";
	}
	

	

	
	
	
}













//@RequestParam(value = "logout", required = false) String logout
//if (logout != null) {
//	model.addObject("msg", "You've been logged out successfully.");
//}