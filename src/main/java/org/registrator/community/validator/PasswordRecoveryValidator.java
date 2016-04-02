package org.registrator.community.validator;

import org.registrator.community.dto.PasswordRecoveryDTO;
import org.registrator.community.service.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PasswordRecoveryValidator implements Validator {
	
	private static final String PASSWORD_PATTERN="[a-zA-Z0-9].{5,20}";
	
	@Autowired
	private VerificationTokenService verificationTokenService;

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.equals(PasswordRecoveryDTO.class);
	}

	@Override
    public void validate(Object object, Errors errors) {
		PasswordRecoveryDTO passwordRecoveryDTO = (PasswordRecoveryDTO) object;

        validatePassword(passwordRecoveryDTO.getPassword(),errors);
        validateHashToken(passwordRecoveryDTO.getHash(),errors);
        validatePasswordEquals(passwordRecoveryDTO.getPassword(),passwordRecoveryDTO.getConfirmPassword(),errors);
    }

	private void validateHashToken(String hash, Errors errors) {
		if (!verificationTokenService.isExistValidVerificationToken(hash)){
            errors.rejectValue("hash","user.Hash.passwordNotEquals","Not valid token");
        }
	}

	private void validatePasswordEquals(String password,
			String confirmPassword, Errors errors) {
		if (!password.equals(confirmPassword)){
            errors.rejectValue("confirmPassword","user.Password.passwordNotEquals","Passwords are not equal");
        }
	}

	private void validatePassword(String password, Errors errors) {
		if(password==null || password.isEmpty()){
            errors.rejectValue("password", "user.Password.emptyPassword","Enter a Password");
        }
        else if(!password.matches(PASSWORD_PATTERN)){
            errors.rejectValue("password", "user.Password.badPassword","Enter correct password");
        }	
	}

}
