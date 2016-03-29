package org.registrator.community.controller.administrator;

import java.util.List;

import javax.validation.Valid;

import org.registrator.community.components.AdminSettings;
import org.registrator.community.components.TableSettingsFactory;
import org.registrator.community.dto.UserDTO;
import org.registrator.community.dto.json.CommunityParamJson;
import org.registrator.community.dto.json.ResourceNumberJson;
import org.registrator.community.dto.json.RoleTypeJson;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/administrator/users/")
public class UsersController {

    @Autowired
    private Logger logger;

    @Autowired
    private CommunityService communityService;

    @Autowired
    private UserService userService;

    @Autowired
    private AdminSettings adminSettings;

    @Autowired
    private RoleService roleService;

    @Autowired
    @Qualifier("registerUserSearchService")
    private BaseSearchService<User> userSearchService;

    @Autowired
    private TableSettingsFactory tableSettingsFactory;

    @Autowired
    ResourceNumberJSONDTOValidator resourceNumberValidator;

    /**
     * Controller for showing information about user
     *
     */
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_COMMISSIONER')")
    @RequestMapping(value = "/edit-registrated-user", method = RequestMethod.GET)
    public String fillInEditWindow(@RequestParam("login") String login, Model model) {
        logger.info("begin");
        UserDTO userDto = userService.getUserDto(login);
        model.addAttribute("userDto", userDto);
        List<Role> roleList = roleService.getAllRole();
        model.addAttribute("roleList", roleList);
        List<UserStatus> userStatusList = userService.fillInUserStatusforRegistratedUsers();
        model.addAttribute("userStatusList", userStatusList);
        List<TerritorialCommunity> territorialCommunities = communityService.findAll();
        model.addAttribute("territorialCommunities", territorialCommunities);
        logger.info("end");
        return "editWindow";
    }

    /**
     * Controller for editing user information
     *
     */

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_COMMISSIONER')")
    @RequestMapping(value = "/edit-registrated-user", method = RequestMethod.POST)
    public String editRegistratedUser(@Valid @ModelAttribute("userDTO") UserDTO userDto, BindingResult result,
            Model model, RedirectAttributes redirectAttributes) {
            ResourceNumberJson resNumJson = userDto.getResourceNumberJson();
            if(resNumJson != null){
                resourceNumberValidator.validate(resNumJson, result);
            }

        if (result.hasErrors()) {
            return fillInEditWindow(userDto.getLogin(), model);
        } else {
            logger.info("begin");
            userService.createTomeAndRecourceNumber(userDto);
            UserDTO editUserDto = userService.editUserInformation(userDto);
            model.addAttribute("userDto", editUserDto);
            logger.info("end");
            redirectAttributes.addFlashAttribute("tableSetting", tableSettingsFactory.getTableSetting("registerUser"));
            return "redirect:/administrator/users/get-all-users";
        }
    }

    /**
     * Controller for showing all inactive user
     *
     */
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_COMMISSIONER')")
    @RequestMapping(value = "/get-all-inactive-users", method = RequestMethod.GET)
    public String getAllInactiveUsers(Model model) {
        logger.info("begin");
        List<UserDTO> inactiveUsers = userService.getAllInactiveUsers();
        model.addAttribute("unregistatedUsers", inactiveUsers);
        List<UserStatus> userStatusList = userService.fillInUserStatusforInactiveUsers();
        model.addAttribute("userStatusList", userStatusList);
        List<Role> roleList = roleService.getAllRole();
        model.addAttribute("roleList", roleList);
        logger.info("end");
        return "InActiveUsers";
    }

    /**
     * Controller for changing user statur for inactive users
     *
     */
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_COMMISSIONER')")
    @ResponseBody
    @RequestMapping(value = "/get-all-inactive-users", method = RequestMethod.POST)
    public String changeStatus(@RequestBody UserStatusJson userStatusDto) {
        logger.info("begin");
        userService.changeUserStatus(userStatusDto);
        logger.info("end");
        return "InActiveUsers";
    }

    /**
     * Controller for showing modal window
     *
     */
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_COMMISSIONER')")
    @ResponseBody
    @RequestMapping(value = "/edit-registrated-user/modal-window", method = RequestMethod.POST)
    public ResponseEntity<String> showModalWindow(@Valid @RequestBody ResourceNumberJson resourceNumberDtoJson,
            BindingResult result) {
        logger.info("begin");
        resourceNumberValidator.validate(resourceNumberDtoJson, result);
        if (result.hasErrors()) {
            return new ResponseEntity<String>(HttpStatus.CONFLICT);
        } else {
            logger.info("end");
            return new ResponseEntity<String>(HttpStatus.OK);
        }
    }

    /**
     * Controller for get all registrated users
     * 
     */

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_COMMISSIONER')")
    @RequestMapping(value = "/get-all-users", method = RequestMethod.GET)
    public String getAllUsers(Model model) {
        logger.info("begin");
        model.addAttribute("tableSetting", tableSettingsFactory.getTableSetting("registerUser"));
        List<UserDTO> userDtoList = userService.getUserDtoList();
        model.addAttribute("userList", userDtoList);
        List<Role> roleTypes = roleService.getAllRole();
        model.addAttribute("roleTypes", roleTypes);
        logger.info("end");
        return "searchTableTemplate";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_COMMISSIONER')")
    @ResponseBody
    @RequestMapping(value = "registerUser", method = RequestMethod.POST)
    public TableSearchResponseDTO getDataFromDataTable(@Valid @RequestBody TableSearchRequestDTO dataTableRequest) {
        TableSearchResponseDTO dto = userSearchService.executeSearchRequest(dataTableRequest);
        return dto;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_COMMISSIONER')")
    @RequestMapping(value = "batch-role-change", method = RequestMethod.POST)
    public @ResponseBody String setRoleForUsers(@RequestBody RoleTypeJson roleTypeJson) {
        String msg = userService.batchRoleChange(roleTypeJson);

        return msg;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_COMMISSIONER')")
    @RequestMapping(value = "batch-community-change", method = RequestMethod.POST)
    public @ResponseBody String setCommunityForUsers(@RequestBody CommunityParamJson communityParamJson) {
        String msg = userService.batchCommunityChange(communityParamJson);

        return msg;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_COMMISSIONER')")
    @ResponseBody
    @RequestMapping(value = "communities", method = RequestMethod.POST)
    public List<TerritorialCommunity> getCommunityList(@RequestParam("communityDesc") String communityDesc) {
        List<TerritorialCommunity> territorialCommunities = communityService.getCommunityBySearchTag(communityDesc);
        return territorialCommunities;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_COMMISSIONER')")
    @ResponseBody
    @RequestMapping(value = "get-community", method = RequestMethod.POST)
    public TerritorialCommunity getCommunity(@RequestBody String communityName) {
        TerritorialCommunity territorialCommunity = communityService.findByName(communityName);
        return territorialCommunity;
    }

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
     * @param optratio
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

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping("/unlockusers")
    public String home() {
        userService.resetAllFailAttempts();
        logger.info("All users atempts are reseted");
        return "homepage";
    }

}