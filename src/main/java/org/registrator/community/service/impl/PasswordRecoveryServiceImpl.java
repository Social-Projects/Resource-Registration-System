package org.registrator.community.service.impl;

import java.util.Date;

import org.registrator.community.dao.UserRepository;
import org.registrator.community.entity.User;
import org.registrator.community.entity.VerificationToken;
import org.registrator.community.enumeration.TokenType;
import org.registrator.community.service.MailService;
import org.registrator.community.service.PasswordRecoveryService;
import org.registrator.community.service.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordRecoveryServiceImpl implements PasswordRecoveryService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	private MailService mailService;

	@Autowired
	private VerificationTokenService verificationTokenService;
	
	@Autowired
	private PasswordEncoder  userPasswordEncoder;

	@Override
	public boolean recoverPasswordByEmailLink(String token,String password) {
		if(verificationTokenService.isExistValidVerificationToken(token)){
			VerificationToken verificationToken = verificationTokenService.findVerificationTokenByTokenAndTokenType(token, TokenType.RECOVER_PASSWORD);
				User user = userRepository.getUserByEmail(verificationToken.getUserEmail());
				if(user != null){
					user.setPassword(userPasswordEncoder.encode(password));
					userRepository.save(user);
					verificationTokenService.deleteVerificationToken(verificationToken);
					return true;
				}
		}
		return false;
	}

	@Override
	public void sendRecoverPasswordEmail(String userEmail, String baseLink) {
		User user = userRepository.getUserByEmail(userEmail);
		if(user != null){
			VerificationToken verifacationToken = verificationTokenService.savePasswordVerificationToken(userEmail, new Date());
			mailService.sendRecoveryPasswordMail(userEmail, user.getFirstName(),verifacationToken.getToken(),baseLink);
		}	
	}

}
