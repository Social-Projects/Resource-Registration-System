package org.registrator.community.mailer;

import org.registrator.community.entity.SmtpParameters;
import org.registrator.community.util.Throwables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessagePreparator;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;
import java.io.InputStream;
import java.util.Properties;

/**
 * Mail sender with the ability to reload SMTP properties in atomic operation.
 *
 * This implementation is a decorator of JavaMailSender. Each time new SMTP parameters need to be applied
 * new instance of JavaMailSender is created and assigned in atomic operation.
 *
 */
public class ReloadableMailSenderImpl implements ReloadableMailSender {
    private static final Logger logger = LoggerFactory.getLogger(ReloadableMailSenderImpl.class);
    private volatile JavaMailSender mailSender;

    public ReloadableMailSenderImpl(SmtpParameters smtpParameters) {
        mailSender = createMailSender(smtpParameters);
    }

    @Override
    synchronized public void applyNewParameters(SmtpParameters smtpParameters) {
        logger.debug("Applying new SMTP parameters");
        mailSender = createMailSender(smtpParameters);
    }

    @Override
    public MimeMessage createMimeMessage() {
        return mailSender.createMimeMessage();
    }

    @Override
    public MimeMessage createMimeMessage(InputStream contentStream) throws MailException {
        return mailSender.createMimeMessage(contentStream);
    }

    @Override
    public void send(MimeMessage mimeMessage) throws MailException {
        mailSender.send(mimeMessage);
    }

    @Override
    public void send(MimeMessage... mimeMessages) throws MailException {
        mailSender.send(mimeMessages);
    }

    @Override
    public void send(MimeMessagePreparator mimeMessagePreparator) throws MailException {
        logger.debug("Applying new SMTP parameters");
        mailSender.send(mimeMessagePreparator);
    }

    @Override
    public void send(MimeMessagePreparator... mimeMessagePreparators) throws MailException {
        mailSender.send(mimeMessagePreparators);
    }

    @Override
    public void send(SimpleMailMessage simpleMessage) throws MailException {
        mailSender.send(simpleMessage);
    }

    @Override
    public void send(SimpleMailMessage... simpleMessages) throws MailException {
        mailSender.send(simpleMessages);
    }

    @Override
    public boolean testConnection(SmtpParameters parameters) {
        JavaMailSenderImpl sender = createMailSender(parameters);

        Session session = sender.getSession();

        boolean success;
        Transport transport = null;
        try {

            String protocol = parameters.getProtocolString();
            transport = session.getTransport(protocol);

            transport.connect(parameters.getHost(), parameters.getPort(),
                    parameters.getUsername(), parameters.getPassword());
            success = true;
        } catch (MessagingException ex) {
            logger.warn("Couldn't connect to SMTP {}:{} with username {}, message: {}, root cause: {} ",
                    parameters.getHost(), parameters.getPort(), parameters.getUsername(),
                    ex.getMessage(), Throwables.getRootCause(ex).getMessage());
            success = false;
        } finally {
            if (transport != null) {
                try {
                    transport.close();
                } catch (MessagingException e) {
                    logger.error("Failed to close SMTP transport connection!", e);
                }
            }
        }

        return success;
    }


    private JavaMailSenderImpl createMailSender(SmtpParameters smtpParameters) {
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setDefaultEncoding("UTF-8");
        sender.setHost(smtpParameters.getHost());
        String protocol = smtpParameters.getTlsEnabled() ? "smtps" : "smtp";
        sender.setProtocol(protocol);
        sender.setPort(smtpParameters.getPort());
        sender.setUsername(smtpParameters.getUsername());
        sender.setPassword(smtpParameters.getPassword());

        Properties javaMailProperties = new Properties();
        javaMailProperties.setProperty("mail.transport.protocol", protocol);
        javaMailProperties.setProperty("mail.smtp.auth", "true");
        javaMailProperties.setProperty("mail.smtp.starttls.enable", smtpParameters.getTlsEnabled() ? "true" : "false");
        javaMailProperties.setProperty("mail.smtp.socketFactory.fallback", "true");
        sender.setJavaMailProperties(javaMailProperties);

        return sender;
    }

}
