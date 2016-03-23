package org.registrator.community.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.registrator.community.config.LoggingConfig;
import org.registrator.community.config.root.SpringRootConfig;
import org.registrator.community.config.root.TestingConfiguration;
import org.registrator.community.entity.ResourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Unit test for resource type service methods
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("testing")
@ContextConfiguration(classes={TestingConfiguration.class,LoggingConfig.class,SpringRootConfig.class})
public class ResourceTypeServiceTest {

    @Autowired
    private ResourceTypeService resourceTypeService;
    
    @Test
    public void findResourceTypeByName() {  
        ResourceType resourceType = resourceTypeService.findByName("земельний");
        Assert.assertEquals("земельний",resourceType.getTypeName());
        Assert.assertTrue(true);
    }
    @Test
    public void findResourceTypeById() {  
        ResourceType resourceType = resourceTypeService.findById(1);
        Assert.assertEquals(new Integer(1),resourceType.getTypeId());
        Assert.assertTrue(true);
    }
}
