package org.registrator.community.service;

public interface EmailConfirmService {

	void sendConfirmEmailFirstTime(String userEmail, String baseLink);

	Boolean confirmEmail(String token);

    void sendConfirmEmailAgain(String login);

}
