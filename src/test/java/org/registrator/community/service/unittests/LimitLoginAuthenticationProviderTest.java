package org.registrator.community.service.unittests;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.support.membermodification.MemberModifier;
import org.registrator.community.entity.User;
import org.registrator.community.enumeration.RoleType;
import org.registrator.community.service.UserService;
import org.registrator.community.service.impl.LimitLoginAuthenticationProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LimitLoginAuthenticationProviderTest {
	@Mock
	UserService userService;

	@Mock
	private PasswordEncoder userPasswordEncoder;

	@InjectMocks
	private DaoAuthenticationProvider authenticationProviderService = new LimitLoginAuthenticationProvider();

	private Logger logger = LoggerFactory.getLogger(authenticationProviderService.getClass());
	private List<User> userList = new ArrayList<User>();
	private static final int DESIRED_RESOURCES = 10;

	// Supplementary classes, needed to initialize the DaoAuthenticationProvider
	// mechanics

	private class BlobUserDetails implements UserDetails {
		private static final long serialVersionUID = 770764066013837287L;
		private String username;
		private String password;

		public BlobUserDetails(String username, String password) {
			this.username = username;
			this.password = password;
		}

		@Override
		public Collection<? extends GrantedAuthority> getAuthorities() {
			List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
			list.add(new SimpleGrantedAuthority(RoleType.USER.toString()));
			return list;
		}

		@Override
		public String getPassword() {
			return password;
		}

		@Override
		public String getUsername() {
			System.out.println("getUser: " + username);
			return username;
		}

		@Override
		public boolean isAccountNonExpired() {
			return true;
		}

		@Override
		public boolean isAccountNonLocked() {
			return true;
		}

		@Override
		public boolean isCredentialsNonExpired() {
			return true;
		}

		@Override
		public boolean isEnabled() {
			return true;
		}
	}

	private UserDetailsService userDetailsService = new UserDetailsService() {
		@Override
		public UserDetails loadUserByUsername(String username)
				throws UsernameNotFoundException {
			User user = userService.findUserByLogin(username);
			
			if (user == null) {
				throw new UsernameNotFoundException("User with name \""
						+ username + "\" not found");
			}

			UserDetails userDetails = new BlobUserDetails(user.getLogin(), user.getPassword());
			return userDetails;
		}
	};

	// BeforeClass preparations
	
	@BeforeClass
	public void bindMocks() {
		logger.debug("Performing InjectMock operations");
		MockitoAnnotations.initMocks(this);
		
		try {
			MemberModifier.field(authenticationProviderService.getClass(), "logger").set(
					authenticationProviderService, logger);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@BeforeClass
	public void prepatePasswordEncoder() {
		logger.debug("Preparing user password encoder emulation");
		Mockito.when(userPasswordEncoder.encode(Mockito.anyString())).then(
				new Answer<String>() {
					public String answer(InvocationOnMock invo) {
						String arg0 = invo.getArgumentAt(0, String.class);
						return arg0;
					}
				});

		Mockito.when(
				userPasswordEncoder.matches(Mockito.anyString(),
						Mockito.anyString())).then(new Answer<Boolean>() {
			public Boolean answer(InvocationOnMock invo) {
				String arg0 = invo.getArgumentAt(0, String.class), 
					   arg1 = invo.getArgumentAt(1, String.class);
				
				return arg0.equals(arg1);
			}
		});
	}

	@BeforeClass
	public void prepareUserService() {
		logger.debug("Preparing user service emulation");
		Mockito.when(userService.findUserByLogin(Mockito.anyString())).then(
				new Answer<User>() {
					public User answer(InvocationOnMock invo) {
						String userLogin = invo.getArgumentAt(0, String.class);
						User user = null;

						for (User usr : userList) {
							if (usr.getLogin().equals(userLogin)) {
								user = usr;
							}
						}
						return user;
					}
				});
	}

	// DataProvider

	@DataProvider(name = "SprintSecurityAuthenticationGenerator")
	public Object[][] generateAuthentications() {
		logger.debug("Generating authentication data");
		Object[][] tmp = new Object[DESIRED_RESOURCES][];
		String userMask = "userLogin%03d";

		for (int i = 0; i < tmp.length; i++) {
			String password = UUID.randomUUID().toString().substring(0, 15);

			User user = new User();
			user.setLogin(String.format(userMask, i));
			user.setPassword(password);
			user.setAccountNonExpired(1);

			userList.add(user);

			Authentication auth = new UsernamePasswordAuthenticationToken(
					user.getLogin(), user.getPassword());

			tmp[i] = new Object[]{auth};
		}
		return tmp;
	}

	@DataProvider(name = "GeneratorForExpiredAccounts")
	public Object[][] expiredGenerator() {
		logger.debug("Alterind previosly generated data to suite the expired exception throw");
		Object[][] tmp = new Object[userList.size()][];
		for (int i = 0; i < tmp.length; i++) {
			User user = userList.get(i);
			user.setAccountNonExpired(0);

			Authentication auth = new UsernamePasswordAuthenticationToken(
					user.getLogin(), user.getPassword());

			tmp[i] = new Object[]{auth};
		}
		return tmp;
	}

	@DataProvider(name = "GeneratorForBadCredentialAuthentications")
	public Object[][] badCredentialGenerator() {
		logger.debug("Alterind previosly generated data to suite the bad credentials exception throw");
		Object[][] tmp = new Object[userList.size()][];
		for (int i = 0; i < tmp.length; i++) {
			User user = userList.get(i);
			user.setAccountNonExpired(1);

			Authentication auth = new UsernamePasswordAuthenticationToken(
					user.getLogin(), user.getPassword() + "boop");

			tmp[i] = new Object[]{auth};
		}
		return tmp;
	}


	@Test(priority = 1)
	public void testSetUserDetailsService() {
		authenticationProviderService.setUserDetailsService(userDetailsService);

		User testUser = new User();
		testUser.setLogin("loginString");
		testUser.setPassword("passwordString");
		userList.add(testUser);

		Authentication auth = new UsernamePasswordAuthenticationToken(
				testUser.getLogin(), testUser.getPassword());

		authenticationProviderService.authenticate(auth);
		userList.remove(testUser);
	}

	@Test(dataProvider = "SprintSecurityAuthenticationGenerator", priority = 2)
	public void testForSuccessfullAuthentication(Authentication auth) {
		authenticationProviderService.authenticate(auth);
	}

	@Test(dataProvider = "GeneratorForExpiredAccounts", priority = 3, expectedExceptions = LockedException.class)
	public void testForAccountExpiredException(Authentication auth) {
		authenticationProviderService.authenticate(auth);
	}

	@Test(dataProvider = "GeneratorForBadCredentialAuthentications", priority = 4, expectedExceptions = BadCredentialsException.class)
	public void testForBadCredentialsException(Authentication auth) {
		authenticationProviderService.authenticate(auth);
	}
}
