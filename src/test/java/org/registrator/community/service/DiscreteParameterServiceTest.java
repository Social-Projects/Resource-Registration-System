package org.registrator.community.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.registrator.community.dao.DiscreteParameterRepository;
import org.registrator.community.entity.DiscreteParameter;
import org.registrator.community.entity.ResourceType;
import org.registrator.community.service.impl.DiscreteParameterServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class DiscreteParameterServiceTest {


	// test data
	private final Integer ID = 1;
	private final String TYPENAME = "TestTypeName";
	private final String DESCRIPTION = "TEST_Description";
	private final String UNITNAME = "TEST_UnitName";
	private ResourceType rt;
	private DiscreteParameter dp;
	// end test data

	@InjectMocks
	private DiscreteParameterService discreteParameterService = new DiscreteParameterServiceImpl();

	@Mock
	private DiscreteParameterRepository discreteParameterRepository;

	@BeforeMethod
	public void init() {
		MockitoAnnotations.initMocks(this);
		// init test data
		rt = new ResourceType(TYPENAME);
		rt.setTypeId(ID);
		dp = new DiscreteParameter(rt, DESCRIPTION, UNITNAME);
		dp.setDiscreteParameterId(ID);
	}

	

	@Test
	public void findById() {
		Mockito.when(discreteParameterRepository.findByDiscreteParameterId(ID)).thenReturn(dp);
		Assert.assertEquals(discreteParameterService.findById(ID).getDiscreteParameterId(), ID);
		Assert.assertEquals(discreteParameterService.findById(ID).getResourceType().getTypeId(), ID);
		Assert.assertEquals(discreteParameterService.findById(ID).getResourceType().getTypeName(), TYPENAME);
		Assert.assertEquals(discreteParameterService.findById(ID).getDescription(), DESCRIPTION);
		Assert.assertEquals(discreteParameterService.findById(ID).getUnitName(), UNITNAME);
	}

	@Test
	public void findAllByResourceType() {
		Mockito.when(discreteParameterRepository.findAllByResourceType(rt))
				.thenReturn(new ArrayList<DiscreteParameter>(Arrays.asList(dp)));
		List<DiscreteParameter> expectedList = discreteParameterService.findAllByResourceType(rt);
		Assert.assertTrue(expectedList.size() == 1);
		DiscreteParameter discreteParameter = expectedList.get(0);
		Assert.assertEquals(discreteParameter.getDiscreteParameterId(), ID);
		Assert.assertEquals(discreteParameter.getResourceType().getTypeId(), ID);
		Assert.assertEquals(discreteParameter.getResourceType().getTypeName(), TYPENAME);
		Assert.assertEquals(discreteParameter.getDescription(), DESCRIPTION);
		Assert.assertEquals(discreteParameter.getUnitName(), UNITNAME);
	}

}
