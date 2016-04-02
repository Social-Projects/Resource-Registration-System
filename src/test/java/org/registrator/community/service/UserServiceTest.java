package org.registrator.community.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.support.membermodification.MemberModifier;
import org.registrator.community.dao.AddressRepository;
import org.registrator.community.dao.PassportRepository;
import org.registrator.community.dao.ResourceNumberRepository;
import org.registrator.community.dao.RoleRepository;
import org.registrator.community.dao.TomeRepository;
import org.registrator.community.dao.UserRepository;
import org.registrator.community.dto.AddressDTO;
import org.registrator.community.dto.PassportDTO;
import org.registrator.community.dto.UserDTO;
import org.registrator.community.dto.UserRegistrationDTO;
import org.registrator.community.dto.json.ResourceNumberJson;
import org.registrator.community.dto.json.UserStatusJson;
import org.registrator.community.entity.Address;
import org.registrator.community.entity.PassportInfo;
import org.registrator.community.entity.ResourceNumber;
import org.registrator.community.entity.Role;
import org.registrator.community.entity.TerritorialCommunity;
import org.registrator.community.entity.Tome;
import org.registrator.community.entity.User;
import org.registrator.community.entity.WillDocument;
import org.registrator.community.enumeration.RoleType;
import org.registrator.community.enumeration.UserStatus;
import org.registrator.community.service.CommunityService;
import org.registrator.community.service.UserService;
import org.registrator.community.service.impl.UserServiceImpl;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserServiceTest {
	private static final Logger log = LoggerFactory.getLogger(UserServiceTest.class);

	@InjectMocks
	private UserService userService = new UserServiceImpl();
	@Mock
	private UserRepository userRepository;
	@Mock
	private RoleRepository roleRepository;
	@Mock
	private CommunityService communityService;
	@Mock
	private PassportRepository passportRepository;
	@Mock
	private AddressRepository addressRepository;
	@Mock
	private ResourceNumberRepository resourceNumberRepository;
	@Mock
	private TomeRepository tomeRepository;
	@Mock
	private PasswordEncoder userPasswordEncoder;
	
	private List<User> fakeUserRepository = new ArrayList<User>();
	private void mockUserRepository(){
		Mockito.when(userRepository.findUserByLogin(Mockito.anyString())).then(new Answer<User>() {
			@Override
			public User answer(InvocationOnMock invocation) throws Throwable {
				for(int i=0;i<fakeUserRepository.size();i++)
					if(fakeUserRepository.get(i).getLogin()==invocation.getArgumentAt(0, String.class))
						return fakeUserRepository.get(i);
				return null;
			}
		});
		Mockito.when(userRepository.getUserByEmail(Mockito.anyString())).then(new Answer<User>() {
			@Override
			public User answer(InvocationOnMock invocation) throws Throwable {
				for(int i=0;i<fakeUserRepository.size();i++)
					if(fakeUserRepository.get(i).getEmail()==invocation.getArgumentAt(0, String.class))
						return fakeUserRepository.get(i);
				return null;
			}
		});
		Mockito.when(userRepository.getUserByLoginAndPassword(Mockito.anyString(), Mockito.anyString())).then(new Answer<User>() {
			@Override
			public User answer(InvocationOnMock invocation) throws Throwable {
				for(int i=0;i<fakeUserRepository.size();i++)
					if(fakeUserRepository.get(i).getLogin()==invocation.getArgumentAt(0, String.class))
						if(fakeUserRepository.get(i).getPassword()==invocation.getArgumentAt(1, String.class))
							return fakeUserRepository.get(i);
				return null;
			}
		});
		Mockito.when(userRepository.save(Mockito.any(User.class))).then(new Answer<User>() {
			@Override
			public User answer(InvocationOnMock invocation) throws Throwable {
				fakeUserRepository.add(invocation.getArgumentAt(0, User.class));
				return invocation.getArgumentAt(0, User.class);
			}
		});
		Mockito.when(userRepository.findOwnersLikeProposed(Mockito.any(TerritorialCommunity.class), Mockito.anyString())).then(new Answer<List<User>>() {
			@Override
			public List<User> answer(InvocationOnMock invocation) throws Throwable {
				List<User> users = new ArrayList<User>();
				for(User user: fakeUserRepository){
					if(user.getTerritorialCommunity().getName()==invocation.getArgumentAt(0, TerritorialCommunity.class).getName())
						if(user.getLastName().contains(invocation.getArgumentAt(1, String.class)))
							users.add(user);
				}
				return users;
			}
		});
		Mockito.when(userRepository.findAll()).thenReturn(fakeUserRepository);
	}
	
	private List<Role> fakeRoleRepository = new ArrayList<Role>();
	private void mockRoleRepository(){
		Mockito.when(roleRepository.findOne(Mockito.anyString())).then(new Answer<Role>() {
			@Override
			public Role answer(InvocationOnMock invocation) throws Throwable {
				if(Integer.parseInt(invocation.getArgumentAt(0, String.class))<fakeRoleRepository.size())
					return fakeRoleRepository.get(Integer.parseInt(invocation.getArgumentAt(0, String.class)));
				return null;
			}
		});
		Mockito.when(roleRepository.findAll()).thenReturn(fakeRoleRepository);
	}
	
	private List<TerritorialCommunity> fakeCommunityRepository = new ArrayList<TerritorialCommunity>();
	private void mockCommunityRepository(){
		Mockito.when(communityService.findByName(Mockito.anyString())).then(new Answer<TerritorialCommunity>() {
			@Override
			public TerritorialCommunity answer(InvocationOnMock invocation) throws Throwable {
				for(int i=0;i<fakeCommunityRepository.size();i++)
					if(fakeCommunityRepository.get(i).getName()==invocation.getArgumentAt(0, String.class))
						return fakeCommunityRepository.get(i);
				return null;
			}
		});
	}
	
	private List<Address> fakeAddressRepository = new ArrayList<Address>();
	private void mockAddressRepository(){
		Mockito.when(addressRepository.save(Mockito.any(Address.class))).then(new Answer<Address>() {
			@Override
			public Address answer(InvocationOnMock invocation) throws Throwable {
				fakeAddressRepository.add(invocation.getArgumentAt(0, Address.class));
				return invocation.getArgumentAt(0, Address.class);
			}
		});
	}
	
	private List<PassportInfo> fakePassportRepository = new ArrayList<PassportInfo>();
	private void mockPassportRepository(){
		Mockito.when(passportRepository.save(Mockito.any(PassportInfo.class))).then(new Answer<PassportInfo>() {
			@Override
			public PassportInfo answer(InvocationOnMock invocation) throws Throwable {
				fakePassportRepository.add(invocation.getArgumentAt(0, PassportInfo.class));
				return invocation.getArgumentAt(0, PassportInfo.class);
			}
		});
	}
	
	private List<Tome> fakeTomeRepository = new ArrayList<Tome>();
	private void mockTomeRepository(){
		Mockito.when(tomeRepository.save(Mockito.any(Tome.class))).then(new Answer<Tome>() {
			@Override
			public Tome answer(InvocationOnMock invocation) throws Throwable {
				fakeTomeRepository.add(invocation.getArgumentAt(0, Tome.class));
				return invocation.getArgumentAt(0, Tome.class);
			}
		});
	}

	@BeforeMethod
	public void beforeMethod() throws IllegalArgumentException, IllegalAccessException {
		MockitoAnnotations.initMocks(this);
		MemberModifier.field(userService.getClass(), "logger").set(userService, LoggerFactory.getLogger(UserServiceTest.class));

		// initialize test data before each method
		Role roleUser = new Role(RoleType.USER, "description");
		Role roleRegistrator = new Role(RoleType.REGISTRATOR, "description");
		Role roleCommissioner = new Role(RoleType.COMMISSIONER, "description");
		Role roleAdmin = new Role(RoleType.ADMIN, "description");
		// equal to enum ordinal
		roleUser.setRoleId(0);
		roleRegistrator.setRoleId(1);
		roleAdmin.setRoleId(2);
		roleCommissioner.setRoleId(3);
		fakeRoleRepository.add(roleUser);
		fakeRoleRepository.add(roleRegistrator);
		fakeRoleRepository.add(roleAdmin);
		fakeRoleRepository.add(roleCommissioner);
		
		User user = new User("user", "user", roleUser, "Василь", "Труба", "Георгійович",
				"vasul.tryba@gmail.com", UserStatus.ACTIVE.toString(), "09800000002");
		User registrator = new User("registrator", "registrator", roleRegistrator, "Володимир",
				"Сидор", "Тарасович", "sydor@gmail.com", UserStatus.ACTIVE.toString(), "09500000003");
		User commissioner = new User("commissioner", "commissioner", roleCommissioner,
				"Віталій", "Гонгадзе", "Юрійович", "gongadze@gmail.com", UserStatus.ACTIVE.toString(), "09300000004");
		User admin = new User("admin", "admin", roleAdmin, "Назар", "Вітер", "Романович",
				"viter@gmail.com", UserStatus.ACTIVE.toString(), "06700000005");
		fakeUserRepository.add(user);
		fakeUserRepository.add(registrator);
		fakeUserRepository.add(commissioner);
		fakeUserRepository.add(admin);
		
		TerritorialCommunity communityUkraine = new TerritorialCommunity();
		TerritorialCommunity communityLviv = new TerritorialCommunity();
		communityUkraine.setName("Україна");
		communityLviv.setName("Львів");
		user.setTerritorialCommunity(communityUkraine);
		registrator.setTerritorialCommunity(communityUkraine);
		commissioner.setTerritorialCommunity(communityUkraine);
		admin.setTerritorialCommunity(communityUkraine);
		fakeCommunityRepository.add(communityUkraine);
		fakeCommunityRepository.add(communityLviv);

		Address userAddress = new Address(user, "00000", "Львівська", "Галицький", "Львів", "Вітовського", "48", "31");
		Address registratorAddress = new Address(registrator, "11111", "Львівська", "Личаківський", "Львів", "Княгині Ольги", "21", "12");
		Address commissionerAddress = new Address(commissioner, "22222", "Київська", "Шевченківський", "Київ", "Т.Г.Шевченка", "13", "31");
		Address adminAddress = new Address(admin, "33333", "Київська", "Шевченківський", "Київ", "Т.Г.Шевченка", "44", "66");
		user.setAddress(Arrays.asList(userAddress));
		registrator.setAddress(Arrays.asList(registratorAddress));
		commissioner.setAddress(Arrays.asList(commissionerAddress));
		admin.setAddress(Arrays.asList(adminAddress));
		fakeAddressRepository.add(userAddress);
		fakeAddressRepository.add(registratorAddress);
		fakeAddressRepository.add(commissionerAddress);
		fakeAddressRepository.add(adminAddress);
		
		PassportInfo userPassport = new PassportInfo(user, "AA", "00000", "Народом України");
		PassportInfo registratorPassport = new PassportInfo(registrator, "ББ", "11111", "Народом України");
		PassportInfo commissionerPassport = new PassportInfo(commissioner, "ВВ", "22222", "Народом України");
		PassportInfo adminPassport = new PassportInfo(admin, "ГГ", "33333", "Народом України");
		user.setPassport(Arrays.asList(userPassport));
		registrator.setPassport(Arrays.asList(registratorPassport));
		commissioner.setPassport(Arrays.asList(commissionerPassport));
		admin.setPassport(Arrays.asList(adminPassport));
		fakePassportRepository.add(userPassport);
		fakePassportRepository.add(registratorPassport);
		fakePassportRepository.add(commissionerPassport);
		fakePassportRepository.add(adminPassport);
		
		user.setUserId(1);
		registrator.setUserId(2);
		admin.setUserId(3);
		commissioner.setUserId(4);
		
		mockUserRepository();
		mockRoleRepository();
		mockCommunityRepository();
		mockAddressRepository();
		mockPassportRepository();
		mockTomeRepository();
	}
	
	@AfterMethod
	public void afterMethod(){
		//clear fake repositories after each test method
		fakeUserRepository.clear();
		fakeRoleRepository.clear();
		fakeCommunityRepository.clear();
		fakeAddressRepository.clear();
		fakePassportRepository.clear();
		fakeTomeRepository.clear();
	}

	/**
	 * Unit Test of function userService.login
	 * @param login user name
	 * @param password password
	 * @param isPositive is expected test result positive
	 */
	@Test(dataProvider="providerLogin")
	public void login(String login, String password, boolean isPositive) {
		log.debug(String.format("credentials: Log:'%s' Pass:'%s'",login,password));
		Assert.assertEquals(userService.login(login, password), isPositive);// correct
	}
	
	/**
	 * Unit Test of function userService.getUserByLogin
	 * @param login user name
	 * @param expected expected User return value
	 * @param isPositive is expected test result positive
	 */
	@Test(dataProvider="providerGetUserByLogin")
	public void getUserByLogin(String login, User expected,  boolean isPositive) {
		log.debug(String.format("login: %s [%s]", login, isPositive));
		if(isPositive){
			User actual = userService.getUserByLogin(login);
			Assert.assertNotNull(actual);// correct data
			assertEqualsUsers(actual, expected);
		}else{
			Assert.assertNull(userService.getUserByLogin(login));// incorrect data
		}
	}
	
	/**
	 * Unit Test of function userService.findUserByLogin
	 * @param login user name
	 * @param expected expected User return value
	 * @param isPositive is expected test result positive
	 */
	@Test(dataProvider="providerGetUserByLogin")
	public void findUserByLogin(String login, User expected,  boolean isPositive) {
		log.debug(String.format("login: %s [%s]", login, isPositive));
		if(isPositive){
			User actual = userService.findUserByLogin(login);
			Assert.assertNotNull(actual);// correct data
			assertEqualsUsers(actual, expected);
		}else{
			Assert.assertNull(userService.getUserByLogin(login));// incorrect data
		}
	}

	/**
	 * Unit Test of function userService.changeUserStatus
	 * @param login user name
	 * @param status expected status
	 */
	@Test(dataProvider = "providerChangeUserStatus", dependsOnMethods={"findUserByLogin"})
	public void changeUserStatus(String login, UserStatus status) {
		UserStatusJson userStatusJson = new UserStatusJson(login, status.toString());
		log.debug(String.format("login: '%s', status: '%s'", login, status.toString()));
		userService.changeUserStatus(userStatusJson);
		Assert.assertEquals(userService.findUserByLogin(login).getStatus(), status);// correct data
	}
	
	/**
	 * Unit Test of function userService.changeUserRole
	 * @param login user name
	 * @param roleType expected role (id eq enum.ordinal)
	 */
	@Test(dataProvider = "providerChangeUserRole", dependsOnMethods = { "findUserByLogin" })
	public void changeUserRole(String login, RoleType roleType) {
		userService.changeUserRole(login, roleType.ordinal());
		Assert.assertEquals(userService.findUserByLogin(login).getRole().getType(), roleType);
	}
	
	/**
	 * Unit Test of function userService.editUserInformation
	 */
	@Test(dependsOnMethods = { "findUserByLogin" })
	public void editUserInformation() {
		// test data
		final UserDTO expected = new UserDTO("Петро", "Замоканий", "Афанасійович", RoleType.REGISTRATOR.toString(),
				"user","petro@gmail.com", UserStatus.BLOCK.toString(), 
				new AddressDTO("11111", "Івано-Франківська","Шевченківський", "Івано-Франківськ", "Зелена", "21", "12"),
				new PassportDTO("ББ", "22222", "Народом України2"));// login 'user' important!
		expected.setTerritorialCommunity("Львів");
		//positive
		UserDTO actual = userService.editUserInformation(expected);
		assertEqualsUsersDto(actual, expected);//compare
		//negative
		Mockito.when(userRepository.findUserByLogin("user")).thenReturn(null);
		Assert.assertNull(userService.editUserInformation(expected), "Saved null user");//
	}
	
	/**
	 * Unit Test of function userService.fillInUserStatusforRegistratedUsers
	 * Registered user have statuses: ACTIVE, BLOCK
	 */
	@Test
	public void fillInUserStatusforRegistratedUsers() {
		// test data
		final List<UserStatus> expected = Arrays.asList(UserStatus.ACTIVE, UserStatus.BLOCK);
		// test action
		List<UserStatus> actual = userService.fillInUserStatusforRegistratedUsers();
		Assert.assertTrue(actual.containsAll(expected) && expected.containsAll(actual));
	}
	
	/**
	 * Unit Test of function userService.fillInUserStatusforInactiveUsers
	 * Inactive user have status: ACTIVE, BLOCK, INACTIVE
	 */
	@Test
	public void fillInUserStatusforInactiveUsers() {
		// test data
		final List<UserStatus> expected = Arrays.asList(UserStatus.ACTIVE, UserStatus.BLOCK, UserStatus.INACTIVE);
		// test action
		List<UserStatus> actual = userService.fillInUserStatusforInactiveUsers();
		Assert.assertTrue(actual.containsAll(expected) && expected.containsAll(actual));
	}

	/**
	 * Unit Test of function userService.getUserDtoList
	 */
	@Test
	public void getUserDtoList() {
		//create expected result
		List<UserDTO> expected = new ArrayList<UserDTO>();
		for(int i=0;i<fakeUserRepository.size();i++){
			UserDTO userDto = createUserDto(fakeUserRepository.get(i).getLogin());
			expected.add(userDto);
			
		}
		//receive function result and compare
		List<UserDTO> actual = userService.getUserDtoList();
		Assert.assertEquals(actual.size(), expected.size(), "Bad size of users returned number");
		for (UserDTO actualDto : actual) {
			for(UserDTO expectedDto: expected){
				if(actualDto.getLogin()==expectedDto.getLogin()){
					assertEqualsUsersDto(actualDto, expectedDto);
					break;//test next actualDto
				}
			}
		}
	}
	
	/**
	 * Unit Test of function userService.getUserDto
	 * @param login user name
	 * @param isPositive is expected test result positive
	 */
	@Test(dataProvider="providerGetUserDto" ,dependsOnMethods = { "getUserByLogin" })
	public void getUserDto(String login, boolean isPositive) {
		boolean exception = false;
		UserDTO expected = createUserDto(login);
	    if(userRepository.findUserByLogin(login) != null)
	        expected.setTerritorialCommunity(userRepository.findUserByLogin(login).getTerritorialCommunity().getName());
		if(isPositive)
			expected.getPassport().setComment("comment");
		User expectedUser = null;
		for(User user: fakeUserRepository)
			if(user.getLogin()==login)
				expectedUser = user;
		// test action
		try{
			UserDTO actual = userService.getUserDto(login);
			assertEqualsUsersDto(actual, expected);
			// other branches
			Tome tom = new Tome(expectedUser, "Парк Погулянка");
			ResourceNumber resourceNumber = new ResourceNumber(0, "0", expectedUser);
			WillDocument willDocument = new WillDocument();
			willDocument.setComment("comment");
			expectedUser.setWillDocument(Arrays.asList(willDocument));
			Mockito.when(tomeRepository.findTomeByRegistrator(Mockito.any(User.class))).thenReturn(tom);
			Mockito.when(resourceNumberRepository.findResourceNumberByUser(Mockito.any())).thenReturn(resourceNumber);
			Assert.assertNotNull(userService.getUserDto("user"));
		}catch(NullPointerException e){
			//for negative test we wait for exception
			exception = true;
		}
		Assert.assertEquals(exception, !isPositive, "Test crashed");
	}
	
	/**
	 * Unit Test of function userService.getAllRegistratedUsers
	 * @param logins logins of users, which status we'll change to inactive for test
	 */
	@Test(dataProvider="providerGetAllUsers", dependsOnMethods = { "findUserByLogin", "fillInUserStatusforRegistratedUsers" })
	public void getAllRegistratedUsers(List<String> logins) {
		// prepare test data
		for(String login: logins)
			userService.findUserByLogin(login).setStatus(UserStatus.INACTIVE);
		// get available statuses for registered users
		List<UserDTO> expected = new ArrayList<UserDTO>();
		List<UserStatus> statuses = userService.fillInUserStatusforRegistratedUsers();
		for(User user: fakeUserRepository){
			if(statuses.contains(user.getStatus())){
				UserDTO userDto = createUserDto(user.getLogin());
				expected.add(userDto);
			}	
		}
		// test action
		List<UserDTO> actual= userService.getAllRegistratedUsers();
		Assert.assertEquals(actual.size(), expected.size(), "Bad size of List<UserDTO>");
		for (UserDTO actualDto : actual) {
			for (UserDTO expectedDto : expected)
				if (actualDto.getLogin() == expectedDto.getLogin())
					assertEqualsUsersDto(actualDto, expectedDto);
		}
	}
	
	/**
	 * Unit Test of function userService.getAllInactiveUsers
	 * @param logins logins of users, which status we'll change to inactive for test
	 */
	@Test(dataProvider="providerGetAllUsers", dependsOnMethods = { "getUserDtoList" })
	public void getAllInactiveUsers(List<String> logins) {
		// prepare test data
		for(String login: logins){
			userService.findUserByLogin(login).setStatus(UserStatus.INACTIVE);
			userService.findUserByLogin(login).setRole(new Role(RoleType.USER,"description"));//inactive users must have ROLE_USER
		}
		List<UserDTO> expected = new ArrayList<UserDTO>();
		for(User user: fakeUserRepository){
			if(user.getStatus() == UserStatus.INACTIVE){
				UserDTO userDto = createUserDto(user.getLogin());
				expected.add(userDto);
			}	
		}
		// test action
		List<UserDTO> actual= userService.getAllInactiveUsers();
		Assert.assertEquals(actual.size(), expected.size(), "Bad size of List<UserDTO>");
		for (UserDTO actualDto : actual) {
			for (UserDTO expectedDto : expected)
				if (actualDto.getLogin() == expectedDto.getLogin())
					assertEqualsUsersDto(actualDto, expectedDto);
		}
	}
	
	/**
	 * Unit Test of function userService.checkUsernameNotExistInDB
	 * @param login user name
	 * @param isExist is user exist in database
	 */
	@Test(dataProvider="providerGetUserDto")
	public void checkUsernameNotExistInDB(String login, boolean isExist) {
		Assert.assertEquals(userService.checkUsernameNotExistInDB(login),!isExist,"");
	}
	
	/**
	 * Unit Test of function userService.getUserBySearchTag
	 * @param registrator user with registrator role
	 * @param searchTag part or full match of user last name in same TC
	 */
	@Test(dataProvider="providerGetUserBySearchTag", dependsOnMethods = { "getUserByLogin" })
	public void getUserBySearchTag(User registrator, String searchTag) {
		// prepare expected
		List<UserDTO> expected = new ArrayList<UserDTO>();
		for(User user: fakeUserRepository)
			if(user.getTerritorialCommunity().getName()==registrator.getTerritorialCommunity().getName())
				if(user.getLastName().contains(searchTag))
					expected.add(createUserDto(user.getLogin()));
		// method that we test need security authentication of registrator
		SecurityContextHolder.getContext()
				.setAuthentication(new UsernamePasswordAuthenticationToken(registrator.getLogin(),registrator.getPassword()));
		// test action
		List<UserDTO> actual = userService.getUserBySearchTag(searchTag);
		Assert.assertEquals(actual.size(), expected.size(), "Different sizes of actual and expected");
		for(UserDTO actualDto: actual){
			for(UserDTO expectedDto: expected){
				if(actualDto.getLogin()==expectedDto.getLogin()){
					//not full DTO is filled
					Assert.assertEquals(actualDto.getFirstName(), expectedDto.getFirstName(), "Different Firstname");
					Assert.assertEquals(actualDto.getLastName(), expectedDto.getLastName(), "Different Lastname");
					Assert.assertEquals(actualDto.getMiddleName(), expectedDto.getMiddleName(), "Different Middlename");
					expected.remove(expectedDto);
					break;
				}
			}
		}
		Assert.assertEquals(expected.isEmpty(), true);
	}
	
	/**
	 * Unit Test of function userService.findUserByEmail
	 * @param expected expected User return value
	 * @param login user name
	 * @param isPositive is expected test result positive
	 */
	@Test(dataProvider="providerGetUserByEmail")
	public void findUserByEmail(User expected, String login, boolean isPositive) {
		User actual = userService.findUserByEmail(login);
		if(isPositive)
			assertEqualsUsers(actual, expected);
		else
			Assert.assertEquals(actual, expected);
	}

	/**
	 * Unit Test of function userService.updateFailAttempts
	 */
	@Test
	public void updateFailAttempts() {
		// test data
		User user = fakeUserRepository.get(0);
		int expected = user.getAttempts()+1;
		// 1st test action
		userService.updateFailAttempts(user.getLogin());
		Assert.assertEquals(user.getAttempts(), expected);
		// 2nd test action
		expected = 3;
		user.setAttempts(expected-1);
		userService.updateFailAttempts(user.getLogin());
		Assert.assertEquals(user.getAttempts(), expected);
	}
	
	/**
	 * Unit Test of function userService.resetFailAttempts
	 */
	@Test(dataProvider="providerResetFailAttempts")
	public void resetFailAttempts(String login, int attempts) {
		// test data, we will change data directly in fake repo
		User testUser = null;
		for(User user: fakeUserRepository){
			if(login==user.getLogin()){
				testUser = user;
				break;
			}
		}
		testUser.setAttempts(2);
		int expected = 0;
		// test action
		userService.resetFailAttempts(login);
		Assert.assertEquals(testUser.getAttempts(), expected);
	}

	/**
	 * Unit Test of function userService.resetAllFailAttempts
	 */
	@Test
	public void resetAllFailAttempts() {
		// test data
		final int expected = 0;
		for(int i=0;i<fakeUserRepository.size();i++)
			fakeUserRepository.get(i).setAttempts(i);
		// test action
		userService.resetAllFailAttempts();
		for(int i=0;i<fakeUserRepository.size();i++)
			Assert.assertEquals(fakeUserRepository.get(i).getAttempts(), expected, String.format("Fail attempts not reseted in user '%s'", fakeUserRepository.get(i).getLogin()));
	}

	/**
	 * Unit Test of function userService.resetAllFailAttempts
	 */
	@Test
	public void registerUser() {
		// test data
		UserRegistrationDTO registrationForm = new UserRegistrationDTO();
		registrationForm.setAddress(new AddressDTO("06060","Львівська","Галицький","Львів","Вітовського",
        		"55","22"));
		registrationForm.setConfirmPassword("pikaso");
		registrationForm.setDateOfAccession(Calendar.getInstance().getTime());
		registrationForm.setEmail("pikaso@gmail.com");
		registrationForm.setFirstName("Іван");
		registrationForm.setLastName("Сидор");
		registrationForm.setLogin("pikaso");
		registrationForm.setMiddleName("Сергійович");
		registrationForm.setPassport(new PassportDTO("AA","00600","Народом України"));
		registrationForm.setPassword("pikaso");
		registrationForm.setPhoneNumber("0950000000");
		registrationForm.setTerritorialCommunity("Україна");
		// test action
		boolean isPassportOk=false, isAddressOk = false;
		userService.registerUser(registrationForm);
		for(User user : fakeUserRepository){
			if(user.getLogin()==registrationForm.getLogin()){
				Assert.assertEquals(user.getLogin(), registrationForm.getLogin());
				Assert.assertEquals(user.getEmail(), registrationForm.getEmail());
				Assert.assertEquals(user.getFirstName(), registrationForm.getFirstName());
				Assert.assertEquals(user.getLastName(), registrationForm.getLastName());
				Assert.assertEquals(user.getMiddleName(), registrationForm.getMiddleName());
				Assert.assertEquals(user.getPhoneNumber(), registrationForm.getPhoneNumber());
				Assert.assertEquals(user.getTerritorialCommunity().getName(), registrationForm.getTerritorialCommunity());
				for(Address address: fakeAddressRepository){
					if(address.getUser().getLogin()==user.getLogin()){
						Assert.assertEquals(address.getBuilding(), registrationForm.getAddress().getBuilding());
						Assert.assertEquals(address.getCity(), registrationForm.getAddress().getCity());
						Assert.assertEquals(address.getDistrict(), registrationForm.getAddress().getDistrict());
						Assert.assertEquals(address.getFlat(), registrationForm.getAddress().getFlat());
						Assert.assertEquals(address.getPostCode(), registrationForm.getAddress().getPostcode());
						Assert.assertEquals(address.getRegion(), registrationForm.getAddress().getRegion());
						Assert.assertEquals(address.getStreet(), registrationForm.getAddress().getStreet());
						isAddressOk=true;
						break;
					}
				}
				for(PassportInfo passport: fakePassportRepository){
					if(passport.getUser().getLogin()==user.getLogin()){
						Assert.assertEquals(passport.getNumber(), registrationForm.getPassport().getNumber());
						Assert.assertEquals(passport.getPublishedByData(), registrationForm.getPassport().getPublished_by_data());
						Assert.assertEquals(passport.getSeria(), registrationForm.getPassport().getSeria());
						isPassportOk=true;
						break;
					}
				}
				break;
			}
		}
		Assert.assertTrue(isAddressOk,"Address not saved");
		Assert.assertTrue(isPassportOk,"Passport not saved");
	}


	/**
	 * Unit Test of function userService.CreateTomeAndRecourceNumber
	 */
	@Test
	public void CreateTomeAndRecourceNumber() {
		// test data
		UserDTO userDto = new UserDTO(fakeUserRepository.get(0).getFirstName(), fakeUserRepository.get(0).getLastName(), fakeUserRepository.get(0).getMiddleName(),
				fakeUserRepository.get(0).getRole().getType().toString(), fakeUserRepository.get(0).getLogin(), fakeUserRepository.get(0).getEmail(), fakeUserRepository.get(0).getStatus().toString(),
				new AddressDTO(fakeUserRepository.get(0).getAddress().get(fakeUserRepository.get(0).getAddress().size() - 1).getPostCode(),
						fakeUserRepository.get(0).getAddress().get(fakeUserRepository.get(0).getAddress().size() - 1).getRegion(),
						fakeUserRepository.get(0).getAddress().get(fakeUserRepository.get(0).getAddress().size() - 1).getDistrict(),
						fakeUserRepository.get(0).getAddress().get(fakeUserRepository.get(0).getAddress().size() - 1).getCity(),
						fakeUserRepository.get(0).getAddress().get(fakeUserRepository.get(0).getAddress().size() - 1).getStreet(),
						fakeUserRepository.get(0).getAddress().get(fakeUserRepository.get(0).getAddress().size() - 1).getBuilding(),
						fakeUserRepository.get(0).getAddress().get(fakeUserRepository.get(0).getAddress().size() - 1).getFlat()),
				new PassportDTO(fakeUserRepository.get(0).getPassport().get(fakeUserRepository.get(0).getPassport().size() - 1).getSeria(),
						fakeUserRepository.get(0).getPassport().get(fakeUserRepository.get(0).getPassport().size() - 1).getNumber(),
						fakeUserRepository.get(0).getPassport().get(fakeUserRepository.get(0).getPassport().size() - 1).getPublishedByData()));
		final String expected = "1";
		final String expectedLogin = fakeUserRepository.get(0).getLogin();
		ResourceNumberJson resourceNumberDtoJson = new ResourceNumberJson(expected, expected, expected);
		resourceNumberDtoJson.setLogin(expectedLogin);
		resourceNumberDtoJson = Mockito.spy(resourceNumberDtoJson);
		userDto.setResourceNumberJson(resourceNumberDtoJson);
		// test action
		userService.createTomeAndRecourceNumber(userDto);// entered
		Assert.assertNotNull(fakeTomeRepository.get(0));
		Assert.assertEquals(fakeTomeRepository.get(0).getIdentifier(), expected);
		Assert.assertEquals(fakeTomeRepository.get(0).getRegistrator().getLogin(), expectedLogin);
		/*
		fakeTomeRepository.clear();
		userDto.setResourceNumberDTOJSON(null);
		userService.CreateTomeAndRecourceNumber(userDto);// exception
		Assert.assertTrue(fakeTomeRepository.isEmpty());
		*/
	}


	
	/**
	 * Data for userService.login
	 */
	@DataProvider
	public static Object[][] providerLogin() {
        return new Object[][] { 
        	{ "user", "user", true } ,
        	{ null, null, false } ,
        	{ "admin","somepass", false } ,
        	{ "badlogin","registrator", false }
        	};
    }
	
	/**
	 * Data for userService.getUserByLogin
	 *  and userService.findUserByLogin
	 */
	@DataProvider
	public static Object[][] providerGetUserByLogin() {
		//prepare expected users
		User user = new User();
		TerritorialCommunity communityUkraine = new TerritorialCommunity();
		communityUkraine.setName("Україна");
		user = new User("user", "user", new Role(RoleType.USER, "description"), "Василь", "Труба", "Георгійович",
				"vasul.tryba@gmail.com", UserStatus.ACTIVE.toString(), "09800000002");
		user.setTerritorialCommunity(communityUkraine);
		user.setAddress(Arrays
				.asList(new Address(user, "00000", "Львівська", "Галицький", "Львів", "Вітовського", "48", "31")));
		user.setPassport(Arrays.asList(new PassportInfo(user, "AA", "00000", "Народом України")));
		user.setUserId(1);
        return new Object[][] {     
                 {"user", user,true}, {"wrong",null,false} , {null,null,false}        
           };
    }
	
	/**
	 * Data for userService.changeUserStatus
	 */
	@DataProvider
	public static Object[][] providerChangeUserStatus() {
        return new Object[][] {     
                 { "user", UserStatus.BLOCK }, 
                 { "user", UserStatus.INACTIVE },
                 { "user", UserStatus.ACTIVE }, 
                 { "user", UserStatus.INACTIVE },
                 { "user", UserStatus.BLOCK },
                 { "user", UserStatus.ACTIVE },
           };
    }
	
	/**
	 * Data for userService.changeUserRole
	 */
	@DataProvider
	public static Object[][] providerChangeUserRole() {
        return new Object[][] {     
        	//Role: user
            { "user", RoleType.ADMIN },
            { "user", RoleType.REGISTRATOR },
            { "user", RoleType.COMMISSIONER },
            { "user", RoleType.USER }
           };
    }
	
	/**
	 * Data for userService.getAllInactiveUsers
	 *  and userService.getAllRegisteredUsers
	 */
	@DataProvider
	public static Object[][] providerGetAllUsers() {
        return new Object[][] {     
            { Arrays.asList("user") }, 
            { Arrays.asList("user","admin") }, 
            { Arrays.asList("user","admin","registrator") }, 
            { Arrays.asList("user","admin","registrator","commissioner") }
           };
    }
	
	/**
	 * Data for userService.getUserDto
	 */
	@DataProvider
	public static Object[][] providerGetUserDto() {
        return new Object[][] {     
        	//Role: user
            { "user", true}, {"admin", true}, {"registrator", true}, {"commissioner", true}, {null,false}, {"login",false}
           };
    }
	
	/**
	 * Data for userService.getUserBySearchTag
	 */
	@DataProvider
	public static Object[][] providerGetUserBySearchTag() {
		//prepare expected users
		TerritorialCommunity communityUkraine = new TerritorialCommunity();
		communityUkraine.setName("Україна");
		User registrator = new User("registrator", "registrator", new Role(RoleType.REGISTRATOR, "description"), "Володимир",
				"Сидор", "Тарасович", "sydor@gmail.com", UserStatus.ACTIVE.toString(), "09500000003");//we need only login&pass&role
		registrator.setTerritorialCommunity(communityUkraine);//and community

		return new Object[][] { {registrator, "Труба"}, {registrator, "Нема"} };
    }
	
	/**
	 * Data for userService.getUserByEmail
	 */
	@DataProvider
	public static Object[][] providerGetUserByEmail() {
		//prepare expected users
		User user = new User();
		TerritorialCommunity communityUkraine = new TerritorialCommunity();
		communityUkraine.setName("Україна");
		user = new User("user", "user", new Role(RoleType.USER, "description"), "Василь", "Труба", "Георгійович",
				"vasul.tryba@gmail.com", UserStatus.ACTIVE.toString(), "09800000002");
		user.setTerritorialCommunity(communityUkraine);
		user.setAddress(Arrays
				.asList(new Address(user, "00000", "Львівська", "Галицький", "Львів", "Вітовського", "48", "31")));
		user.setPassport(Arrays.asList(new PassportInfo(user, "AA", "00000", "Народом України")));
		user.setUserId(1);

		return new Object[][] { {user,user.getEmail(), true}, {null, "notmy@gmail.com", false} };
    }
	
	/**
	 * Data for userService.resetFailAttempts;
	 */
	@DataProvider
	public static Object[][] providerResetFailAttempts() {
		return new Object[][] { 
				{"user",1}, {"admin",2}, 
				{"registrator",0}, {"commissioner",3}, 
			};
    }
	
	/**
	 * TESTNG Compare of two Users objects 
	 * @param actual actual user object
	 * @param expected expected user object
	 */
	private void assertEqualsUsers(User actual, User expected) {
		Assert.assertEquals(actual.getLogin(), expected.getLogin(), "Different login");
		Assert.assertEquals(actual.getEmail(), expected.getEmail(), "Different email");
		Assert.assertEquals(actual.getFirstName(), expected.getFirstName(), "Different Firstname");
		Assert.assertEquals(actual.getLastName(), expected.getLastName(), "Different Lastname");
		Assert.assertEquals(actual.getMiddleName(), expected.getMiddleName(), "Different Middlename");
		Assert.assertEquals(actual.getPhoneNumber(), expected.getPhoneNumber(), "Different phone");
		Assert.assertEquals(actual.getRole().toString(), expected.getRole().toString(), "Different role");
		Assert.assertEquals(actual.getStatus(), expected.getStatus(), "Different status");
		Assert.assertEquals(actual.getTerritorialCommunity().getName(), expected.getTerritorialCommunity().getName(), "Different community");
		Assert.assertEquals(actual.getAddress().size(), expected.getAddress().size(), "Different addresses count");
		for(int i=0;i<actual.getAddress().size();i++){
			Assert.assertEquals(actual.getAddress().get(i).getBuilding(), expected.getAddress().get(i).getBuilding(), "Different building");
			Assert.assertEquals(actual.getAddress().get(i).getCity(), expected.getAddress().get(i).getCity(), "Different city");
			Assert.assertEquals(actual.getAddress().get(i).getDistrict(), expected.getAddress().get(i).getDistrict(), "Different disrict");
			Assert.assertEquals(actual.getAddress().get(i).getFlat(), expected.getAddress().get(i).getFlat(), "Different flat");
			Assert.assertEquals(actual.getAddress().get(i).getPostCode(), expected.getAddress().get(i).getPostCode(), "Different postcode");
			Assert.assertEquals(actual.getAddress().get(i).getRegion(), expected.getAddress().get(i).getRegion(), "Different region");
			Assert.assertEquals(actual.getAddress().get(i).getStreet(), expected.getAddress().get(i).getStreet(), "Different street");
		}
		Assert.assertEquals(actual.getPassport().size(), expected.getPassport().size(), "Different passports count");
		for(int i=0;i<actual.getPassport().size();i++){
			Assert.assertEquals(actual.getPassport().get(0).getNumber(), expected.getPassport().get(0).getNumber(), "Different passport number");
			Assert.assertEquals(actual.getPassport().get(0).getPublishedByData(), expected.getPassport().get(0).getPublishedByData(), "Different passport published data");
			Assert.assertEquals(actual.getPassport().get(0).getSeria(), expected.getPassport().get(0).getSeria(), "Different passport seria");
			Assert.assertEquals(actual.getPassport().get(0).getComment(), expected.getPassport().get(0).getComment(), "Different passport comment");
		}
	}
	
	/**
	 * TESTNG Compare of two UserDTOs objects 
	 * @param actual actual userDto object
	 * @param expected expected userDto object
	 */
	private void assertEqualsUsersDto(UserDTO actual, UserDTO expected) {
		Assert.assertEquals(actual.getLogin(), expected.getLogin());
		Assert.assertEquals(actual.getEmail(), expected.getEmail());
		Assert.assertEquals(actual.getFirstName(), expected.getFirstName());
		Assert.assertEquals(actual.getLastName(), expected.getLastName());
		Assert.assertEquals(actual.getMiddleName(), expected.getMiddleName());
		Assert.assertEquals(actual.getPhoneNumber(), expected.getPhoneNumber());
		Assert.assertEquals(actual.getRole(), expected.getRole());
		Assert.assertEquals(actual.getStatus(), expected.getStatus());
		Assert.assertEquals(actual.getTerritorialCommunity(), expected.getTerritorialCommunity());
		Assert.assertEquals(actual.getAddress().getBuilding(), expected.getAddress().getBuilding());
		Assert.assertEquals(actual.getAddress().getCity(), expected.getAddress().getCity());
		Assert.assertEquals(actual.getAddress().getDistrict(), expected.getAddress().getDistrict());
		Assert.assertEquals(actual.getAddress().getFlat(), expected.getAddress().getFlat());
		Assert.assertEquals(actual.getAddress().getPostcode(), expected.getAddress().getPostcode());
		Assert.assertEquals(actual.getAddress().getRegion(), expected.getAddress().getRegion());
		Assert.assertEquals(actual.getAddress().getStreet(), expected.getAddress().getStreet());
		Assert.assertEquals(actual.getPassport().getNumber(), expected.getPassport().getNumber());
		Assert.assertEquals(actual.getPassport().getPublished_by_data(), expected.getPassport().getPublished_by_data());
		Assert.assertEquals(actual.getPassport().getSeria(), expected.getPassport().getSeria());
	}

	/**
	 * Create UserDTO from user.login
	 * @param login user name
	 * @return userDTO, else null if user with 'login' don't exist
	 */
	private UserDTO createUserDto(String login){
		for(User user: fakeUserRepository){
			if(user.getLogin()==login){
				Address address = user.getAddress().get(user.getAddress().size()-1);
				PassportInfo passport = user.getPassport().get(user.getPassport().size()-1);
				AddressDTO addressDto = new AddressDTO(address.getPostCode(), address.getRegion(), address.getDistrict(), address.getCity(), address.getStreet(), address.getBuilding(), address.getFlat());
				PassportDTO passportDto = new PassportDTO(passport.getSeria(),passport.getNumber(),passport.getPublishedByData());;
				UserDTO userDto = new UserDTO(user.getFirstName(), user.getLastName(), user.getMiddleName(), user.getRole().toString(), user.getLogin(), user.getEmail(), user.getStatus().toString(), addressDto, passportDto);
				userDto.setTerritorialCommunity(user.getTerritorialCommunity().getName());
				return userDto;
			}
		}
		return null;
	}


}

