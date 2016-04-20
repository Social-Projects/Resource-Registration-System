package org.registrator.community.service.impl;

import org.apache.commons.lang.RandomStringUtils;
import org.registrator.community.dto.json.PasswordResetJson;
import org.registrator.community.entity.User;
import org.registrator.community.service.MailService;
import org.registrator.community.service.PasswordResetService;
import org.registrator.community.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PasswordResetServiceImpl implements PasswordResetService{
    private static final Logger logger = LoggerFactory.getLogger(PasswordResetServiceImpl.class);

    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;

    @Autowired
    private PasswordEncoder userPasswordEncoder;

    @Transactional
    @Override
    public String batchPasswordReset(PasswordResetJson batch) {
        logger.info("Recieved package: " + batch.getLogin());
        List<User> userList = new ArrayList<User>();

        for (String login : batch.getLogin().split(",")) {
            User user = userService.findUserByLogin(login);
            if (user != null) {
                userList.add(user);
                logger.info("found user: [ {} : {} ]",user.getUserId(),user.getLogin());
            }
        }
        if (userList.isEmpty()) {
            logger.warn("There is no user(s) in database with such login(s) {}", batch.getLogin());
            return "msg.batchops.wrongInput";
        }
        // prepare data that would be sent asynchronously 
        List<Map<String, Object>> listTemplateVariables = new ArrayList<Map<String, Object>>(userList.size());
        String password;
        for (User user : userList) {
            password = RandomStringUtils.randomAlphanumeric(8);
            Map<String, Object> templateVariables = new HashMap<>();
            templateVariables.put("email", user.getEmail());
            templateVariables.put("name", user.getFirstName());
            templateVariables.put("login", user.getLogin());
            templateVariables.put("password", password);
            listTemplateVariables.add(templateVariables);
            // change password
            user.setPassword(userPasswordEncoder.encode(password));
            userService.updateUser(user);
            logger.info("Successful reset password and update for user with login {}", user.getLogin());
            logger.info("Data accepted and prepared in Thread: "+Thread.currentThread().getName());
        }
        // send mails asynchronously
        mailService.sendBatchResetedPasswordMail(listTemplateVariables);
        
        return "msg.batchops.passwordResetSuccess";
    }

    @Transactional
    @Override
    public String passwordReset(){

        User user = userService.getLoggedUser();

        if (user != null) {
            String password = RandomStringUtils.randomAlphanumeric(8);
            mailService.sendResetedPasswordMail(user.getEmail(), user.getFirstName(), user.getLogin(), password);
            user.setPassword(userPasswordEncoder.encode(password));
            userService.updateUser(user);
            logger.info("Successful reset password and update for user with login {}", user.getLogin());
            return "msg.batchops.passwordResetSuccess";
        }
        logger.warn("Can't perform password change for user");
        return "msg.batchops.wrongInput";
    }
}
