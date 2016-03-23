package org.registrator.community.service;

public interface MailService {
	
	public void sendRecoveryPasswordMail(String recepientEmail, String recepientName,String token ,String url);


}
