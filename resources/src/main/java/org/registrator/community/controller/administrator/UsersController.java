package org.registrator.community.controller.administrator;

import java.util.ArrayList;
import java.util.List;

import org.registrator.community.dto.UserDTO;
import org.registrator.community.entity.Role;
import org.registrator.community.enumeration.UserStatus;
import org.registrator.community.service.RoleService;
import org.registrator.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/administrator/users/")
@PreAuthorize("hasRole('ADMIN')")
public class UsersController {

	@Autowired
	UserService userService;

	@Autowired
	RoleService roleService;
	//
	// @RequestMapping(value =
	// "/get-all-users/change-status",method=RequestMethod.POST,
	// produces = MediaType.APPLICATION_JSON_VALUE, consumes =
	// MediaType.APPLICATION_JSON_VALUE)
	//
	// public String changeStatus(@ModelAttribute("login") String login) {
	// userService.changeUserStatus(login, UserStatus.ACTIVE);
	// return "allUsers";
	// }

//	@ResponseBody
//	@RequestMapping(value = "/get-all-users", method = RequestMethod.POST)
//	public String changeRole(String login, Role role) {
//		userService.changeUserRole(login, role);
//		return "allUsers";
//	}

	@RequestMapping(value = "/addressWindow", method = RequestMethod.GET)
	public String showAddressWindow(@RequestParam("login") String login, Model model) {
		UserDTO userDto = userService.getUserDto(login);
		model.addAttribute("userDto", userDto);
		return "addressWindow";
	}

	@RequestMapping(value = "/passportWindow", method = RequestMethod.GET)
	public String showPassportWindow(@RequestParam("login") String login, Model model) {
		UserDTO userDto = userService.getUserDto(login);
		model.addAttribute("userDto", userDto);
		return "passportWindow";
	}

	@RequestMapping(value = "/get-all-users", method = RequestMethod.GET)
	public String getAllUsers(Model model) {
		List<UserDTO> userDtoList = new ArrayList<UserDTO>();
		userDtoList = userService.getAllRegistratedUsers();
		model.addAttribute("userDtoList", userDtoList);
		List<Role> roleList = roleService.getAllRole();
		model.addAttribute("roleList", roleList);
		List<UserStatus> userStatusList = userService.fillInUserStatus();
		model.addAttribute("userStatusList", userStatusList);
		return "RegistratedUsers";
	}

	@RequestMapping(value = "/get-all-inactive-users", method = RequestMethod.GET)
	public String getAllInactiveUsers(Model model) {
		List<UserDTO> inactiveUsers = userService.getAllInactiveUsers();
		model.addAttribute("unregistatedUsers", inactiveUsers);
		List<UserStatus> userStatusList = userService.fillInUserStatus();
		model.addAttribute("userStatusList", userStatusList);
		List<Role> roleList = roleService.getAllRole();
		model.addAttribute("roleList", roleList);
		return "InActiveUsers";
	}

}