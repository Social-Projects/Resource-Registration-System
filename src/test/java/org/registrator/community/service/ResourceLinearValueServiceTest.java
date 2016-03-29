package org.registrator.community.service;

import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.registrator.community.dao.LinearParameterRepository;
import org.registrator.community.dao.ResourceLinearValueRepository;
import org.registrator.community.entity.LinearParameter;
import org.registrator.community.entity.Resource;
import org.registrator.community.entity.ResourceLinearValue;
import org.registrator.community.service.impl.ResourceLinearValueServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.*;
import static org.testng.Assert.assertEquals;

/**
 * Test class for ResourceLinearValueService interface implementation
 * */

public class ResourceLinearValueServiceTest{
    @InjectMocks
    private ResourceLinearValueService valueService;

    @Mock
    private Resource resource;

    @Mock
    private LinearParameter parameter;

    @Mock(answer = Answers.RETURNS_SMART_NULLS)
    private ResourceLinearValueRepository valueRepository;

    @Mock(answer = Answers.RETURNS_SMART_NULLS)
    private LinearParameterRepository parameterRepository;

    private ResourceLinearValue resourceValue;

    private Logger logger = LoggerFactory.getLogger("");

    private static final String BEGIN = "begin test";
    private static final String END = "end test";
    private static final double VALUE_SEARCH = 47.0;
    private static final Integer ID_PARAMETER = 47;

    @BeforeMethod
    public void initMocks() throws Exception {
        valueService = new ResourceLinearValueServiceImpl();
        MockitoAnnotations.initMocks(this);

        when(parameterRepository.findByLinearParameterId(ID_PARAMETER)).thenReturn(parameter);

        resourceValue = mock(ResourceLinearValue.class);
    }


    @Test
    public void findByResource() throws Exception {
        
        List<ResourceLinearValue> expected = asList(resourceValue);
        when(valueRepository.findByResource(resource)).thenReturn(expected);

        List<ResourceLinearValue> actual = valueService.findByResource(resource);

        verify(valueRepository, times(1)).findByResource(resource);
        assertEquals(actual, expected);
        
    }

    @Test
    public void findAllByLinearParameter() throws Exception {
        
        List<ResourceLinearValue> expected = asList(resourceValue);
        when(valueRepository.findAllByLinearParameter(parameter)).thenReturn(expected);

        List<ResourceLinearValue> actual = valueService.findAllByLinearParameter(parameter);

        verify(valueRepository, times(1)).findAllByLinearParameter(parameter);
        assertEquals(actual, expected);
        
    }

    @Test
    public void findAllByValueAndLinearParameter() throws Exception {
        
        List<ResourceLinearValue> expected =  asList(resourceValue);
        when(valueRepository.findByValueAndLinearParameterId(VALUE_SEARCH, parameter)).thenReturn(expected);

        List<ResourceLinearValue> actual =
                valueService.findAllByValueAndLinearParameter(VALUE_SEARCH, parameter);
        verify(valueRepository, times(1)).findByValueAndLinearParameterId(VALUE_SEARCH, parameter);
        assertEquals(actual, expected);
        
    }

    @Test
    public void findResourcesbyLinearParam() throws Exception {
        
        String identifier = "test";
        Resource resource = mock(Resource.class);
        when(resource.getIdentifier()).thenReturn(identifier);
        when(resourceValue.getResource()).thenReturn(resource);

        List<ResourceLinearValue> resourceLinearValues =  asList(resourceValue);
        when(valueRepository.findByValueAndLinearParameterId(VALUE_SEARCH, parameter))
                .thenReturn(resourceLinearValues);

        Set<String> expected = new HashSet<>();
        expected.add(identifier);

        Set<String> actual =
            valueService.findResourcesbyLinearParam(ID_PARAMETER, VALUE_SEARCH);
        verify(parameterRepository, times(1)).findByLinearParameterId(ID_PARAMETER);
        assertEquals(actual, expected);
        

    }

    @Test
    public void findResourcesByLinParamList() {
        


        String identifier = "test";
        Resource resource = mock(Resource.class);
        when(resource.getIdentifier()).thenReturn(identifier);
        List<ResourceLinearValue> resourceLinearValues =  asList(resourceValue);
        when(resourceValue.getResource()).thenReturn(resource);

        List<Integer> idParameters = new ArrayList<>();
        List<Double> values = new ArrayList<>();
        int count = 5;

        // create test data for method
        for (int i = 0; i < count; i++) {
            idParameters.add(i);
            values.add(VALUE_SEARCH);

            LinearParameter parameter = mock(LinearParameter.class);
            when(parameterRepository.findByLinearParameterId(i)).thenReturn(parameter);
            when(valueRepository.findByValueAndLinearParameterId(VALUE_SEARCH, parameter))
                    .thenReturn(resourceLinearValues);

        }
        
        //test for null value
        idParameters.add(count);
        values.add(null);

        Set<String> expected = new HashSet<>();
        expected.add(identifier);

        Set<String> actual =
            valueService.findResourcesByLinParamList(idParameters, values);

        assertEquals(actual, expected);

        
    }


}