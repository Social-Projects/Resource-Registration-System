package org.registrator.community.service;

import org.apache.velocity.app.VelocityEngine;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.testng.PowerMockTestCase;
import org.registrator.community.mailer.ReloadableMailSender;
import org.registrator.community.service.impl.MailServiceImpl;

import static org.mockito.Mockito.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;


public class MailServiceTest extends PowerMockTestCase {
    
    private String recepientEmail = "recepientEmail";
    private String recepientName = "recepientName";
    private String recepientLogin = "recepientLogin";
    private String recepientPassword = "recepientPassword";
    private String token = "token";
    private String url = "url";
    private Map<String, Object> batch = new HashMap<String, Object>(){{put("email", recepientEmail);
            put("email", recepientEmail);
            put("name", recepientName);
            put("login", recepientLogin);
            put("password", recepientPassword);
        }};

    @InjectMocks
    private MailService mailService = new MailServiceImpl();

    @Mock
    private ReloadableMailSender mailSender;

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
    public void sendBatchResetedPasswordMailTestInvokesMailSender() throws Exception {
        PowerMockito.whenNew(MimeMessageHelper.class)
                .withAnyArguments()
                .thenReturn(mock(MimeMessageHelper.class, RETURNS_MOCKS));
        List<Map<String, Object>> batches = Arrays.asList(batch);
        mailService.sendBatchResetedPasswordMail(batches);
        verify(mailSender).send(anyListOf(MimeMessagePreparator.class).toArray(new MimeMessagePreparator[batches.size()]));
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