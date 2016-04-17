package org.registrator.community.service;

import java.util.List;

import org.registrator.community.dto.json.UsersDataNotConfJson;
import org.registrator.community.entity.User;

public interface NotConfirmedUsersService {

	void sendConfirmEmailFirstTime(String userEmail, String baseLink);
	
	Boolean confirmEmail(String token);
	
	String actionsWithNotConfirmedUsers(UsersDataNotConfJson usersDataNotConfJson);
	
	String sendConfirmEmailAgain(List<User> userList);
	
    String deleteNotConfirmedUsers(List<User> userList);

}
