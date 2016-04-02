package org.registrator.community.controller;

import org.registrator.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterRestController {

    @Autowired
    UserService userService;

    @RequestMapping(value="/check-username-is-available", method = RequestMethod.GET)
    public @ResponseBody
    String checkUsernameIsAvailable(@RequestParam String login){
        if(userService.checkUsernameNotExistInDB(login)){
            return "";
        }
        return "Username is already taken. Please, use another one";
    }
}
