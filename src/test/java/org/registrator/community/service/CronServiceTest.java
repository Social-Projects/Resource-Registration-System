package org.registrator.community.service;

import org.testng.annotations.Test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.registrator.community.dao.UserRepository;
import org.slf4j.Logger;
import org.registrator.community.entity.Address;
import org.registrator.community.entity.PassportInfo;
import org.registrator.community.entity.Role;
import org.registrator.community.entity.TerritorialCommunity;
import org.registrator.community.entity.User;
import org.registrator.community.enumeration.RoleType;
import org.registrator.community.enumeration.UserStatus;
import org.registrator.community.service.impl.CronService;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;

public class CronServiceTest {
	@InjectMocks
	private CronService cronService = new CronService();
	@Mock
	private UserRepository userRepository;
	@Mock
	private Logger logger;
	
	@BeforeMethod
	public void beforeMethod() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void checkAllFailAttempts() {
		User user = new User("user", "user", new Role(RoleType.USER, "description"), "Василь", "Труба", "Георгійович",
				"vasul.tryba@gmail.com", UserStatus.ACTIVE.toString(), "09800000002");
		user.setLastModified(new Timestamp(300001));
		user.setAttempts(2);
		user.setAccountNonLocked(0);
		
		List<User> fakeUserRepo = new ArrayList<User>();
		fakeUserRepo.add(user);
		Mockito.when(userRepository.findAll()).thenReturn(fakeUserRepo);
		
		cronService.checkAllFailAttempts();
		Assert.assertEquals(user.getAccountNonLocked(), 1);
		Assert.assertEquals(user.getAttempts(), 0);
	}
}
