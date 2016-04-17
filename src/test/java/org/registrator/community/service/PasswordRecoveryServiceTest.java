package org.registrator.community.service;


import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.whenNew;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertFalse;

import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.registrator.community.dao.UserRepository;
import org.registrator.community.entity.Role;
import org.registrator.community.entity.User;
import org.registrator.community.entity.VerificationToken;
import org.registrator.community.enumeration.RoleType;
import org.registrator.community.enumeration.TokenType;
import org.registrator.community.service.impl.PasswordRecoveryServiceImpl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;

import java.util.Arrays;
import java.util.Date;

public class PasswordRecoveryServiceTest extends PowerMockTestCase {

    @InjectMocks
    private PasswordRecoveryService passwordRecoveryService = new PasswordRecoveryServiceImpl();
    @Mock
    private UserRepository userRepository;
    @Mock
    private MailService mailService;
    @Mock
    private VerificationTokenService verificationTokenService;
    @Mock
    private PasswordEncoder userPasswordEncoder;

    private User user;
    private VerificationToken verificationToken;
    private String userEmail = "userEmail";
    private String userLogin = "login";
    private Date expiryDate = new Date();
    private String VALID_TOKEN = "0001";
    private String NON_VALID_TOKEN = "0002";
    private String NEW_PASSWORD = "newPassword";
    private String baseLink = "baseLink";

    @BeforeMethod
    public void beforeMethod() {
        MockitoAnnotations.initMocks(this);

        user = new User("login", "password", new Role(RoleType.USER, "description"), "firstName", "lastName",
                "middleName", "email", "ACTIVE");

        verificationToken = new VerificationToken(VALID_TOKEN, userEmail, expiryDate);
    }

    @Test
    public void recoverPasswordByEmailLinkByValidToken() throws Exception {
        when(verificationTokenService.isExistValidVerificationToken(VALID_TOKEN)).thenReturn(true);
        when(verificationTokenService.findVerificationTokenByTokenAndTokenType(VALID_TOKEN, TokenType.RECOVER_PASSWORD))
                .thenReturn(verificationToken);
        when(userRepository.findUserByLogin(userLogin)).thenReturn(user);
        when(userPasswordEncoder.encode(NEW_PASSWORD)).thenReturn(anyString());

        passwordRecoveryService.recoverPasswordByEmailLink(VALID_TOKEN, userLogin, NEW_PASSWORD);

        verify(userPasswordEncoder).encode(NEW_PASSWORD);
        verify(userRepository).save(user);
        verify(verificationTokenService).deleteVerificationToken(verificationToken);
    }

    @Test
    public void recoverPasswordByEmailLinkByNonValidToken() throws Exception {
        when(verificationTokenService.isExistValidVerificationToken(NON_VALID_TOKEN)).thenReturn(false);
        Boolean actualResult = passwordRecoveryService.recoverPasswordByEmailLink(NON_VALID_TOKEN, userLogin, NEW_PASSWORD);
        assertFalse(actualResult, "Recover password by non valid token shoud return false");
    }

    @Test
    public void recoverPasswordByEmailLinkByTokenNull() throws Exception {
        when(verificationTokenService.isExistValidVerificationToken(null)).thenReturn(false);
        Boolean actualResult = passwordRecoveryService.recoverPasswordByEmailLink(null, userLogin, NEW_PASSWORD);
        assertFalse(actualResult == null, "Recover password by null user return false");
    }

    @Test
    public void recoverPasswordByEmailLinkByNullUser() throws Exception {
        user = null;
        Boolean actualResult = passwordRecoveryService.recoverPasswordByEmailLink(null, userLogin, NEW_PASSWORD);
        assertFalse(actualResult, "Recover password by null user shoud return false");
    }

    @Test
    public void sendRecoverPasswordEmailTestWithCorrectArguments() throws Exception {
        whenNew(Date.class).withNoArguments().thenReturn(expiryDate);
        when(userRepository.getUsersByEmail(userEmail)).thenReturn(Arrays.asList(user));
        when(verificationTokenService.savePasswordVerificationToken(any(), any())).thenReturn(verificationToken);

        passwordRecoveryService.sendRecoverPasswordEmail(userEmail, baseLink);

        verify(mailService).sendRecoveryPasswordMail(userEmail, VALID_TOKEN, baseLink);
    }

    @Test
    public void sendRecoverPasswordEmailTestDoNothingIfUserNull() throws Exception {
        when(userRepository.getUsersByEmail(userEmail)).thenReturn(null);

        passwordRecoveryService.sendRecoverPasswordEmail(userEmail, baseLink);

        verify(verificationTokenService, never()).savePasswordVerificationToken(anyString(),anyString(), any(Date.class));
        verify(mailService, never()).sendRecoveryPasswordMail(anyString(), anyString(), anyString());
    }


}