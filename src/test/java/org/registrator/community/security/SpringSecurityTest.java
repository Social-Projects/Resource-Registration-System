package org.registrator.community.security;

import com.itextpdf.text.DocumentException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.junit.runner.RunWith;
import org.registrator.community.config.LoggingConfig;
import org.registrator.community.config.root.SpringRootConfig;
import org.registrator.community.config.root.TestingConfiguration;
import org.registrator.community.service.PrintService;
import org.registrator.community.service.ResourceService;
import org.registrator.community.service.ResourceTypeService;
import org.registrator.community.service.UserService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("testing")
@ContextConfiguration(classes={TestingConfiguration.class,LoggingConfig.class,SpringRootConfig.class})
public class SpringSecurityTest {


	@Autowired
    private Logger logger;
	
	@Autowired
	public UserService userService;

	@Autowired
	public UserDetailsService userDetailsService;

	@Autowired
	public ResourceService resourceService;

	@Autowired
	ResourceTypeService resourceTypeService;

	@Autowired
	PrintService printService;

	@Rule
    public	TestWatcher testWatcher = new TestWatcher() {

		protected void succeeded(org.junit.runner.Description description) {
			logger.info(description.getMethodName());
			System.out.println("<<SUCCESS>> - " + description.getMethodName());
		};

		protected void failed(Throwable e, org.junit.runner.Description description) {
			logger.error(description.getMethodName());
			System.out.println("<<FAILED>> - " + description.getMethodName());
		};

	};

	@Test
	@WithMockUser(roles = { "ADMIN", "REGISTRATOR" })
	public void testOne() {
		userService.getAllInactiveUsers();
	}

	@Test
	@WithMockUser(roles = { "ADMIN", "REGISTRATOR" })
	public void testTwo() {
		userService.getAllRegistratedUsers();
	}

	@Test
	@WithMockUser(roles = { "ADMIN", "REGISTRATOR" })
	public void testThree() {
		resourceService.count();
	}

	@Test
	@WithMockUser(roles = {"REGISTRATOR" })
	public void testFour() {
		resourceTypeService.findAll();
	}

	@Test
	@WithMockUser(roles = { "USER", "REGISTRATOR" })
	public void testFive() throws IOException, DocumentException {
		printService.printProcuration(1);

	}

	@Test
	@WithMockUser(roles = { "REGISTRATOR" })
	public void testSix() throws IOException, DocumentException {
		printService.printExtract(1);
	}

	@Test
	@WithMockUser(roles = { "USER" })
	public void testSeven() throws IOException, DocumentException {
		printService.printProcurationOnSubmitInfo(2);
	}

}

