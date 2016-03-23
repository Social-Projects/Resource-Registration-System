package org.registrator.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainPageController {
/*
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getMainPage() {
        return "index";
    }
*/
    @RequestMapping("/")
    public String welcome(Model model){
        String message = "Welcome to Spring MVC";
        model.addAttribute("welcomeMessage", message);
        return "homepage";
    }
}