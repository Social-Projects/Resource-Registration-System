package org.registrator.community.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.registrator.community.config.LoggingConfig;
import org.registrator.community.config.root.SpringRootConfig;
import org.registrator.community.config.root.TestingConfiguration;
import org.registrator.community.dao.InquiryRepository;
import org.registrator.community.entity.Inquiry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Unit test for saving output procuration in the database.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("testing")
@ContextConfiguration(classes={TestingConfiguration.class,LoggingConfig.class,SpringRootConfig.class})
public class InquiryServiceIntegrationTest {
    
    @Autowired
    InquiryService inquiryService;
    @Autowired
    InquiryRepository inquiryRepository;
    
    @Test
     public void addOutputInquiryTest() {
        long linesNumber = inquiryRepository.count();
        inquiryService.addOutputInquiry("111111", "registrator", "user");
        long linesNumberNew = inquiryRepository.count();
        Assert.assertTrue(linesNumberNew - linesNumber == 1);
        
        Inquiry inquiry = inquiryRepository.findOne(1);
        Assert.assertEquals(inquiry.getResource().getIdentifier(), "111111");
        Assert.assertEquals(inquiry.getRegistrator().getLogin(), "registrator");
        Assert.assertEquals(inquiry.getUser().getLogin(), "user");
    }

}