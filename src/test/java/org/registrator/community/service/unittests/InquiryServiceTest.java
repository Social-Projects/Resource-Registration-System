package org.registrator.community.service.unittests;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.support.membermodification.MemberModifier;
import org.registrator.community.dao.InquiryRepository;
import org.registrator.community.dao.ResourceRepository;
import org.registrator.community.dao.UserRepository;
import org.registrator.community.dto.InquiryListDTO;
import org.registrator.community.dto.UserNameDTO;
import org.registrator.community.entity.Inquiry;
import org.registrator.community.entity.Resource;
import org.registrator.community.entity.ResourceType;
import org.registrator.community.entity.Role;
import org.registrator.community.entity.TerritorialCommunity;
import org.registrator.community.entity.Tome;
import org.registrator.community.entity.User;
import org.registrator.community.enumeration.InquiryType;
import org.registrator.community.enumeration.ResourceStatus;
import org.registrator.community.enumeration.RoleType;
import org.registrator.community.enumeration.UserStatus;
import org.registrator.community.service.InquiryService;
import org.registrator.community.service.impl.InquiryServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class InquiryServiceTest {
	@Mock
	private UserRepository userRepository;
	@Mock
	private InquiryRepository inquiryRepository;
	@Mock
	private ResourceRepository resourceRepository;
	@InjectMocks
	private InquiryService inquiryService = new InquiryServiceImpl();

	private Logger logger = LoggerFactory.getLogger(inquiryService.getClass());
	private Date date = new Date();
	private static final int DESIRED_RESOURCES = 10;
	private int inquiryId = 0;

	@BeforeClass
	public void bindMocks() {
		logger.debug("Performing InjectMock operations");
		MockitoAnnotations.initMocks(this);

		try {
			MemberModifier.field(inquiryService.getClass(), "logger").set(
					inquiryService, logger);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Mock repository preparations and bindings

	@BeforeClass
	public void prepareUserRepMock() {
		logger.debug("Preparing User repository emulation");
		List<User> mockList = new ArrayList<User>();
		when(userRepository.count()).then(new Answer<Long>() {
			public Long answer(InvocationOnMock invocation) throws Throwable {
				return (long) mockList.size();
			}
		});
		when(userRepository.save(any(User.class))).then(new Answer<User>() {
			@Override
			public User answer(InvocationOnMock invocation) throws Throwable {
				User givenArg = invocation.getArgumentAt(0, User.class);
				mockList.add(givenArg);
				return givenArg;
			}

		});

		when(userRepository.findOne(anyInt())).then(new Answer<User>() {
			@Override
			public User answer(InvocationOnMock invocation) throws Throwable {
				Integer num = invocation.getArgumentAt(0, Integer.class);
				return mockList.get(num);
			}
		});

		when(userRepository.findUserByLogin(anyString())).then(
				new Answer<User>() {
					public User answer(InvocationOnMock invocation)
							throws Throwable {
						String userName = invocation.getArgumentAt(0,
								String.class);
						for (User usr : mockList) {
							if (usr.getLogin().equals(userName)) {
								return usr;
							}
						}
						return null;
					}
				});
		when(
				(userRepository.getUsersByRoleAndCommunity(any(RoleType.class),
						any(TerritorialCommunity.class)))).then(
				new Answer<List<User>>() {
					public List<User> answer(InvocationOnMock invocation)
							throws Throwable {
						List<User> result = new ArrayList<User>();
						TerritorialCommunity tc = invocation.getArgumentAt(1,
								TerritorialCommunity.class);

						for (User usr : mockList) {
							if (usr.getRole().getType() == RoleType.REGISTRATOR
									&& usr.getTerritorialCommunity() != null
									&& usr.getTerritorialCommunity().equals(tc)) {
								result.add(usr);
							}
						}
						return result;
					}
				});

		doAnswer(new Answer<Void>() {
			public Void answer(InvocationOnMock invocation) throws Throwable {
				mockList.clear();
				return null;
			}
		}).when(userRepository).deleteAll();
	}

	@BeforeClass
	public void prepareResourceRepMock() {
		logger.debug("Preparing Resource repository emulation");
		List<Resource> mockList = new ArrayList<Resource>();

		when(resourceRepository.count()).then(new Answer<Long>() {
			public Long answer(InvocationOnMock invocation) throws Throwable {
				return (long) mockList.size();
			}
		});
		when(resourceRepository.findByIdentifier(anyString())).then(
				new Answer<Resource>() {
					public Resource answer(InvocationOnMock invocation)
							throws Throwable {
						String resource = invocation.getArgumentAt(0,
								String.class);
						for (Resource res : mockList) {
							if (res.getIdentifier() == resource)
								return res;
						}
						return null;
					}
				});
		when(resourceRepository.save(any(Resource.class))).then(
				new Answer<Resource>() {
					public Resource answer(InvocationOnMock invocation)
							throws Throwable {
						Resource res = invocation.getArgumentAt(0,
								Resource.class);
						mockList.add(res);
						return res;
					}
				});
	}

	@BeforeClass
	public void prepareInquiryRepMock() {
		logger.debug("Preparing Inquiry repository emulation");
		List<Inquiry> mockList = new ArrayList<Inquiry>();

		when(inquiryRepository.count()).then(new Answer<Long>() {
			public Long answer(InvocationOnMock invocation) throws Throwable {
				return (long) mockList.size();
			}
		});
		when(inquiryRepository.save(any(Inquiry.class))).then(
				new Answer<Inquiry>() {
					public Inquiry answer(InvocationOnMock invocation)
							throws Throwable {
						Inquiry inq = invocation
								.getArgumentAt(0, Inquiry.class);
						mockList.add(inq);
						return inq;
					}
				});
		when(inquiryRepository.findOne(anyInt())).then(new Answer<Inquiry>() {
			public Inquiry answer(InvocationOnMock invocation) throws Throwable {
				Integer num = invocation.getArgumentAt(0, Integer.class);
				return mockList.get(num);
			}
		});
		doAnswer(new Answer<Void>() {
			public Void answer(InvocationOnMock invocation) throws Throwable {
				mockList.clear();
				return null;
			}
		}).when(inquiryRepository).deleteAll();

		when(inquiryRepository.getOne(anyInt())).then(new Answer<Inquiry>() {
			public Inquiry answer(InvocationOnMock invocation) throws Throwable {
				Integer num = invocation.getArgumentAt(0, Integer.class);
				for (Inquiry inq : mockList) {
					if (inq.getInquiryId() != null && inq.getInquiryId() == num) {
						return inq;
					}
				}
				return null;
			}
		});
		doAnswer(new Answer<Void>() {
			public Void answer(InvocationOnMock invocation) throws Throwable {
				Integer num = invocation.getArgumentAt(0, Integer.class);
				for (int i = 0; i < mockList.size(); i++) {
					Inquiry inq = mockList.get(i);
					if (inq.getInquiryId() == num) {
						mockList.remove(inq);
					}
				}
				return null;
			}
		}).when(inquiryRepository).delete(anyInt());

		when(
				inquiryRepository.findByUserAndInquiryType(
						org.mockito.Mockito.any(User.class),
						any(InquiryType.class))).then(
				new Answer<List<Inquiry>>() {
					public List<Inquiry> answer(InvocationOnMock invocation)
							throws Throwable {
						User user = invocation.getArgumentAt(0, User.class);
						InquiryType inqT = invocation.getArgumentAt(1,
								InquiryType.class);
						List<Inquiry> result = new ArrayList<Inquiry>();

						for (Inquiry inq : mockList) {
							if (inq.getUser().equals(user)
									&& inq.getInquiryType().equals(inqT)) {
								result.add(inq);
							}
						}

						return result;
					}
				});

		when(
				inquiryRepository.findByRegistratorAndInquiryType(
						any(User.class), any(InquiryType.class))).then(
				new Answer<List<Inquiry>>() {
					public List<Inquiry> answer(InvocationOnMock invocation)
							throws Throwable {
						// User user = invocation.getArgumentAt(0, User.class);
						InquiryType inqT = invocation.getArgumentAt(1,
								InquiryType.class);
						List<Inquiry> result = new ArrayList<Inquiry>();

						for (Inquiry inq : mockList) {
							if (inq.getInquiryType().equals(inqT)) {
								result.add(inq);
							}
						}

						return result;
					}
				});
	}

	// DataProviders

	@DataProvider(name = "ProviderForInquiries")
	public Object[][] formDataForInquiries() {
		logger.debug("Generating basic input data for Inquiry buildup");
		Object[][] tmp = new Object[DESIRED_RESOURCES][];

		Role[] roles = new Role[]{new Role(RoleType.REGISTRATOR, "desc"),
				new Role(RoleType.USER, "desc")};

		for (int i = 0; i < roles.length; i++) {
			User usr = new User("user." + roles[i].toString().toLowerCase(),
					"password", roles[i], roles[i].toString(), "is", "User",
					"m@il.ua", UserStatus.ACTIVE.toString());
			if (userRepository.findUserByLogin("user."
					+ roles[i].toString().toLowerCase()) == null) {
				userRepository.save(usr);
			}
		}

		User registrator = userRepository.findUserByLogin("user."
				+ RoleType.REGISTRATOR.toString().toLowerCase()), user = userRepository
				.findUserByLogin("user."
						+ RoleType.USER.toString().toLowerCase());
		String resIdent = "land#", resDef = "This is land";
		Tome tome = new Tome();
		ResourceType rt = new ResourceType();

		for (int i = 0; i < DESIRED_RESOURCES; i++) {
			Resource rs = new Resource(rt, resIdent + i, resDef, registrator,
					date, ResourceStatus.ACTIVE.toString(), tome, "Reason");
			resourceRepository.save(rs);

			tmp[i] = new Object[]{registrator, user, rs};
		}

		return tmp;
	}

	@DataProvider(name = "ProviderForListUserNameMethod")
	public Object[][] formDataForListTypeTests() {
		logger.debug("Generating input data for List users method");
		Object[][] tmp = new Object[DESIRED_RESOURCES][];

		Role[] roles = new Role[]{new Role(RoleType.REGISTRATOR, "desc")};
		TerritorialCommunity tc = new TerritorialCommunity();

		for (int i = 0; i < DESIRED_RESOURCES; i++) {
			String userLog = "user#" + i + "."
					+ roles[0].toString().toLowerCase();
			Role role = roles[0];

			User usr = new User(userLog, "password", role, role.toString(),
					"is", "User", "m@il.ua", UserStatus.ACTIVE.toString());
			if (userRepository.findUserByLogin(userLog) == null) {
				usr.setTerritorialCommunity(tc);
				userRepository.save(usr);
			} else {
				usr = userRepository.findUserByLogin(userLog);
				usr.setTerritorialCommunity(tc);
			}
			tmp[i] = new Object[]{usr};
		}
		return tmp;
	}

	@DataProvider(name = "ProviderForInquiryListTests")
	public Object[][] formDataForInquiryListTypeTests() {
		logger.debug("Generating data for Inquiry list test");

		InquiryType[] inqTypes = new InquiryType[]{InquiryType.INPUT,
				InquiryType.OUTPUT};
		Role[] roles = new Role[]{new Role(RoleType.REGISTRATOR, "desc"),
				new Role(RoleType.USER, "desc")};
		Tome tome = new Tome();
		ResourceType rt = new ResourceType();
		String resIdent = "land#", resDef = "This is land";

		Object[][] tmp = new Object[DESIRED_RESOURCES * inqTypes.length][2];

		User registrator = new User("userRegistratorForInqList", "password",
				roles[0], roles[0].toString(), "is", "User", "m@il.ua",
				UserStatus.ACTIVE.toString());
		registrator.setUserId(new Integer(0));
		userRepository.save(registrator);

		int j = 0;
		for (int i = 0; i < DESIRED_RESOURCES; i++) {
			String userN = "user#" + i + "." + roles[1];
			User user = new User(userN, "password", roles[1],
					roles[1].toString(), "is", "User", "m@il.ua",
					UserStatus.ACTIVE.toString());
			userRepository.save(user);

			Resource rs = new Resource(rt, resIdent + "#00" + i, resDef,
					registrator, date, ResourceStatus.ACTIVE.toString(), tome,
					"Reason");
			for (int n = 0; n < inqTypes.length; n++) {
				Inquiry inquiry = new Inquiry(inqTypes[n].toString(), date,
						user, registrator, rs);
				inquiryRepository.save(inquiry);

				User insU = (i % 2 == 0) ? user : registrator;
				tmp[j++] = new Object[]{inqTypes[n], insU};
			}
		}
		return tmp;
	}

	// Tests

	@Test(dataProvider = "ProviderForInquiries")
	public void testForOutputInquiryFormation(User reg, User user, Resource res) {
		logger.debug("Start");

		Inquiry expected = new Inquiry(InquiryType.OUTPUT.toString(), date,
				user, reg, res), actual = inquiryService.addOutputInquiry(
				res.getIdentifier(), reg.getLogin(), user.getLogin());

		Assert.assertEquals(expected.getInquiryType(), actual.getInquiryType());
		Assert.assertEquals(expected.getResource(), actual.getResource());
		Assert.assertEquals(expected.getRegistrator(), actual.getRegistrator());
		Assert.assertEquals(expected.getUser(), actual.getUser());

		logger.debug("End");
	}

	@Test(dataProvider = "ProviderForInquiries")
	public void testForInputInquiryFormation(User reg, User user, Resource res) {
		logger.debug("Start");

		long countBefore = inquiryRepository.count() + 1;

		inquiryService.addInputInquiry(user.getLogin(), res, reg);
		Assert.assertEquals(countBefore, inquiryRepository.count());

		logger.debug("End");
	}

	@Test(dataProvider = "ProviderForInquiries")
	public void testForRemoveInquiry(User reg, User user, Resource res) {
		logger.debug("Start");

		Inquiry expected = new Inquiry(InquiryType.OUTPUT.toString(), date,
				user, reg, res);

		expected.setInquiryId(inquiryId++);
		inquiryRepository.save(expected);

		expected = inquiryRepository.getOne(expected.getInquiryId());

		Assert.assertNotNull(expected);

		inquiryService.removeInquiry(expected.getInquiryId());

		Inquiry actual = inquiryRepository.getOne(expected.getInquiryId());
		Assert.assertNull(actual);

		logger.debug("End");
	}
		
	@Test(dataProvider = "ProviderForInquiryListTests")
	public void testForListInquiryUser(InquiryType inqT, User user) {
		logger.debug("Start");

		List<InquiryListDTO> expected = new ArrayList<InquiryListDTO>();
		user = userRepository.findUserByLogin(user.getLogin());
		InquiryListDTO inquiryListDTO;

		List<Inquiry> inquiries;
		if (user.getRole().getType().equals(RoleType.USER)) {
			inquiries = inquiryRepository.findByUserAndInquiryType(user, inqT);
		} else {
			inquiries = inquiryRepository.findByRegistratorAndInquiryType(user,
					inqT);
		}

		for (Inquiry inq : inquiries) {
			inquiryListDTO = new InquiryListDTO(inq.getInquiryId(), inq
					.getInquiryType().toString(), inq.getDate(), null, null,
					inq.getResource().getIdentifier(), inq.getResource()
							.getStatus());
			User userFrom = inq.getUser();
			inquiryListDTO.setUserName(userFrom.getLastName() + " "
					+ userFrom.getFirstName() + " " + userFrom.getMiddleName());
			User registrator = inq.getRegistrator();
			inquiryListDTO.setRegistratorName(registrator.getLastName() + " "
					+ registrator.getFirstName() + " "
					+ registrator.getMiddleName());
			expected.add(inquiryListDTO);
		}

		List<InquiryListDTO> actual = inquiryService.listInquiryUser(
				user.getLogin(), inqT);
		Assert.assertEquals(expected.size(), actual.size());

		for (int i = 0; i < expected.size(); i++) {
			InquiryListDTO comp0 = expected.get(i), comp1 = actual.get(i);
			Assert.assertEquals(comp0.getInquiryType(), comp1.getInquiryType());
			Assert.assertEquals(comp0.getRegistratorName(),
					comp1.getRegistratorName());
			Assert.assertEquals(comp0.getResourceIdentifier(),
					comp1.getResourceIdentifier());
			Assert.assertEquals(comp0.getUserName(), comp1.getUserName());
			Assert.assertEquals(comp0.getRegistratorName(),
					comp1.getRegistratorName());
			Assert.assertEquals(comp0.getResourceStatus(),
					comp1.getResourceStatus());
		}
		logger.debug("End");
	}

	@Test(dataProvider = "ProviderForListUserNameMethod")
	public void testForListUserNameFormation(User user) {
		logger.debug("Start");

		User usr = userRepository.findUserByLogin(user.getLogin());
		TerritorialCommunity tc = usr.getTerritorialCommunity();
		List<User> registrators = userRepository.getUsersByRoleAndCommunity(
				RoleType.REGISTRATOR, tc);

		logger.debug("Testing the formation of the UserNameDTO, builded using User obj: "
				+ user.getLogin());

		List<UserNameDTO> expected = new ArrayList<UserNameDTO>(
				registrators.size());
		UserNameDTO userNameDTO;
		for (User registrator : registrators) {
			userNameDTO = new UserNameDTO(registrator.getFirstName(),
					registrator.getLastName(), registrator.getMiddleName(),
					registrator.getLogin());
			expected.add(userNameDTO);
		}

		List<UserNameDTO> actual = inquiryService.listUserNameDTO(user
				.getLogin());
		Assert.assertEquals(expected.size(), actual.size());
		actual.removeAll(expected);
		Assert.assertEquals(actual.size(), 0);

		logger.debug("End");
	}


}
