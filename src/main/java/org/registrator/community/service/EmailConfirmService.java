package org.registrator.community.service;

import org.registrator.community.dto.json.UsersDataNotConfJson;

public interface EmailConfirmService {

	public void sendConfirmEmailFirstTime(String userEmail, String baseLink);

	public Boolean confirmEmail(String token);

	public String actionsWithNotConfirmedUsers(UsersDataNotConfJson usersDataNotConfJson);
	
	public String sendConfirmEmailAgain(String login);


}
