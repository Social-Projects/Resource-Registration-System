package org.registrator.community.service;

import org.mockito.MockitoAnnotations;
import org.powermock.api.support.membermodification.MemberModifier;
import org.slf4j.LoggerFactory;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.registrator.community.dao.ResourceRepository;
import org.registrator.community.entity.Resource;
import org.slf4j.Logger;
import org.registrator.community.service.impl.ResourceDeleteServiceImpl;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class ResourceDeleteServiceTest {

    private static final String ID = "id";

    @InjectMocks
    private ResourceDeleteService resourceDeleteService = new ResourceDeleteServiceImpl();

    @Mock
    private ResourceRepository resourceRepository;

    private Logger logger;

    @BeforeMethod
    public void init() throws IllegalAccessException{

        MockitoAnnotations.initMocks(this);

        // inject logger into tested service
        logger = LoggerFactory.getLogger("");
        MemberModifier.field(ResourceDeleteServiceImpl.class, "logger").set(resourceDeleteService, logger);

    }

    @Test
    public void testDeleteResourceRetrieveResourceByCorrectIdentifier() throws Exception {

        resourceDeleteService.deleteResource(ID);
        verify(resourceRepository).findByIdentifier(ID);

    }

    @Test
    public void testDeleteResourceDeleteCorrectResource() throws Exception {

        Resource persistedResource = new Resource();
        when(resourceRepository.findByIdentifier(ID)).thenReturn(persistedResource);
        resourceDeleteService.deleteResource(ID);
        verify(resourceRepository).delete(persistedResource);

    }
}
