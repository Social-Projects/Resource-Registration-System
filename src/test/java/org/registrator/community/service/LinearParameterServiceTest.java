package org.registrator.community.service;
	
	import org.mockito.InjectMocks;
	import org.mockito.Mock;
	import org.mockito.Mockito;
	import org.mockito.MockitoAnnotations;
	import org.registrator.community.dao.LinearParameterRepository;
	import org.registrator.community.entity.LinearParameter;
	import org.registrator.community.entity.ResourceType;
	import org.registrator.community.service.LinearParameterService;
	import org.registrator.community.service.impl.LinearParameterServiceImpl;
	import org.slf4j.Logger;
	import org.slf4j.LoggerFactory;
	import org.testng.Assert;
	import org.testng.annotations.AfterClass;
	import org.testng.annotations.BeforeClass;
	import org.testng.annotations.BeforeMethod;
	import org.testng.annotations.Test;
	import java.util.ArrayList;
	import java.util.Arrays;
	
	/**
	 * Created by Orest on 12.03.2016.
	 * Test class for LinearParameterService interface implementation
	 * */
	
	public class LinearParameterServiceTest {
		 
		private Logger testLogger = LoggerFactory.getLogger(LinearParameterServiceTest.class);
		private Integer ID = 111;
		private String uniNAME = "MGG";
		private String deScripto = "Radio";
		LinearParameter lp;
			
	    @InjectMocks
	    private LinearParameterService linearParameterService = new LinearParameterServiceImpl();
	    @Mock
	    private ResourceType type;
	    @Mock
	    private LinearParameterRepository parametrLineRepository;	    
	    @Mock
	    private Logger logger;
	    @BeforeMethod
	    public void init() {
	        MockitoAnnotations.initMocks(this);
	        lp = new LinearParameter();
	        lp.setUnitName(uniNAME);
			lp.setLinearParameterId(ID);
			lp.setDescription(deScripto);
	    }	
		@BeforeClass
		public void startClass(){
				testLogger.info("Begin LinearParameterServiceTest.java");
			}
		@AfterClass
		public void endClass(){
				testLogger.info("End LinearParameterServiceTest");
			}	    
	    @Test
		public void findAllByResourceType(){
			testLogger.info("Start");
			Mockito.when(parametrLineRepository.findByResourceType(type)).thenReturn(new ArrayList<LinearParameter>(Arrays.asList(lp)));
			LinearParameter actualLP = linearParameterService.findAllByResourceType(type).get(0);
			Assert.assertEquals( actualLP.getUnitName(), "MGG");
			Assert.assertEquals( actualLP.getLinearParameterId(), ID);
			Assert.assertEquals( actualLP.getDescription(), "Radio");
			testLogger.info("End");
	    }
	   @Test
		public void findById(){
			testLogger.info("Start");
			Mockito.when(parametrLineRepository.findByLinearParameterId(ID)).thenReturn(lp);
			Assert.assertEquals(parametrLineRepository.findByLinearParameterId(111).getLinearParameterId(), ID);
			LinearParameter actualLP1 = linearParameterService.findById(111);
			Assert.assertEquals( actualLP1.getUnitName(), "MGG");
			Assert.assertEquals( actualLP1.getLinearParameterId(), ID);
			Assert.assertEquals( actualLP1.getDescription(), "Radio");
			testLogger.info("End");
		}
	}
