package org.registrator.community.controller.administrator;

import org.registrator.community.enumeration.ApplicationProperty;
import org.registrator.community.enumeration.RegistrationMethod;
import org.registrator.community.service.SettingsService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import java.util.TimeZone;

@Controller
@RequestMapping(value = "/administrator/")
public class SettingsController {

    @Autowired
    private Logger logger;

    @Autowired
    private SettingsService settingsService;

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
        // put all settings in Model, all properties will be available with the name of ApplicationProperty
        // ex REGISTRATION_METHOD
        model.addAttribute("REGISTRATION_METHOD", settingsService.getRegistrationMethod().toString());
        model.addAttribute("TIME_ZONE", settingsService.getTimeZone().getID());
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
     * @param timeZone
     * @return adminSettings.jsp
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/settings", method = RequestMethod.POST)
    public String changeSettings(@RequestParam String optradio, @RequestParam String timeZone) {
        logger.info("start changing settings");
        settingsService.setRegistrationMethod(RegistrationMethod.valueOf(optradio));
        settingsService.setTimeZone(TimeZone.getTimeZone(timeZone));
        logger.info("settings are successfully changed");
        return "adminSettings";
    }


}