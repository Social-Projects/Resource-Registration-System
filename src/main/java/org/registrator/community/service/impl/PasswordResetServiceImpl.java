package org.registrator.community.service.impl;

import org.apache.commons.lang.RandomStringUtils;
import org.registrator.community.dao.UserRepository;
import org.registrator.community.dto.json.PasswordResetJson;
import org.registrator.community.entity.User;
import org.registrator.community.service.MailService;
import org.registrator.community.service.PasswordResetService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class PasswordResetServiceImpl implements PasswordResetService{

    @Autowired
    private Logger logger;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MailService mailService;

    @Autowired
    private PasswordEncoder userPasswordEncoder;

    @Transactional
    @Override
    public String batchPasswordReset(PasswordResetJson batch) {
        logger.info("Recieved package: " + batch);
        List<User> userList = new ArrayList<User>();

        for (String users : batch.getLogin().split(",")) {
            User tmp = userRepository.findUserByLogin(users);
            if (tmp != null) {
                userList.add(tmp);
                logger.info("found user: {" + tmp.getUserId() + ":"
                        + tmp.getLogin() + "}");
            }
        }
        if (userList.isEmpty())
            return "msg.batchops.wrongInput";

        String password;
        for (User user : userList) {
            password = RandomStringUtils.randomAlphanumeric(8);
            mailService.sendResetedPasswordMail(user.getEmail(), user.getFirstName(), user.getLogin(), password);
            user.setPassword(userPasswordEncoder.encode(password));
            userRepository.save(user);
        }

        return "msg.batchops.passwordResetSuccess";
    }

    @Transactional
    @Override
    public String passwordReset(){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findUserByLogin(auth.getName());

        if (user != null) {
            String password = RandomStringUtils.randomAlphanumeric(8);
            mailService.sendResetedPasswordMail(user.getEmail(), user.getFirstName(), user.getLogin(), password);
            user.setPassword(userPasswordEncoder.encode(password));
            userRepository.save(user);
            return "msg.batchops.passwordResetSuccess";
        }
        return "msg.batchops.wrongInput";

    }
}
