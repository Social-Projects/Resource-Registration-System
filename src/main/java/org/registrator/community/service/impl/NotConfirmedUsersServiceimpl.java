package org.registrator.community.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.registrator.community.dto.json.UsersDataNotConfJson;
import org.registrator.community.entity.Address;
import org.registrator.community.entity.PassportInfo;
import org.registrator.community.entity.User;
import org.registrator.community.entity.VerificationToken;
import org.registrator.community.enumeration.RoleType;
import org.registrator.community.enumeration.TokenType;
import org.registrator.community.enumeration.UserStatus;
import org.registrator.community.service.NotConfirmedUsersService;
import org.registrator.community.service.PassportService;
import org.registrator.community.service.AddressService;
import org.registrator.community.service.MailService;
import org.registrator.community.service.UserService;
import org.registrator.community.service.VerificationTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NotConfirmedUsersServiceimpl implements NotConfirmedUsersService {
    private static final Logger logger = LoggerFactory.getLogger(NotConfirmedUsersServiceimpl.class);
    
    @Autowired
	private UserService userService;
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	private PassportService passportService;
	
	@Autowired
	private AddressService addressService;

	@Autowired
	private VerificationTokenService verificationTokenService;
	
	@Override
	public void sendConfirmEmailFirstTime(String login, String baseLink) {
		User user = userService.findUserByLogin(login);
		if(user != null){
			VerificationToken verifacationToken = verificationTokenService.saveEmailConfirmationToken(user.getLogin(), user.getEmail(), new Date(), baseLink);
			mailService.sendComfirmEMail(user.getEmail(), user.getFirstName(), user.getLogin(), verifacationToken.getToken(),baseLink);
			logger.info("send confirm email to {} [login: {}]", user.getEmail(), user.getLogin());
		}	
	}
	
	@Override
    public String sendConfirmEmailAgain(List<User> userList) {
        for (User user: userList){
            VerificationToken verifacationToken = verificationTokenService.findVerificationTokenByLoginAndTokenType(user.getLogin(), TokenType.CONFIRM_EMAIL);
            if (verifacationToken!=null){
                logger.info("Send email to {}", user.getEmail());
                mailService.sendComfirmEMail(user.getEmail(), user.getFirstName(), user.getLogin(), verifacationToken.getToken(),verifacationToken.getBaseLink());
            }else{
                logger.warn("no verifacationToken found");
                return "msg.notconfirmedusers.unsucsesfullysent";
            }
        }
        return "msg.notconfirmedusers.sucsesfullysent";              
    }
	
	
	@Transactional
	@Override
	public String actionsWithNotConfirmedUsers(UsersDataNotConfJson usersDataNotConfJson){
	    logger.info("Recieved data: {}", usersDataNotConfJson);
	    if (usersDataNotConfJson == null || usersDataNotConfJson.getActions() == null || 
	            usersDataNotConfJson.getLogins() == null) {
            logger.warn("Empty usersDataNotConfJson file");
            return "msg.batchops.wrongInput";
        }
	    
	    List<String> loginList = new ArrayList<String>();
	    String logins = usersDataNotConfJson.getLogins();
	    Collections.addAll(loginList, logins.split(","));
	    logger.debug("Loking for users with logins: {}", logins);
        List<User> userList = userService.findUsersByLoginList(loginList);
        if (userList.isEmpty()){
            logger.warn("no such users found");
            return "msg.notconfirmedusers.nosuchusersfound";
        }
        logger.debug("users found");
        
        for (User user: userList){
            if (user.getStatus() != UserStatus.NOTCOMFIRMED) {
                logger.debug("Try to delete users wich are not in status NOTCOMFIRMED");
                return "msg.notconfirmedusers.onlynotconf";
            }
        }
        
        logger.debug("check logged user RoleType");
        User loggetUser = userService.getLoggedUser();
	    RoleType roleType = loggetUser.getRole().getType();
	    logger.debug("logged user RoleType: {}", roleType);
	    
	    if (roleType!=RoleType.ADMIN && roleType!=RoleType.COMMISSIONER){
	        logger.warn("try to perform action by forbitten user: {}", roleType);
	        return "msg.notconfirmedusers.onlyadminorcommisioner";
	    }else if(roleType == RoleType.COMMISSIONER){
	        logger.debug("check whether users over which operation is performing  belongs to the same community");
	        for (User user: userList){
	            if(user.getTerritorialCommunity()!=loggetUser.getTerritorialCommunity()){
	                logger.debug("commisioner try to delete users from other community");
	                return "msg.notconfirmedusers.commistryothercommunity";
	            }
	        }
	    }
 
        switch(usersDataNotConfJson.getActions()){
        case DELETE:
            logger.info("Run Action DELETE");
            deleteListVerificationToken(loginList);
            return deleteNotConfirmedUsers(userList);
        case SENDEMAILAGAIN:
            logger.info("Run Action SENDEMAILAGAIN");
            return sendConfirmEmailAgain(userList);
        }
	        
	    return  "msg.batchops.wrongInput";
	}
	
	 /**
     * Method, which delete users only if user status = NOTCOMFIRMED
     *
     * @return String message
     */
    @Transactional
    @Override
    public String deleteNotConfirmedUsers(List<User> userList) {
        List<PassportInfo> passportInfoList = new ArrayList<PassportInfo>();
        List<Address> addressList = new ArrayList<Address>();
        
        for (User user: userList){
            passportInfoList.addAll(user.getPassport());
            addressList.addAll(user.getAddress());
        }
        
        logger.info("start delete operations");
        
        passportService.delete(passportInfoList);
        logger.info("pasports succesfuly deleted");
        
        addressService.delete(addressList);
        logger.info("addresses succesfuly deleted");
        
        userService.delete(userList);
        logger.info("users succesfuly deleted");
        
        return "msg.notconfirmedusers.sucsesfullydeleted";
    }
    
    @Transactional
    public void deleteListVerificationToken(List<String> loginList){
        logger.debug("Looking for verifacationTokens with logins: {}", loginList);
        List<VerificationToken> verifacationTokenList = verificationTokenService.findVerificationTokensByLoginsAndTokenType(loginList, TokenType.CONFIRM_EMAIL);
        if (verifacationTokenList.isEmpty()){
            logger.warn("no such VerificationToken found in database");
        }else{
            logger.debug("VerificationTokens found");
            logger.info("start delete operations");
            verificationTokenService.deleteVerificationTokenList(verifacationTokenList);
            logger.info("VerificationTokens successfully deleted");
        }        
        
    }
	
	@Transactional
	@Override
	public Boolean confirmEmail(String token){
        VerificationToken verToken = verificationTokenService.findVerificationTokenByTokenAndTokenType(token,
                TokenType.CONFIRM_EMAIL);
        if (verToken == null) {
            logger.warn("no such VerificationToken found in database");
            return false;
        }
        User user = userService.findUserByLogin(verToken.getUserLogin());
        if (user == null) {
            logger.warn("no such User found in database");
            return false;
        }
        user.setStatus(UserStatus.INACTIVE);
        userService.updateUser(user);
        verificationTokenService.deleteVerificationToken(verToken);
        logger.info("user succesfuly confirmed " + user.getLogin());
        return true;

	}

}
