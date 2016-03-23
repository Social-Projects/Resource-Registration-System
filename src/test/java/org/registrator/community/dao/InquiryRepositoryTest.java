package org.registrator.community.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.registrator.community.config.root.TestingConfiguration;
import org.registrator.community.entity.Inquiry;
import org.registrator.community.entity.User;
import org.registrator.community.enumeration.InquiryType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("testing")
@ContextConfiguration(classes={TestingConfiguration.class})
public class InquiryRepositoryTest {
	
	@Autowired
	InquiryRepository inquiryRepository;
	@Autowired
	UserRepository userRepository;
	
	// test method getOne - get inquiry by inquiryId
	@Test
    public void testGetOne(){
		Inquiry inquiry = inquiryRepository.findOne(1);
		Assert.assertTrue(inquiry.getInquiryId() == 1);
	}
	
	// test method getOne - get inquiry by inquiryId that does not exist
		@Test
	    public void testGetOneFail(){
			Inquiry inquiry = inquiryRepository.findOne(-1);
			Assert.assertNull(inquiry);
		}
	
		// test method findByUserAndInquiryType - get inquiry by User and inquiryType
		@Test
		@Ignore
	    public void testFindByUserAndInquiryType(){
			User user = userRepository.findUserByLogin("user");
			List<Inquiry> inquiryList = inquiryRepository.findByUserAndInquiryType(user, InquiryType.OUTPUT); 
			Assert.assertTrue(inquiryList.size() == 2);						
		}
	

}
