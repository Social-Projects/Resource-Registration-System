package org.registrator.community.service;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.registrator.community.dao.RoleRepository;
import org.registrator.community.entity.Role;
import org.registrator.community.enumeration.RoleType;
import org.registrator.community.service.impl.RoleServiceImpl;

public class RoleServiceTest {
	@InjectMocks
	private RoleService roleService = new RoleServiceImpl();
	@Mock
	private RoleRepository roleRepository;
	
	@BeforeMethod
	public void beforeMethod(){
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void getAllRole() {
		List<Role> fakeRoleRepo = new ArrayList<Role>();
		fakeRoleRepo.addAll(0, Arrays.asList(new Role(RoleType.USER,"user"),
				new Role(RoleType.ADMIN,"admin"),
				new Role(RoleType.REGISTRATOR,"registrator"),
				new Role(RoleType.COMMISSIONER,"commissioner")));
		Mockito.when(roleRepository.findAll()).thenReturn(fakeRoleRepo);
		List<Role> actual = new ArrayList<Role>();
		actual.addAll(roleService.getAllRole());
		Assert.assertTrue(actual.containsAll(fakeRoleRepo) && fakeRoleRepo.containsAll(actual));
	}
}
