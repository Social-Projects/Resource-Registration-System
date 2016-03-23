package org.registrator.community.service;

import java.util.List;

import org.registrator.community.dto.UserDTO;
import org.registrator.community.entity.Role;
import org.registrator.community.entity.User;
import org.registrator.community.enumeration.UserStatus;

public interface UserService {

	public void changeUserStatus(String login, UserStatus userStatus);

	public List<UserDTO> getAllRegistratedUsers();
	
	public User getUserByLogin(String login);

	public void changeUserRole(String login, Role role);
	
	public void editUserInformation(UserDTO userDto);
	
	public List<UserStatus> fillInUserStatus();
	
	public List<UserDTO> getUserDtoList();
	
	public UserDTO getUserDto(String login);
	
	public List<UserDTO> getAllInactiveUsers();// set Role of inactive user to "USER"
}

