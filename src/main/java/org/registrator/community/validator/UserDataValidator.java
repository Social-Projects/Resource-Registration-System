package org.registrator.community.validator;

import java.util.ArrayList;
import java.util.List;

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
        if(userRepository.getUserByEmail(registrationForm.getEmail()) != null){
            errors.rejectValue("email", "msg.registration.email.exist");
        }
        List<String> emptyPassportFields = emptyPassportField(registrationForm);
        for(String field : emptyPassportFields){
            errors.rejectValue(field, "msg.registration.passport3fields");
        }
    }

    /**
     * If one field of passport is filled than two other must be to. 
     * Method check this condition.
     * @param registrationForm registration form
     * @return empty string if passport not filled or field name if one of fields is null
     */
    private List<String> emptyPassportField(UserRegistrationDTO registrationForm) {
        List<String> rejectedFields = new ArrayList<String>();
        PassportDTO passport = registrationForm.getPassport();
        //if all passport fields are empty than OK!
        if( passport.getSeria().isEmpty()==passport.getNumber().isEmpty() ? 
                passport.getSeria().isEmpty()==passport.getPublished_by_data().isEmpty() : false){
            return rejectedFields;
        }
        //else get empty fields name
        if(passport.getSeria().isEmpty()){
            rejectedFields.add("passport.seria");
        }
        if(passport.getNumber().isEmpty()){
            rejectedFields.add("passport.number");
        }
        if(passport.getPublished_by_data().isEmpty()){
            rejectedFields.add("passport.published_by_data");
        }
        return rejectedFields;
    }
}
