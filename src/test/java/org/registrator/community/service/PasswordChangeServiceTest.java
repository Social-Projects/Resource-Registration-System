package org.registrator.community.service;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.support.membermodification.MemberModifier;
import org.registrator.community.dao.UserRepository;
import org.registrator.community.entity.Role;
import org.registrator.community.entity.User;
import org.registrator.community.enumeration.RoleType;
import org.registrator.community.service.impl.MailServiceImpl;
import org.registrator.community.service.impl.PasswordChangeServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class PasswordChangeServiceTest {
    private static final Logger logger = LoggerFactory.getLogger(PasswordChangeServiceTest.class);
    
    @InjectMocks
    private PasswordChangeService passwordChangeService = new PasswordChangeServiceImpl();

    @Mock
    private UserService userService;

    @Mock
    private PasswordEncoder userPasswordEncoder;

    private static User user;
    private static final String login = "login";
    private static final String oldPassword = "oldPassword";
    private static final String newPassword = "password";

    @BeforeMethod
    public void init() {
        MockitoAnnotations.initMocks(this);

        user = new User(login, oldPassword, new Role(RoleType.USER,"description"), "firstName", "lastName",
                "middleName", "email", "ACTIVE");
    }

    @Test
    public void changePasswordByOldPasswordTestInvokesRepositoriesWithCorrectParams() throws Exception {

        when(userService.getLoggedUser()).thenReturn(user);

        passwordChangeService.changePasswordByOldPassword(newPassword);

        verify(userService).getLoggedUser();
        verify(userPasswordEncoder).encode(newPassword);
        verify(userService).updateUser(user);

    }

    @Test
    public void changePasswordByOldPasswordTestDoNothingIfUserNull() throws Exception {

        user = null;
        when(userService.getLoggedUser()).thenReturn(user);

        passwordChangeService.changePasswordByOldPassword(newPassword);

        verify(userPasswordEncoder, never()).encode(newPassword);
        verify(userService, never()).updateUser(user);

    }

}
