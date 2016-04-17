package org.registrator.community.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.registrator.community.service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import static org.springframework.ui.velocity.VelocityEngineUtils.mergeTemplateIntoString;

@Service
public class MailServiceImpl implements MailService{
    private static final Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);
	
	public static final String SEND_FROM = "resources.registrator@gmail.com";
	
	public static final String CONFIRM_EMAIL_LETTER_PATH =  "/velocity/confirmEmail.vm";
	public static final String RECOVER_PASSWORD_LETTER_PATH =  "/velocity/recoverPassword.vm";
		
	public static final String CONFIRM_EMAIL_SUBJECT =  "Заявка на реєстрацію";
	public static final String RECOVER_PASSWORD_SUBJECT =  "Заявка на відновлення паролю";

    public static final String RESET_PASSWORD_LETTER_PATH =  "/velocity/resetPassword.vm";
    public static final String RESET_PASSWORD_SUBJECT =  "Ваш пароль скинуто адміном";
	
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
    private VelocityEngine velocityEngine;

	
	@Override
	@Async
	public void sendComfirmEMail(String recepientEmail, String recepientName, String login, String token, String url) {
	    logger.info("Method sendComfirmEMail run it Thread: {}", Thread.currentThread().getName());
	    Map<String, Object> templateVariables = new HashMap<>();
	    templateVariables.put("name", recepientName);
        templateVariables.put("login", login);
        templateVariables.put("url", url);
        templateVariables.put("token", token);
        MimeMessagePreparator preparator = prepareMail(recepientEmail, templateVariables, CONFIRM_EMAIL_LETTER_PATH, CONFIRM_EMAIL_SUBJECT);
        try {
            mailSender.send(preparator);
        } catch (MailException e) {
            logger.error("Send mail exception to {}", recepientEmail);
        }
        
	}

	@Override
	@Async
	public void sendRecoveryPasswordMail(String recepientEmail, String token, String url) {
	    logger.info("Method sendRecoveryPasswordMail run it Thread: {}", Thread.currentThread().getName());
	    Map<String, Object> templateVariables = new HashMap<>();
        templateVariables.put("url", url);
        templateVariables.put("token", token);
	    MimeMessagePreparator preparator = prepareMail(recepientEmail, templateVariables, RECOVER_PASSWORD_LETTER_PATH, RECOVER_PASSWORD_SUBJECT);
        try {
            mailSender.send(preparator);
        } catch (MailException e) {
            logger.error("Send mail exception to {}", recepientEmail);
        }

	}

    @Override
    @Async
    public void sendResetedPasswordMail(String recepientEmail, String recepientName, String login, String password){
        logger.info("Method sendResetedPasswordMail run it Thread: {}", Thread.currentThread().getName());
        Map<String, Object> templateVariables = new HashMap<>();
        templateVariables.put("name", recepientName);
        templateVariables.put("login", login);
        templateVariables.put("password", password);
        MimeMessagePreparator preparator = prepareMail(recepientEmail, templateVariables, RESET_PASSWORD_LETTER_PATH, RESET_PASSWORD_SUBJECT);
        try {
            mailSender.send(preparator);
        } catch (MailException e) {
            logger.error("Send mail exception to {}", recepientEmail);
        }
        
    }
    
    private MimeMessagePreparator prepareMail(String recepientEmail,Map<String, Object> templateVariables, String templatePath, String subject){
        MimeMessagePreparator preparator = new MimeMessagePreparator() {
            public void prepare(MimeMessage mimeMessage) throws MessagingException, UnsupportedEncodingException {
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
                message.setTo(recepientEmail);
                message.setFrom(new InternetAddress("resources.registrator@gmail.com", "Registrator system"));
                String body = mergeTemplateIntoString(velocityEngine, templatePath, "UTF-8", templateVariables);
                message.setText(body, true);
                message.setSubject(subject);
            }
        };
        return preparator; 
    }

}
