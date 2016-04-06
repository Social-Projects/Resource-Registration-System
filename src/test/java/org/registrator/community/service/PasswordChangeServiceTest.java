package org.registrator.community.service;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.registrator.community.dao.UserRepository;
import org.registrator.community.entity.Role;
import org.registrator.community.entity.User;
import org.registrator.community.enumeration.RoleType;
import org.registrator.community.service.impl.PasswordChangeServiceImpl;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class PasswordChangeServiceTest {

    private static final String login = "login";
    private static final String oldPassword = "oldPassword";
    private static final String newPassword = "password";

    @InjectMocks
    private PasswordChangeService passwordChangeService = new PasswordChangeServiceImpl();

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder userPasswordEncoder;

    private static User user;

    @BeforeMethod
    public void init() {

        MockitoAnnotations.initMocks(this);

        user = new User("login", "password", new Role(RoleType.USER,"description"), "firstName", "lastName",
                "middleName", "email", "ACTIVE");

        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(login, oldPassword));
    }

    @Test
    public void changePasswordByOldPasswordTestInvokesRepositoriesWithCorrectParams() throws Exception {

        when(userRepository.findUserByLogin(login)).thenReturn(user);

        passwordChangeService.changePasswordByOldPassword(newPassword);

        verify(userRepository).findUserByLogin(login);
        verify(userPasswordEncoder).encode(newPassword);
        verify(userRepository).save(user);

    }

    @Test
    public void changePasswordByOldPasswordTestDoNothingIfUserNull() throws Exception {

        user = null;
        when(userRepository.findUserByLogin(login)).thenReturn(user);

        passwordChangeService.changePasswordByOldPassword(newPassword);

        verify(userPasswordEncoder, never()).encode(newPassword);
        verify(userRepository, never()).save(user);

    }

}
