package org.registrator.community.service;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.support.membermodification.MemberModifier;
import org.registrator.community.dto.json.UsersDataNotConfJson;
import org.registrator.community.entity.Address;
import org.registrator.community.entity.PassportInfo;
import org.registrator.community.entity.Role;
import org.registrator.community.entity.User;
import org.registrator.community.entity.VerificationToken;
import org.registrator.community.enumeration.ActionsWithNotConfUsers;
import org.registrator.community.enumeration.RoleType;
import org.registrator.community.enumeration.TokenType;
import org.registrator.community.enumeration.UserStatus;
import org.registrator.community.service.impl.NotConfirmedUsersServiceimpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.spy;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyListOf;




public class NotConfirmedUsersServiceTest {
    private static final Logger logger = LoggerFactory.getLogger(NotConfirmedUsersServiceTest.class);
    
    //test data
    private final String LOGIN = "testLogin";
    private final String BASELINK = "testBaseLink";
    private final String EMAIL = "test@ukr.net";
    private final String TOKEN = "testToken";
    private User user = new User();
    private List<User> userList = new ArrayList<User>();
    private List<String> loginList = new ArrayList<String>();
    private VerificationToken verificationToken = new VerificationToken();
    private List<VerificationToken> verificationTokenList;
    
    
    @InjectMocks
    private NotConfirmedUsersServiceimpl notConfirmedUsersService = spy(new NotConfirmedUsersServiceimpl());
   
    @Mock
    private UserService userService;
    
    @Mock
    private MailService mailService;
    
    @Mock
    private VerificationTokenService verificationTokenService;
    
    @Mock
    private PassportService passportService;
    
    @Mock
    private AddressService addressService;   
    
    
    @BeforeMethod
    public void init() {
        MockitoAnnotations.initMocks(this);
    }
    
    @BeforeClass
    public void initData(){
        user.setEmail(EMAIL);
        user.setLogin(LOGIN);
        userList.add(new User("login1","email1@ukr.net",UserStatus.INACTIVE));
        userList.add(new User("login2","email2@ukr.net",UserStatus.NOTCOMFIRMED));
        loginList.add("login1");
        loginList.add("login2");
        verificationTokenList = new ArrayList<VerificationToken>();
        verificationTokenList.add(verificationToken);
    }
    
    @Test
    public void sendConfirmEmailFirstTime(){
        verificationToken.setToken(TOKEN);

        when(userService.findUserByLogin(LOGIN)).thenReturn(user);
        when(verificationTokenService.saveEmailConfirmationToken(anyString(), anyString(), any(Date.class), anyString())).thenReturn(verificationToken);
        notConfirmedUsersService.sendConfirmEmailFirstTime(LOGIN,BASELINK);
        verify(verificationTokenService).saveEmailConfirmationToken(anyString(), anyString(), any(Date.class), anyString());
        verify(mailService).sendComfirmEMail(anyString(), anyString(), anyString(), anyString(), anyString());
        
    }
    
    @Test
    public void sendConfirmEmailAgain(){
        when(verificationTokenService.findVerificationTokenByLoginAndTokenType(anyString(), eq(TokenType.CONFIRM_EMAIL))).thenReturn(verificationToken);
        notConfirmedUsersService.sendConfirmEmailAgain(userList);
        verify(mailService, times(2)).sendComfirmEMail(anyString(), anyString(), anyString(), anyString(), anyString());
    }
    
    @Test(dependsOnMethods = { "sendConfirmEmailAgain" })
    public void deleteNotConfirmedUsers(){ 
        notConfirmedUsersService.deleteNotConfirmedUsers(userList);
        verify(passportService).delete(anyListOf(PassportInfo.class));
        verify(addressService).delete(anyListOf(Address.class));
        verify(userService).delete(anyListOf(User.class));
        
    }
    
    @Test(dependsOnMethods = { "deleteNotConfirmedUsers" })
    public void actionsWithNotConfirmedUsers(){
        UsersDataNotConfJson usersDataNotConfJson = null;
        String actual = notConfirmedUsersService.actionsWithNotConfirmedUsers(usersDataNotConfJson);
        String expected = "msg.batchops.wrongInput";
        Assert.assertEquals(actual, expected);
        
        usersDataNotConfJson = new UsersDataNotConfJson();
        usersDataNotConfJson.setActions(ActionsWithNotConfUsers.SENDEMAILAGAIN);
        actual = notConfirmedUsersService.actionsWithNotConfirmedUsers(usersDataNotConfJson);
        expected = "msg.batchops.wrongInput";
        
        usersDataNotConfJson.setLogins("login1,login2");
        actual = notConfirmedUsersService.actionsWithNotConfirmedUsers(usersDataNotConfJson);
        expected = "msg.notconfirmedusers.nosuchusersfound";
        Assert.assertEquals(actual, expected);
        
        when(userService.findUsersByLoginList(anyListOf(String.class))).thenReturn(userList);
        actual = notConfirmedUsersService.actionsWithNotConfirmedUsers(usersDataNotConfJson);
        expected = "msg.notconfirmedusers.onlynotconf";
        Assert.assertEquals(actual, expected);
        
        userList.get(0).setStatus(UserStatus.NOTCOMFIRMED);
        User loggetUser = new User();
        Role role = new Role(RoleType.ADMIN, "");
        loggetUser.setRole(role);
        when(userService.getLoggedUser()).thenReturn(loggetUser);
        
        when(notConfirmedUsersService.sendConfirmEmailAgain(anyListOf(User.class))).thenReturn("");
        notConfirmedUsersService.actionsWithNotConfirmedUsers(usersDataNotConfJson);
        verify(notConfirmedUsersService,Mockito.times(2)).sendConfirmEmailAgain(anyListOf(User.class));
        
        usersDataNotConfJson.setActions(ActionsWithNotConfUsers.DELETE);
        Mockito.doNothing().when(notConfirmedUsersService).deleteListVerificationToken(anyListOf(String.class));
        Mockito.doReturn("").when(notConfirmedUsersService).deleteNotConfirmedUsers(anyListOf(User.class));
        notConfirmedUsersService.actionsWithNotConfirmedUsers(usersDataNotConfJson);
        verify(notConfirmedUsersService,Mockito.times(2)).deleteNotConfirmedUsers(anyListOf(User.class));
        
    }
    
    @Test
    public void deleteListVerificationToken(){
        notConfirmedUsersService.deleteListVerificationToken(new ArrayList<String>());
        verify(verificationTokenService, Mockito.never()).deleteVerificationTokenList(anyListOf(VerificationToken.class));
        when(verificationTokenService.findVerificationTokensByLoginsAndTokenType(anyListOf(String.class), eq(TokenType.CONFIRM_EMAIL))).thenReturn(verificationTokenList);
        notConfirmedUsersService.deleteListVerificationToken(new ArrayList<String>());
        verify(verificationTokenService).deleteVerificationTokenList(anyListOf(VerificationToken.class));

    }
    
   
        
    @Test
    public void confirmEmail(){
        boolean actual = notConfirmedUsersService.confirmEmail(TOKEN);
        Assert.assertEquals(actual, false);
        
        when(verificationTokenService.findVerificationTokenByTokenAndTokenType(anyString(), eq(TokenType.CONFIRM_EMAIL))).thenReturn(verificationToken);
        actual = notConfirmedUsersService.confirmEmail(TOKEN);
        Assert.assertEquals(actual, false);

        when(userService.findUserByLogin(anyString())).thenReturn(user);
        actual = notConfirmedUsersService.confirmEmail(TOKEN);
        Assert.assertEquals(actual, true);
    }
    
}
