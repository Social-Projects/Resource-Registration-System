package org.registrator.community.validator;

import org.registrator.community.dao.UserRepository;
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
        if(userRepository.getUserByEmail(registrationForm.getEmail()) != null){
            errors.rejectValue("email", "msg.registration.email.exist");
        }
    }
}
