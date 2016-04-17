package org.registrator.community.service;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.support.membermodification.MemberModifier;
import org.registrator.community.dto.json.PasswordResetJson;
import org.registrator.community.entity.Role;
import org.registrator.community.entity.User;
import org.registrator.community.enumeration.RoleType;
import org.registrator.community.service.impl.PasswordResetServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PasswordResetServiceTest {

    @InjectMocks
    private PasswordResetService passwordChangeService = new PasswordResetServiceImpl();

    @Mock
    private UserService userService;

    @Mock
    private MailService mailService;

    @Mock
    private PasswordEncoder userPasswordEncoder;

    private PasswordResetJson batch = new PasswordResetJson();
    private String login = "firstLogin";
    private User user;

    @BeforeMethod
    public void init() {
        MockitoAnnotations.initMocks(this);

        batch.setLogin("firstLogin,secondLogin,thirdLogin"); //TODO check correctness of method in case of multiply users
        user = new User(login, "password", new Role(RoleType.USER,"description"), "firstName", "lastName",
                "middleName", "email", "ACTIVE");
    }

    @Test
    public void batcnPasswordResetTestInvokesBeansWithCorrectParams() throws Exception {

        when(userService.findUserByLogin(login)).thenReturn(user);

        passwordChangeService.batchPasswordReset(batch);

        verify(userService).findUserByLogin(login);
        verify(mailService).sendResetedPasswordMail(anyString(), anyString(), anyString(), anyString());
        verify(userPasswordEncoder).encode(anyString());
        verify(userService).updateUser(user);

    }

    @Test
    public void batcnPasswordResetTestWrongInput() throws Exception {

        when(userService.findUserByLogin(login)).thenReturn(null);

        passwordChangeService.batchPasswordReset(batch);

        verify(userService).findUserByLogin(login);
        verify(mailService, never()).sendResetedPasswordMail(anyString(), anyString(), anyString(), anyString());
        verify(userPasswordEncoder, never()).encode(anyString());
        verify(userService, never()).updateUser(user);

    }

    @Test
    public void passwordResetTestInvokesBeansWithCorrectParams() throws Exception {

        when(userService.getLoggedUser()).thenReturn(user);

        passwordChangeService.passwordReset();

        verify(userService).getLoggedUser();
        verify(mailService).sendResetedPasswordMail(anyString(), anyString(), anyString(), anyString());
        verify(userPasswordEncoder).encode(anyString());
        verify(userService).updateUser(user);

    }

    @Test
    public void passwordResetTestUnauthorizedUser() throws Exception {

        when(userService.getLoggedUser()).thenReturn(null);

        passwordChangeService.passwordReset();

        verify(userService).getLoggedUser();
        verify(mailService, never()).sendResetedPasswordMail(anyString(), anyString(), anyString(), anyString());
        verify(userPasswordEncoder, never()).encode(anyString());
        verify(userService, never()).updateUser(user);

    }

}
