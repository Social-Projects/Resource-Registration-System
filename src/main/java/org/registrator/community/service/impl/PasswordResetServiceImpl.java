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
import java.util.List;

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
        logger.info("Recieved package: " + batch);
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

        String password;
        for (User user : userList) {
            password = RandomStringUtils.randomAlphanumeric(8);
            mailService.sendResetedPasswordMail(user.getEmail(), user.getFirstName(), user.getLogin(), password);
            user.setPassword(userPasswordEncoder.encode(password));
            userService.updateUser(user);
            logger.info("Successful reset password and update for user with login {}", user.getLogin());
        }
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
