package org.registrator.community.service.impl;

import org.registrator.community.dao.UserRepository;
import org.registrator.community.entity.User;

import org.registrator.community.service.PasswordChangeService;
import org.registrator.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordChangeServiceImpl implements PasswordChangeService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder  userPasswordEncoder;

    @Override
    public boolean changePasswordByOldPassword(String password) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByLogin(auth.getName());
        if (user != null) {
            user.setPassword(userPasswordEncoder.encode(password));
            userRepository.save(user);
            return true;
        }
        return false;
    }
}
