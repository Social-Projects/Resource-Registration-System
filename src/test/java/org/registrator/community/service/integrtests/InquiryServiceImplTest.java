package org.registrator.community.service.integrtests;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.registrator.community.config.LoggingConfig;
import org.registrator.community.config.root.SpringRootConfig;
import org.registrator.community.config.root.TestingConfiguration;
import org.registrator.community.dao.InquiryRepository;
import org.registrator.community.dao.ResourceRepository;
import org.registrator.community.dao.ResourceTypeRepository;
import org.registrator.community.dao.TomeRepository;
import org.registrator.community.dao.UserRepository;
import org.registrator.community.dto.InquiryListDTO;
import org.registrator.community.dto.UserNameDTO;
import org.registrator.community.entity.Inquiry;
import org.registrator.community.entity.Resource;
import org.registrator.community.entity.TerritorialCommunity;
import org.registrator.community.entity.User;
import org.registrator.community.enumeration.InquiryType;
import org.registrator.community.enumeration.ResourceStatus;
import org.registrator.community.enumeration.RoleType;
import org.registrator.community.service.InquiryService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@ActiveProfiles("testing")
@ContextConfiguration(classes = { TestingConfiguration.class, LoggingConfig.class, SpringRootConfig.class })
public class InquiryServiceImplTest extends AbstractTestNGSpringContextTests {
	@Autowired
	private Logger logger;
	@Autowired
	private InquiryRepository inquiryRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ResourceRepository resourceRepository;
	@Autowired
	private InquiryService inquiryService;
	@Autowired
	private ResourceTypeRepository resourceTypeRepository;
	@Autowired
	private TomeRepository tomeRepository;

	List<Inquiry> cInquiryList = new ArrayList<Inquiry>();
	List<Resource> cResourceList = new ArrayList<Resource>();

	private Date date = new Date();

	private static final int DESIRED_RESOURCES = 10;
	private int resourceNum = 0;

	// DataProviders

	@DataProvider(name = "ProviderForInquiries")
	public Object[][] formDataForInquiries() {
		logger.debug("Generating basic input data");
		Object[][] tmp = new Object[DESIRED_RESOURCES][3];

		User owner = userRepository.findUserByLogin("user"), registrator = userRepository.findUserByLogin("registrator");

		String resIdent = "land#%03d", resDef = "This is land";

		for (int i = 0; i < DESIRED_RESOURCES; i++) {
			Resource res = new Resource();
			res.setType(resourceTypeRepository.findOne(1));
			res.setIdentifier(String.format(resIdent, resourceNum++));
			res.setDescription(resDef);
			res.setDate(date);
			res.setRegistrator(registrator);
			res.setStatus(ResourceStatus.ACTIVE);
			res.setTome(tomeRepository.findTomeByRegistrator(registrator));
			res.setReasonInclusion("reason");
			resourceRepository.save(res);

			cResourceList.add(res);

			tmp[i] = new Object[] { res, registrator, owner };
		}

		return tmp;
	}

	@DataProvider(name = "DataForListInquiryMethod")
	public Object[][] formDataForListInquiryMethod() {
		logger.debug("Generating input and output inquiry data");
		List<User> userList = userRepository.findAll();
		InquiryType[] inqType = InquiryType.values();

		Object[][] tmp = new Object[userList.size() * inqType.length][];

		int pos = 0;
		for (User user : userList) {
			tmp[pos++] = new Object[] { user, inqType[pos % inqType.length] };
			tmp[pos++] = new Object[] { user, inqType[pos % inqType.length] };
		}

		return tmp;
	}

	@DataProvider(name = "DataForUserListUsersByTC")
	public Object[][] formDataForUserlistByTC() {
		logger.debug("Generating user list");
		List<User> userList = userRepository.findAll();

		Object[][] tmp = new Object[userList.size()][];
		int pos = 0;
		for (User user : userList) {
			tmp[pos++] = new Object[] { user };
		}

		return tmp;
	}
	// Tests

	@Test(dataProvider = "ProviderForInquiries", priority=1)
	public void addInputInquiry(Resource res, User registrator, User owner) {
		logger.debug("Start");
		long sizeBefore = inquiryRepository.count();
		InquiryType type = InquiryType.INPUT;

		inquiryService.addInputInquiry(owner.getLogin(), res, registrator);
		Assert.assertEquals(sizeBefore + 1, inquiryRepository.count());

		Inquiry actual = null;
		List<Inquiry> inqList = inquiryRepository.findByRegistratorAndInquiryType(registrator, type);
		for (Inquiry iq : inqList) {
			if (iq.getResource().equals(res)) {
				if (actual == null) {
					actual = iq;
				} else {
					Assert.assertNull(actual);
				}
			}
		}
		Assert.assertNotNull(actual);
		cInquiryList.add(actual);
		logger.debug("End");
	}

