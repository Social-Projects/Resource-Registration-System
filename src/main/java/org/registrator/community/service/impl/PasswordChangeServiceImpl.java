package org.registrator.community.service.impl;

import org.registrator.community.entity.User;

import org.registrator.community.service.PasswordChangeService;
import org.registrator.community.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordChangeServiceImpl implements PasswordChangeService {
    private static final Logger logger = LoggerFactory.getLogger(PasswordChangeServiceImpl.class);

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder  userPasswordEncoder;

    @Override
    public boolean changePasswordByOldPassword(String password) {
        User user = userService.getLoggedUser();
        if (user != null) {
            user.setPassword(userPasswordEncoder.encode(password));
            userService.updateUser(user);
            logger.info("Successful password change and update user with login {}", user.getLogin());
            return true;
        }
        logger.warn("Can't perform password change for user");
        return false;
    }
}
