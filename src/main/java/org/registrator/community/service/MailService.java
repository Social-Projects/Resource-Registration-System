package org.registrator.community.service;

public interface MailService {
	
	void sendComfirmEMail(String recepientEmail, String recepientName, String token, String url);
	
	public void sendRecoveryPasswordMail(String recepientEmail, String recepientName,String token ,String url);

	


}
