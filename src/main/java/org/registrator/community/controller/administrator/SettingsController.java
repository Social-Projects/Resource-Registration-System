package org.registrator.community.controller.administrator;

import org.registrator.community.enumeration.RegistrationMethod;
import org.registrator.community.service.SettingsService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.TimeZone;

@Controller
@RequestMapping(value = "/administrator/")
public class SettingsController {

    @Autowired
    private Logger logger;

    @Autowired
    private SettingsService settingsService;

    private static final Validator validator = new TimeZoneValidator();

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
        model.addAttribute("settings",
                new SettingsDTO(settingsService.getRegistrationMethod().toString(),
                    settingsService.getTimeZone().getID()));
        logger.info("end: admin settings are shown");
        return "adminSettings";
    }

    /**
     * Method for changing administrator settings for one of the possible
     * options
     *
     * @return adminSettings.jsp
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/settings", method = RequestMethod.POST)
    public String changeSettings(@Valid @ModelAttribute SettingsDTO settings,
                                 BindingResult result,
                                 Model model) {
        logger.debug("start changing settings");

        model.addAttribute("settings", settings);
        validator.validate(settings, result);
        if (result.hasErrors()) {
            settings.setError(true);
            logger.info("Settings have errors {}", result);
            return "adminSettings";
        }
        logger.info("Settings don't have errors {}", result);

        settingsService.setRegistrationMethod(RegistrationMethod.valueOf(settings.getRegistrationMethod()));
        settingsService.setTimeZone(TimeZone.getTimeZone(settings.getTimeZone()));
        logger.info("settings are successfully changed");
        settings.setSuccess(true);
        model.addAttribute("settings", settings);
        return "adminSettings";
    }

    public static class SettingsDTO {
        private String registrationMethod;
        private String timeZone;
        private boolean success;
        private boolean error;

        public SettingsDTO() {}

        public SettingsDTO(String registrationMethod, String timeZone) {
            this.registrationMethod = registrationMethod;
            this.timeZone = timeZone;
        }

        public String getRegistrationMethod() {
            return registrationMethod;
        }

        public void setRegistrationMethod(String registrationMethod) {
            this.registrationMethod = registrationMethod;
        }

        public String getTimeZone() {
            return timeZone;
        }

        public void setTimeZone(String timeZone) {
            this.timeZone = timeZone;
        }

        @Override
        public String toString() {
            return String.format("[registration method: %s; time zone: %s", registrationMethod, timeZone);
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public void setError(boolean error) {
            this.error = error;
        }

        public boolean getSuccess() {
            return success;
        }

        public boolean getError() {
            return error;
        }
    }

    private static class TimeZoneValidator implements Validator{
        @Override
        public boolean supports(Class<?> clazz) {
            return clazz.equals(SettingsDTO.class);
        }

        @Override
        public void validate(Object target, Errors errors) {
            SettingsDTO settingsDTO = (SettingsDTO) target;
            String[] timeZones = TimeZone.getAvailableIDs();
            for (String timeZone : timeZones) {
                if (timeZone.equals(settingsDTO.getTimeZone())) {
                    return;
                }
            }
            errors.rejectValue("timeZone", "msg.settings.timeZone.error","Wrong time zone");
        }
    }


}