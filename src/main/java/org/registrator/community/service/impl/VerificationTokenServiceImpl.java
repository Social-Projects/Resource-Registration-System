package org.registrator.community.service.impl;

import java.util.Date;
import java.util.UUID;

import org.registrator.community.dao.VerificationTokenRepository;
import org.registrator.community.entity.VerificationToken;
import org.registrator.community.enumeration.TokenType;
import org.registrator.community.service.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VerificationTokenServiceImpl implements VerificationTokenService{
	
	public static final int PASSWORD_TOKEN_EXPIRY_TIME = 1000*60*60*24;
	public static final int EMAIL_TOKEN_EXPIRY_TIME = 1000*60*60*24*7;
	
	@Autowired
	private VerificationTokenRepository verificationTokenRepository;

	@Override
	public boolean deletePasswordVerificationTokenByEmail(String email) {
		VerificationToken passwordResetToken = verificationTokenRepository.findTokenByEmail(email);
		if(passwordResetToken != null){
			verificationTokenRepository.delete(passwordResetToken);
			return true;
		}
		return false;
	}

	@Override
	public VerificationToken savePasswordVerificationToken(String userEmail, Date nowTime) {
		String token = createHashForPasswordToken();
		nowTime.setTime(nowTime.getTime()+PASSWORD_TOKEN_EXPIRY_TIME);
		VerificationToken passwordVerificationToken = new VerificationToken(token,userEmail,nowTime,TokenType.RECOVER_PASSWORD);
		deletePasswordVerificationTokenByEmail(userEmail);
		verificationTokenRepository.save(passwordVerificationToken);
		return passwordVerificationToken;
	}
	
	@Override
	public VerificationToken saveEmailConfirmationToken(String userEmail, Date nowTime) {
		String token = createHashForPasswordToken();
		nowTime.setTime(nowTime.getTime()+EMAIL_TOKEN_EXPIRY_TIME);
		VerificationToken emailVerificationToken = new VerificationToken(token,userEmail,nowTime,TokenType.CONFIRM_EMAIL);
		deletePasswordVerificationTokenByEmail(userEmail);
		verificationTokenRepository.save(emailVerificationToken);
		return emailVerificationToken;
	}

	@Override
	public String createHashForPasswordToken() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}

	@Override
	public VerificationToken findVerificationTokenByTokenAndTokenType(String token,
			TokenType type) {
		return verificationTokenRepository.findVerificationTokenByTokenAndTokenType(token, type);
	}

	@Override
	public boolean isExistValidVerificationToken(String token) {
		VerificationToken verToken = verificationTokenRepository.findVerificationTokenByToken(token);
		if(verToken != null){
			return (verToken.getExpiryDate().getTime()> System.currentTimeMillis());
		}
		return false;
	}

	@Override
	public void deleteVerificationToken(VerificationToken verificationToken) {
		verificationTokenRepository.delete(verificationToken);
	}
	
	
}
