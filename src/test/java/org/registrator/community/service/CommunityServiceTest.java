package org.registrator.community.service;

import java.util.ArrayList;
import java.util.Arrays;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.support.membermodification.MemberModifier;
import org.registrator.community.dao.CommunityRepository;
import org.registrator.community.dao.UserRepository;
import org.registrator.community.dto.CommunityDTO;
import org.registrator.community.entity.TerritorialCommunity;
import org.registrator.community.entity.User;
import org.registrator.community.service.impl.CommunityServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CommunityServiceTest {
	
	
	
	// test data
	private final Integer ID = 1;
	private final String NAME = "TestName";
	private final String REGISTRATION_NUMBER = "804:14:01:001:79000";
	private TerritorialCommunity tc;
	//end test data
	
	@InjectMocks
	private CommunityService communityService = new CommunityServiceImpl();
	
	@Mock
	private CommunityRepository communityRepository;
	
	@Mock
	private UserRepository userRepository;
	

	@BeforeMethod
    public void init() throws IllegalArgumentException, IllegalAccessException {
        MockitoAnnotations.initMocks(this);
        
        // inject logger into tested service
        Logger logger = LoggerFactory.getLogger("");
        MemberModifier
        	.field(CommunityServiceImpl.class, "logger")
            .set(communityService, logger);
        
        tc = new TerritorialCommunity();
        tc.setName(NAME);
		tc.setTerritorialCommunityId(ID);
		tc.setRegistrationNumber(REGISTRATION_NUMBER);
    }
	
	
	
	@Test
	public void findAll(){
		Mockito.when(communityRepository.findAll()).thenReturn(new ArrayList<TerritorialCommunity>(Arrays.asList(tc)));
		TerritorialCommunity actualTC = communityService.findAll().get(0);
		Assert.assertEquals(actualTC.getName(), NAME);
		Assert.assertEquals(actualTC.getTerritorialCommunityId(), ID);
	}
	
	@Test
	public void findByName(){
		Mockito.when(communityRepository.findByName(NAME)).thenReturn(tc);
		Assert.assertEquals(communityService.findByName(NAME).getName(), NAME);
		Assert.assertEquals(communityService.findByName(NAME).getTerritorialCommunityId(), ID);
	}
	
	@Test
	public void addCommunity(){
		Mockito.when(communityRepository.saveAndFlush(tc)).thenReturn(tc);
		Assert.assertEquals(communityService.addCommunity(tc).getName(), NAME);
		Assert.assertEquals(communityService.addCommunity(tc).getTerritorialCommunityId(), ID);
	}
	
	@Test
	public void findById(){
		Mockito.when(communityRepository.findOne(ID)).thenReturn(tc);
		//Mockito.doReturn(tc).when(communityRepository).findOne(ID);
		Assert.assertEquals(communityService.findById(ID).getName(), NAME);
		Assert.assertEquals(communityService.findById(ID).getTerritorialCommunityId(), ID);
	}
	
	@Test
	public void deleteCommunity() throws IllegalAccessException{
		Assert.assertTrue(communityService.deleteCommunity(tc));                  // try to delete, if success then return true
		Mockito.verify(communityRepository).delete(tc);                           // check whether method have been called
		Mockito.when(userRepository.findByTerritorialCommunity(tc))
		.thenReturn(new ArrayList<User>(Arrays.asList(new User())));              // imitation of constraint
		Assert.assertFalse(communityService.deleteCommunity(tc));                 // try to delete with constraint so method must return false
		Mockito.verifyZeroInteractions(communityRepository);                      // check whether  method "communityRepository.delete" have not been call at least one time
	}
	
	@Test
	public void findAllByAsc(){
		Mockito.when(communityRepository.findAllByAsc()).thenReturn(new ArrayList<TerritorialCommunity>(Arrays.asList(tc)));
		TerritorialCommunity actualTC = communityService.findAllByAsc().get(0);
		Assert.assertEquals(actualTC.getName(), NAME);
		Assert.assertEquals(actualTC.getTerritorialCommunityId(), ID);
	}
	
	@Test
	public void updateCommunity(){
		Mockito.when(communityRepository.findOne(ID)).thenReturn(tc);
		Mockito.when(communityRepository.save(tc)).thenReturn(tc);
		final String newName = "New name";
		final String newRegistrationNumber = "805:15:02:002:79001";
		communityService.updateCommunity(new CommunityDTO(newName, ID, newRegistrationNumber));
		Mockito.verify(communityRepository).save(tc);
		Assert.assertEquals(communityService.findById(ID).getName(), newName);
		Assert.assertEquals(communityService.findById(ID).getTerritorialCommunityId(), ID);
		Assert.assertEquals(communityService.findById(ID).getRegistrationNumber(), newRegistrationNumber);
	}

}
