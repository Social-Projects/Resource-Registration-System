package org.registrator.community.service;

import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.support.membermodification.MemberModifier;
import org.registrator.community.dao.DiscreteParameterRepository;
import org.registrator.community.dao.ResourceDiscreteValueRepository;
import org.registrator.community.entity.DiscreteParameter;
import org.registrator.community.entity.Resource;
import org.registrator.community.entity.ResourceDiscreteValue;
import org.registrator.community.entity.ResourceLinearValue;
import org.registrator.community.service.impl.ResourceDiscreteValueServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Arrays.asList;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;
import static org.testng.Assert.assertEquals;

/**
 * Test class for ResourceDiscreteValueService interface implementation
 * */

public class ResourceDiscreteValueServiceTest {

    @InjectMocks
    private ResourceDiscreteValueService valueService;

    @Mock
    private DiscreteParameter parameter;

    @Mock(answer = Answers.RETURNS_SMART_NULLS)
    private ResourceDiscreteValueRepository valueRepository;

    @Mock(answer = Answers.RETURNS_SMART_NULLS)
    private DiscreteParameterRepository parameterRepository;

    private static final double VALUE_SEARCH = 47.0;
    private static final Integer ID_PARAMETER = 47;

    private Logger logger;
    private ResourceDiscreteValue resourceValue;
    private String resourceIdentifier = "test";
    private Resource resource;

    @BeforeMethod
    public void initMocks() throws Exception {
        valueService = new ResourceDiscreteValueServiceImpl();
        MockitoAnnotations.initMocks(this);

        logger = LoggerFactory.getLogger("");

        when(parameterRepository.findByDiscreteParameterId(ID_PARAMETER)).thenReturn(parameter);

        resourceValue = mock(ResourceDiscreteValue.class);
        resource = mock(Resource.class);
        when(resourceValue.getResource()).thenReturn(resource);
        when(resource.getIdentifier()).thenReturn(resourceIdentifier);
        when(resourceValue.getResource()).thenReturn(resource);

    }


    @Test
    public void findByResource() throws Exception {
        List<ResourceDiscreteValue> expected = asList(resourceValue);
        when(valueRepository.findByResource(resource)).thenReturn(expected);

        List<ResourceDiscreteValue> actual = valueService.findByResource(resource);

        verify(valueRepository, times(1)).findByResource(resource);
        assertEquals(actual, expected);
    }


    @Test
    public void findAllByDiscreteParameter() throws Exception {
        List<ResourceDiscreteValue> expected = asList(resourceValue);
        when(valueRepository.findAllByDiscreteParameter(parameter)).thenReturn(expected);

        List<ResourceDiscreteValue> actual = valueService.findAllByDiscreteParameter(parameter);

        verify(valueRepository, times(1)).findAllByDiscreteParameter(parameter);
        assertEquals(actual, expected);

    }

    @Test
    public void findAllByValueAndDiscreteParameter() throws Exception {
        
        List<ResourceDiscreteValue> expected =  asList(resourceValue);
        when(valueRepository.findAllByValueAndDiscreteParameter(VALUE_SEARCH, parameter)).thenReturn(expected);

        List<ResourceDiscreteValue> actual =
                valueService.findAllByValueAndDiscreteParameter(VALUE_SEARCH, parameter);
        verify(valueRepository, times(1)).findAllByValueAndDiscreteParameter(VALUE_SEARCH, parameter);
        assertEquals(actual, expected);
        
    }

    @Test
    public void findAllByBiggerValueAndDiscreteParameter() throws Exception {
        
        List<ResourceDiscreteValue> expected =  asList(resourceValue);
        when(valueRepository.findAllByBiggerValueAndDiscreteParameter(VALUE_SEARCH, parameter)).thenReturn(expected);

        List<ResourceDiscreteValue> actual =
                valueService.findAllByBiggerValueAndDiscreteParameter(VALUE_SEARCH, parameter);
        verify(valueRepository, times(1)).findAllByBiggerValueAndDiscreteParameter(VALUE_SEARCH, parameter);
        assertEquals(actual, expected);
        
    }

    @Test
    public void findAllBySmallerValueAndDiscreteParameter() throws Exception {
        

        List<ResourceDiscreteValue> expected =  asList(resourceValue);
        when(valueRepository.findAllBySmallerValueAndDiscreteParameter(VALUE_SEARCH, parameter)).thenReturn(expected);

        List<ResourceDiscreteValue> actual =
                valueService.findAllBySmallerValueAndDiscreteParameter(VALUE_SEARCH, parameter);
        verify(valueRepository, times(1)).findAllBySmallerValueAndDiscreteParameter(VALUE_SEARCH, parameter);
        assertEquals(actual, expected);
        
    }


