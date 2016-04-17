package org.registrator.community.validator;

import org.registrator.community.dao.UserRepository;
import org.registrator.community.dto.PassportDTO;
import org.registrator.community.dto.UserRegistrationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserDataValidator implements Validator {

    @Autowired
    public UserRepository userRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(UserRegistrationDTO.class);
    }

    /**
     * Validate user login, password confirmation and email
     * @param target
     * @param errors
     */
    @Override
    public void validate(Object target, Errors errors) {
        UserRegistrationDTO registrationForm = (UserRegistrationDTO) target;

        if (userRepository.findUserByLogin(registrationForm.getLogin()) != null) {
            errors.rejectValue("login", "msg.registration.login.exist");
        }
        if(!(registrationForm.getPassword()).equals(registrationForm.getConfirmPassword())){
            errors.rejectValue("confirmPassword", "msg.registration.badconfirmation");
        }
        String emptyPassportField = emptyPassportField(registrationForm);
        if(!emptyPassportField.isEmpty()){
            errors.rejectValue(emptyPassportField, "msg.registration.passport2fields");
        }
    }

    /**
     * If one field of passport is filled the second must be to. 
     * Method check this condition.
     * @param registrationForm registration form
     * @return empty string if passport not filled or field name if one of fields is null
     */
    private String emptyPassportField(UserRegistrationDTO registrationForm) {
        PassportDTO passport = registrationForm.getPassport();
        if(passport.getNumber().isEmpty() == passport.getSeria().isEmpty()){
            return "";
        }
        if(passport.getNumber().isEmpty()){
            return "passport.number";
        }
        return "passport.seria";
    }
}
