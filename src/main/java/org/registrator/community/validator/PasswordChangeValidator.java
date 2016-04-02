package org.registrator.community.validator;


import org.registrator.community.dto.PasswordChangeDTO;
import org.registrator.community.entity.User;
import org.registrator.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PasswordChangeValidator implements Validator {

    private static final String PASSWORD_PATTERN="[a-zA-Z0-9].{4,20}";


    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder userPasswordEncoder;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(PasswordChangeDTO.class);
    }

    /**
     * Validate user old password and password confirmation
     * @param target
     * @param errors
     */

    @Override
    public void validate(Object target, Errors errors) {
        PasswordChangeDTO passwordChangeDTO = (PasswordChangeDTO) target;

        validateOldPassword(passwordChangeDTO.getPassword(), errors);
        validateNewPassword(passwordChangeDTO.getNewPassword(), errors);
        validatePasswordEquals(passwordChangeDTO.getNewPassword(), passwordChangeDTO.getConfirmNewPassword(), errors);
    }

    private void validateOldPassword(String password, Errors errors) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByLogin(auth.getName());
        if (!userPasswordEncoder.matches(password, user.getPassword())) {
            errors.rejectValue("password", "msg.change.password.wrong.password");
        }
    }

    private void validateNewPassword(String newPassword, Errors errors) {
        if (newPassword == null || newPassword.isEmpty()) {
            errors.rejectValue("newPassword", "user.Password.emptyPassword", "Enter a Password");
        } else if (!newPassword.matches(PASSWORD_PATTERN)) {
            errors.rejectValue("newPassword", "msg.change.password.nonvalid.password");
        }
    }

    private void validatePasswordEquals(String newPassword, String confirmNewPassword, Errors errors) {
        if (!newPassword.equals(confirmNewPassword)) {
            errors.rejectValue("confirmNewPassword", "msg.change.password.badconfirmation");
        }
    }
}