    @Test
    public void findResourcesbyDiscreteParamLess() throws Exception {
        

        List<ResourceDiscreteValue> resourceDiscreteValues =  asList(resourceValue);
        when(valueRepository.findAllBySmallerValueAndDiscreteParameter(VALUE_SEARCH, parameter))
                .thenReturn(resourceDiscreteValues);

        Set<String> expected = new HashSet<>();
        expected.add(resourceIdentifier);

        Set<String> actual =
            valueService.findResourcesByDiscreteParam(ID_PARAMETER, "less", VALUE_SEARCH);

        verify(parameterRepository, times(1)).findByDiscreteParameterId(ID_PARAMETER);
        verify(valueRepository, times(1)).findAllBySmallerValueAndDiscreteParameter(eq(VALUE_SEARCH), any());
        assertEquals(actual, expected);

        
    }

    @Test
    public void findResourcesbyDiscreteParamGreater() throws Exception {
        

        List<ResourceDiscreteValue> resourceDiscreteValues =  asList(resourceValue);
        when(valueRepository.findAllByBiggerValueAndDiscreteParameter(VALUE_SEARCH, parameter))
                .thenReturn(resourceDiscreteValues);

        Set<String> expected = new HashSet<>();
        expected.add(resourceIdentifier);

        Set<String> actual =
                valueService.findResourcesByDiscreteParam(ID_PARAMETER, "greater", VALUE_SEARCH);

        verify(parameterRepository, times(1)).findByDiscreteParameterId(ID_PARAMETER);
        verify(valueRepository, times(1)).findAllByBiggerValueAndDiscreteParameter(eq(VALUE_SEARCH), any());
        assertEquals(actual, expected);

        
    }

    @Test
    public void findResourcesbyDiscreteParamEqual() throws Exception {
        

        List<ResourceDiscreteValue> resourceDiscreteValues =  asList(resourceValue);
        when(valueRepository.findAllByValueAndDiscreteParameter(VALUE_SEARCH, parameter))
                .thenReturn(resourceDiscreteValues);

        Set<String> expected = new HashSet<>();
        expected.add(resourceIdentifier);

        Set<String> actual =
                valueService.findResourcesByDiscreteParam(ID_PARAMETER, "equal", VALUE_SEARCH);

        verify(parameterRepository, times(1)).findByDiscreteParameterId(ID_PARAMETER);
        verify(valueRepository, times(1)).findAllByValueAndDiscreteParameter(eq(VALUE_SEARCH), any());
        assertEquals(actual, expected);

        
    }

    @Test(dataProvider = "findParamsProvider")
    public void findResourcesByParamsList(
    		List<Integer> idList, 
    		List<String> compareSignList, 
    		List<Double> valuesList) throws Exception {
    	
        
        List<ResourceDiscreteValue> resourceDiscreteValues =  asList(resourceValue);
        when(valueRepository.findAllBySmallerValueAndDiscreteParameter(VALUE_SEARCH, parameter))
                .thenReturn(resourceDiscreteValues);
        when(valueRepository.findAllByBiggerValueAndDiscreteParameter(VALUE_SEARCH, parameter))
                .thenReturn(resourceDiscreteValues);
        when(valueRepository.findAllByValueAndDiscreteParameter(VALUE_SEARCH, parameter))
                .thenReturn(resourceDiscreteValues);

        Set<String> expected = new HashSet<>();
        expected.add(resourceIdentifier);

        Set<String> actual = valueService.findResourcesByParamsList(idList, compareSignList, valuesList);
        
        for (int i = 0; i < compareSignList.size(); i++) {
        	String compareSign = compareSignList.get(i);
        	if (valuesList.get(i) != null) {
	        	switch (compareSign) {
	        	case "equal":
	        		verify(valueRepository, atLeast(1)).findAllByValueAndDiscreteParameter(eq(VALUE_SEARCH), any());
	        		break;
	        	case "greater":
	        		verify(valueRepository, atLeast(1)).findAllByBiggerValueAndDiscreteParameter(eq(VALUE_SEARCH), any());
	        		break;
	        	case "less":
	        		verify(valueRepository, atLeast(1)).findAllBySmallerValueAndDiscreteParameter(eq(VALUE_SEARCH), any());
	        		break;
	        	}
        	}
        }
        
        assertEquals(actual, expected);

    }

    @DataProvider(name = "findParamsProvider")
    // creates parameters for findResourcesByParamsList
    public Object[][] findParamsProvider() {
    	int paramsCount = 3;
    	List<Object[]> listParams = new ArrayList<>();
    	
    	//normal flow of program
    	listParams.add(new Object[]{
	    	asList(ID_PARAMETER, ID_PARAMETER, ID_PARAMETER),
	        asList("equal", "greater", "less"),
	        asList(VALUE_SEARCH, VALUE_SEARCH, VALUE_SEARCH)
    	});
    	
    	//null value
    	listParams.add(new Object[]{
	    	asList(ID_PARAMETER, ID_PARAMETER),
	        asList("equal", "greater" ),
	        asList(VALUE_SEARCH, null)
    	});
    	
    	
    	Object[][] result = new Object[listParams.size()][paramsCount];
    	for (int i = 0; i < listParams.size(); i++) {
    		for (int k = 0; k < paramsCount; k++) {
    			result[i][k] = listParams.get(i)[k];
    		}
    	}
    	return result;
    }

}