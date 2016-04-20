package org.registrator.community.service;

import java.util.List;
import java.util.Map;

import org.registrator.community.entity.SmtpParameters;

public interface MailService {
	
	void sendComfirmEMail(String recepientEmail, String recepientName, String login, String token, String url);
	
	void sendRecoveryPasswordMail(String recepientEmail, String token ,String url);

	void sendResetedPasswordMail(String recepientEmail, String recepientName, String login, String password);
	
	void sendBatchResetedPasswordMail(List<Map<String, Object>> listTemplateVariables);

    boolean testConnection(SmtpParameters parameters);

	void applyNewParameters(SmtpParameters smtpParameters);
}
