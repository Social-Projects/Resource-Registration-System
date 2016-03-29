package org.registrator.community.dao;
import static org.junit.Assert.assertTrue;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.registrator.community.config.root.TestingConfiguration;
import org.registrator.community.dao.UserRepository;
import org.registrator.community.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("testing")
@ContextConfiguration(classes={TestingConfiguration.class})
public class UserRepositoryTest {
	
	@Autowired
	UserRepository userRepository;
	
	@Test
    public void testFindUserByLogin() {
		User user = userRepository.findUserByLogin("user");
		System.out.println(user.getPassword());
		
		Assert.assertEquals("Compare user.mail", "ivan@gmail.com",user.getEmail());
    }
}