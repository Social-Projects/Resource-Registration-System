package org.registrator.community.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.registrator.community.config.root.TestingConfiguration;
import org.registrator.community.entity.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.testng.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("testing")
@ContextConfiguration(classes={TestingConfiguration.class})
public class ResourceRepositoryTest {

    @Autowired
    ResourceRepository resourceRepository;
    
    /*check resource by identifier*/
    @Test
    public void findResourceByIdentifierTest() {
        
        Resource resource = resourceRepository.findByIdentifier("111111");
        Assert.assertEquals("111111", resource.getIdentifier());
    }
    
    /*check resource with not existing identifier*/
    @Test
    public void findResourceByIdentifierTestFaild() {
        
        Resource resource = resourceRepository.findByIdentifier("");
        Assert.assertNull(resource, "The resource with empty identifier can't exist");
        }
}
