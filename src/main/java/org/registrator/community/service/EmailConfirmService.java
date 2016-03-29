package org.registrator.community.service;

public interface EmailConfirmService {

	void sendConfirmEmail(String userEmail, String baseLink);

	Boolean confirmEmail(String token);

}
