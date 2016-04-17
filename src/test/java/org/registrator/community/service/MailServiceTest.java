package org.registrator.community.service;

import org.apache.velocity.app.VelocityEngine;
import org.mockito.Spy;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.registrator.community.service.impl.MailServiceImpl;
import org.slf4j.Logger;

import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;


public class MailServiceTest extends PowerMockTestCase {
    
    private String recepientEmail = "recepientEmail";
    private String recepientName = "recepientName";
    private String recepientLogin = "recepientLogin";
    private String token = "token";
    private String url = "url";

    @InjectMocks
    private MailService mailService = new MailServiceImpl();

    @Mock
    private JavaMailSender mailSender;

    @Mock
    private VelocityEngine velocityEngine;

    @BeforeMethod
    public void beforeMethod() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void sendRecoveryPasswordMailTestInvokesMailSender() throws Exception {
        PowerMockito.whenNew(MimeMessageHelper.class)
                .withAnyArguments()
                .thenReturn(mock(MimeMessageHelper.class, RETURNS_MOCKS));
        mailService.sendRecoveryPasswordMail(recepientEmail, token, url);
        verify(mailSender).send(any(MimeMessagePreparator.class));
    }

    @Test
    public void sendResetedPasswordMailTestInvokesMailSender() throws Exception {
        PowerMockito.whenNew(MimeMessageHelper.class)
                .withAnyArguments()
                .thenReturn(mock(MimeMessageHelper.class, RETURNS_MOCKS));
        mailService.sendResetedPasswordMail(recepientEmail, recepientName, token, url);
        verify(mailSender).send(any(MimeMessagePreparator.class));
    }

    @Test
    public void sendComfirmEMailTestInvokesMailSender() throws Exception {
        PowerMockito.whenNew(MimeMessageHelper.class)
                .withAnyArguments()
                .thenReturn(mock(MimeMessageHelper.class, RETURNS_MOCKS));
        mailService.sendComfirmEMail(recepientEmail, recepientName, recepientLogin, token, url);
        verify(mailSender).send(any(MimeMessagePreparator.class));
    }

}