	@Test(dataProvider = "ProviderForInquiries", priority=2)
	public void addOutputInquiry(Resource res, User registrator, User owner) {
		logger.debug("Start");
		InquiryType type = InquiryType.OUTPUT;

		Inquiry expected = new Inquiry(type.toString(), date, owner, registrator, res),
				actual = inquiryService.addOutputInquiry(res.getIdentifier(), registrator.getLogin(), owner.getLogin());

		Assert.assertEquals(actual.getRegistrator().getUserId(), expected.getRegistrator().getUserId());
		Assert.assertEquals(actual.getResource(), expected.getResource());
		Assert.assertEquals(actual.getInquiryType(), expected.getInquiryType());

		cInquiryList.add(actual);
		logger.debug("End");
	}

	//
	@Test(dataProvider = "DataForListInquiryMethod", priority=3)
	public void listInquiryUser(User user, InquiryType it) {
		logger.debug("Start");
		List<InquiryListDTO> expected = new ArrayList<InquiryListDTO>();
		user = userRepository.findUserByLogin(user.getLogin());
		InquiryListDTO inquiryListDTO;

		List<Inquiry> inquiries;
		if (user.getRole().getType().equals(RoleType.USER)) {
			inquiries = inquiryRepository.findByUserAndInquiryType(user, it);
		} else {
			inquiries = inquiryRepository.findByRegistratorAndInquiryType(user, it);
		}

		for (Inquiry inq : inquiries) {
			inquiryListDTO = new InquiryListDTO(inq.getInquiryId(), inq.getInquiryType().toString(), inq.getDate(),
					null, null, inq.getResource().getIdentifier(), inq.getResource().getStatus());
			User userFrom = inq.getUser();
			inquiryListDTO.setUserName(
					userFrom.getLastName() + " " + userFrom.getFirstName() + " " + userFrom.getMiddleName());
			User registrator = inq.getRegistrator();
			inquiryListDTO.setRegistratorName(
					registrator.getLastName() + " " + registrator.getFirstName() + " " + registrator.getMiddleName());
			expected.add(inquiryListDTO);
		}

		List<InquiryListDTO> actual = inquiryService.listInquiryUser(user.getLogin(), it);
		Assert.assertEquals(expected.size(), actual.size());

		for (int i = 0; i < expected.size(); i++) {
			InquiryListDTO comp0 = expected.get(i), comp1 = actual.get(i);
			Assert.assertEquals(comp0.getInquiryType(), comp1.getInquiryType());
			Assert.assertEquals(comp0.getRegistratorName(), comp1.getRegistratorName());
			Assert.assertEquals(comp0.getResourceIdentifier(), comp1.getResourceIdentifier());
			Assert.assertEquals(comp0.getUserName(), comp1.getUserName());
			Assert.assertEquals(comp0.getRegistratorName(), comp1.getRegistratorName());
			Assert.assertEquals(comp0.getResourceStatus(), comp1.getResourceStatus());
		}
		logger.debug("End");
	}

	@Test(dataProvider = "DataForUserListUsersByTC", priority=4)
	public void listUserNameDTO(User user) {
		logger.debug("Start");
		User usr = userRepository.findUserByLogin(user.getLogin());
		TerritorialCommunity tc = usr.getTerritorialCommunity();
		List<User> registrators = userRepository.getUsersByRoleAndCommunity(RoleType.REGISTRATOR, tc);

		logger.info("Testing the formation of the UserNameDTO, builded using User obj: " + user.getLogin());

		List<UserNameDTO> expected = new ArrayList<UserNameDTO>(registrators.size());
		UserNameDTO userNameDTO;
		for (User registrator : registrators) {
			userNameDTO = new UserNameDTO(registrator.getFirstName(), registrator.getLastName(),
					registrator.getMiddleName(), registrator.getLogin());
			expected.add(userNameDTO);
		}

		List<UserNameDTO> actual = inquiryService.listUserNameDTO(user.getLogin());
		Assert.assertEquals(expected.size(), actual.size());
		actual.removeAll(expected);
		Assert.assertEquals(actual.size(), 0);
		logger.debug("End");
	}

	@Test(priority=5)
	public void removeInquiry() {
		logger.debug("Start");
		long sizeBefore = inquiryRepository.count();
		for (Inquiry inq : cInquiryList) {
			inquiryService.removeInquiry(inq.getInquiryId());
		}
		long expectedSize = sizeBefore - cInquiryList.size();
		Assert.assertEquals(inquiryRepository.count(), expectedSize);
		logger.debug("End");
	}

	@AfterClass
	public void cleanUp() {
		logger.debug("Start");
		logger.info("Cleaning up the repositories. Before: " + resourceRepository.count());
		for (Resource res : cResourceList) {
			resourceRepository.delete(res);
		}
		logger.info("After: " + resourceRepository.count());
		logger.debug("End");
	}
}
