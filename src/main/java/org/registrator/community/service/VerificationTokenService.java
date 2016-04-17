package org.registrator.community.service;

import java.util.Date;
import java.util.List;

import org.registrator.community.entity.VerificationToken;
import org.registrator.community.enumeration.TokenType;

public interface VerificationTokenService {
	
	boolean deletePasswordVerificationTokenByLogin(String login);
	
	void deleteVerificationToken(VerificationToken verificationToken);
	
	VerificationToken savePasswordVerificationToken(String userEmail, Date nowTime);
	
	VerificationToken savePasswordVerificationToken(String userEmail, String login, Date nowTime);
	
	String createHashForPasswordToken();
	
	VerificationToken findVerificationTokenByTokenAndTokenType(String token, TokenType type);
	
	boolean isExistValidVerificationToken(String token);

    VerificationToken saveEmailConfirmationToken(String login, String userEmail, Date nowTime, String baseLink);

    VerificationToken findVerificationTokenByLoginAndTokenType(String login, TokenType type);

    void deleteVerificationTokenList(List<VerificationToken> verifacationTokenList);
    
    List<VerificationToken> findVerificationTokensByLoginsAndTokenType(List<String> loginList, TokenType type);

}
