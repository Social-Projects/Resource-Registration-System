package org.registrator.community.controller.administrator;

import org.registrator.community.components.AdminSettings;
import org.registrator.community.components.TableSettingsFactory;
import org.registrator.community.dto.UserDTO;
import org.registrator.community.dto.json.ResourceNumberJson;
import org.registrator.community.dto.json.UserStatusJson;
import org.registrator.community.dto.search.TableSearchRequestDTO;
import org.registrator.community.dto.search.TableSearchResponseDTO;
import org.registrator.community.entity.Role;
import org.registrator.community.entity.TerritorialCommunity;
import org.registrator.community.entity.User;
import org.registrator.community.enumeration.UserStatus;
import org.registrator.community.service.CommunityService;
import org.registrator.community.service.RoleService;
import org.registrator.community.service.UserService;
import org.registrator.community.service.search.BaseSearchService;
import org.registrator.community.validator.ResourceNumberJSONDTOValidator;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(value = "/administrator/")
public class SettingsController {

	@Autowired
	private Logger logger;

	@Autowired
	private AdminSettings adminSettings;


	/**
	 * Method for showing administrator settings in order to change registration
	 * method
	 * 
	 * @param model
	 * @return adminSettings.jsp
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/settings", method = RequestMethod.GET)
	public String showSettings(Model model) {
		logger.info("begin: show admin settings");
		model.addAttribute("regMethod", adminSettings.getRegistrationMethod());
		logger.info("end: admin settings are shown");
		return "adminSettings";
	}

	/**
	 * Method for changing administrator settings for one of the possible
	 * options
	 * 
	 * @param optradio
	 *            - one of three possible option for changing registration
	 *            method
	 * @return adminSettings.jsp
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/settings", method = RequestMethod.POST)
	public String changeSettings(@RequestParam String optradio) {
		logger.info("start changing settings");
		adminSettings.changeRegMethod(optradio);
		logger.info("settings are successfully changed");
		return "adminSettings";
	}

	
}