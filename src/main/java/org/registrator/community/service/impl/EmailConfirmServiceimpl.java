package org.registrator.community.service.impl;

import java.util.Date;

import org.registrator.community.dao.UserRepository;
import org.registrator.community.entity.User;
import org.registrator.community.entity.VerificationToken;
import org.registrator.community.enumeration.TokenType;
import org.registrator.community.enumeration.UserStatus;
import org.registrator.community.service.EmailConfirmService;
import org.registrator.community.service.MailService;
import org.registrator.community.service.UserService;
import org.registrator.community.service.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmailConfirmServiceimpl implements EmailConfirmService {
	
	@Autowired
	UserService userService;
	
	@Autowired
	private MailService mailService;

	@Autowired
	private VerificationTokenService verificationTokenService;
	
	
	
	/*@Autowired
	private PasswordEncoder  userPasswordEncoder;*/
	
	@Override
	public void sendConfirmEmailFirstTime(String login, String baseLink) {
		User user = userService.findUserByLogin(login);
		if(user != null){
			VerificationToken verifacationToken = verificationTokenService.saveEmailConfirmationToken(user.getLogin(), user.getEmail(), new Date(), baseLink);
			mailService.sendComfirmEMail(user.getEmail(), user.getFirstName(),verifacationToken.getToken(),baseLink);
		}	
	}
	
	@Override
    public void sendConfirmEmailAgain(String login) {
        User user = userService.findUserByLogin(login);
        if(user != null){
            VerificationToken verifacationToken = verificationTokenService.findVerificationTokenByLoginAndTokenType(login, TokenType.CONFIRM_EMAIL);
            mailService.sendComfirmEMail(user.getEmail(), user.getFirstName(),verifacationToken.getToken(),verifacationToken.getBaseLink());
        }   
    }
	
	@Transactional
	@Override
	public Boolean confirmEmail(String token){
		
			VerificationToken verToken = verificationTokenService.findVerificationTokenByTokenAndTokenType(token, TokenType.CONFIRM_EMAIL);
			if (verToken == null) return false;
			User user = userService.findUserByEmail(verToken.getUserEmail());
			if (user == null) return false;
			user.setStatus(UserStatus.INACTIVE);
			userService.updateUser(user);
			verificationTokenService.deleteVerificationToken(verToken);
			return true;

	}

}
