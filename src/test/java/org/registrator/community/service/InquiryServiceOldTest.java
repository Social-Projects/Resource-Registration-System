package org.registrator.community.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.registrator.community.dao.InquiryRepository;
import org.registrator.community.dao.ResourceRepository;
import org.registrator.community.dao.UserRepository;
import org.registrator.community.dto.InquiryListDTO;
import org.registrator.community.dto.UserNameDTO;
import org.registrator.community.entity.*;
import org.registrator.community.enumeration.InquiryType;
import org.registrator.community.enumeration.ResourceStatus;
import org.registrator.community.enumeration.RoleType;
import org.registrator.community.enumeration.UserStatus;
import org.registrator.community.service.impl.InquiryServiceImpl;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * Unit test for inquiry service methods
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class InquiryServiceOldTest {

    public static final String RESOURCE_ID = "resourceId";
    public static final String REGISTRATOR_LOGIN = "registrator";
    public static final String USER_LOGIN = "user";

    private static final User USER = new User(USER_LOGIN, "", new Role(RoleType.USER, ""), "", "", "", "",
            UserStatus.ACTIVE.toString());
    private static final User REGISTRATOR = new User(REGISTRATOR_LOGIN, "", new Role(RoleType.REGISTRATOR, ""), "", "",
            "", "", UserStatus.ACTIVE.toString());
    private static final Resource RESOURCE = new Resource();

    private static final User[] REGISTRATORS = new User[] {
            new User("login1", "", new Role(RoleType.REGISTRATOR, ""), "firstName1", "lastName1", "middleName1", "",
                    UserStatus.ACTIVE.toString()),
            new User("login2", "", new Role(RoleType.REGISTRATOR, ""), "firstName2", "lastName2", "middleName2", "",
                    UserStatus.ACTIVE.toString()) };

    public static final Inquiry[] INQUIRIES = new Inquiry[] {
            new Inquiry(InquiryType.INPUT.toString(), new Date(), USER, REGISTRATOR,
                    new Resource(null, "resource1", "", USER, new Date(), ResourceStatus.ACTIVE.toString(), null, "")),
            new Inquiry(InquiryType.INPUT.toString(), new Date(), USER, REGISTRATOR, new Resource(null, "resource2", "",
                    USER, new Date(), ResourceStatus.ACTIVE.toString(), null, "")) };

    @Autowired
    InquiryService inquiryService;

    @Autowired
    InquiryRepository inquiryRepository;

    @Autowired
    UserRepository userRepository;
    
    @Autowired
    ResourceRepository resourceRepository;

    @Before
    public void setup() {
        reset(userRepository, inquiryRepository, resourceRepository);

        mockUserRepository();
        mockInquiryRepository();
        mockResourceRepository();
        
    }

    private void mockUserRepository() {
        doReturn(USER).when(userRepository).findUserByLogin(eq(USER_LOGIN));
        doReturn(REGISTRATOR).when(userRepository).findUserByLogin(eq(REGISTRATOR_LOGIN));
        doReturn(Arrays.asList(REGISTRATORS)).when(userRepository).getUsersByRoleAndCommunity(eq(RoleType.REGISTRATOR),
                any(TerritorialCommunity.class));
    }

    private void mockInquiryRepository() {
        doReturn(Arrays.asList(INQUIRIES)).when(inquiryRepository).findByUserAndInquiryType(USER, InquiryType.INPUT);
        doReturn(Arrays.asList(INQUIRIES)).when(inquiryRepository).findByRegistratorAndInquiryType(REGISTRATOR,
                InquiryType.INPUT);
    }
    
    private void mockResourceRepository(){
        doReturn(RESOURCE).when(resourceRepository).findByIdentifier(eq(RESOURCE_ID));
    }

    @Test
    public void testAddOutputInquiry() {

        Inquiry inquiry = inquiryService.addOutputInquiry(RESOURCE_ID, REGISTRATOR_LOGIN, USER_LOGIN);

        verify(userRepository, times(1)).findUserByLogin(REGISTRATOR_LOGIN);
        verify(userRepository, times(1)).findUserByLogin(USER_LOGIN);
        verify(resourceRepository, times(1)).findByIdentifier(RESOURCE_ID);
        verify(inquiryRepository, times(1)).save(inquiry);

        Assert.assertEquals(InquiryType.OUTPUT, inquiry.getInquiryType());
    }

    @Test
    public void testAddInputInquiry() {

        inquiryService.addInputInquiry(USER_LOGIN, new Resource(), USER);

        verify(userRepository, times(1)).findUserByLogin(USER_LOGIN);
        verify(inquiryRepository, times(1)).save(any(Inquiry.class));
    }

    @Test
    public void testListUserNameDTO() {
        List<UserNameDTO> userNameDTOs = inquiryService.listUserNameDTO(USER_LOGIN);

        verify(userRepository, times(1)).findUserByLogin(USER_LOGIN);
        verify(userRepository, times(1)).getUsersByRoleAndCommunity(eq(RoleType.REGISTRATOR),
                any(TerritorialCommunity.class));

        for (User registrator : REGISTRATORS) {
            Assert.assertTrue(userNameDTOs.contains(convertToDTO(registrator)));
        }
    }

    private UserNameDTO convertToDTO(User user) {
        return new UserNameDTO(user.getFirstName(), user.getLastName(), user.getMiddleName(), user.getLogin());
    }

    @Test
    public void testListInquiryUserForUserRole() {
        List<InquiryListDTO> inquiryListDTOs = inquiryService.listInquiryUser(USER_LOGIN, InquiryType.INPUT);
        verify(inquiryRepository, times(1)).findByUserAndInquiryType(USER, InquiryType.INPUT);
        verify(inquiryRepository, never()).findByRegistratorAndInquiryType(REGISTRATOR, InquiryType.INPUT);

        Assert.assertEquals(inquiryListDTOs.size(), INQUIRIES.length);

        List<Integer> inquiryDtoIds = new ArrayList<>(inquiryListDTOs.size());
        for (InquiryListDTO inquiryListDTO : inquiryListDTOs) {
            inquiryDtoIds.add(inquiryListDTO.getInquiryId());
        }

        for (Inquiry inquiry : INQUIRIES) {
            inquiryDtoIds.contains(inquiry.getInquiryId());
        }
    }

    @Test
    public void testListInquiryUserForNonUserRole() {
        List<InquiryListDTO> inquiryListDTOs = inquiryService.listInquiryUser(REGISTRATOR_LOGIN, InquiryType.INPUT);
        verify(inquiryRepository, never()).findByUserAndInquiryType(USER, InquiryType.INPUT);
        verify(inquiryRepository, times(1)).findByRegistratorAndInquiryType(REGISTRATOR, InquiryType.INPUT);

        Assert.assertEquals(inquiryListDTOs.size(), INQUIRIES.length);

        List<Integer> inquiryDtoIds = new ArrayList<>(inquiryListDTOs.size());
        for (InquiryListDTO inquiryListDTO : inquiryListDTOs) {
            inquiryDtoIds.add(inquiryListDTO.getInquiryId());
        }

        for (Inquiry inquiry : INQUIRIES) {
            inquiryDtoIds.contains(inquiry.getInquiryId());
        }
    }

    @Configuration
    static class InquiryServiceTestContextConfiguration {

        @Bean
        public InquiryRepository inquiryRepository() {
            return mock(InquiryRepository.class);
        }

        @Bean
        public UserRepository userRepository() {
            return mock(UserRepository.class);
        }

        @Bean
        public ResourceRepository resourceRepository() {
            return mock(ResourceRepository.class);
        }

        @Bean
        public InquiryService inquiryService() {
            return new InquiryServiceImpl();
        }

        @Bean
        public org.slf4j.Logger logger() {
            return LoggerFactory.getLogger("");
        }
    }
}
