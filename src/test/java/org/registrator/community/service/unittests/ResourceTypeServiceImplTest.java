package org.registrator.community.service.unittests;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.support.membermodification.MemberModifier;
import org.registrator.community.dao.DiscreteParameterRepository;
import org.registrator.community.dao.LinearParameterRepository;
import org.registrator.community.dao.ResourceTypeRepository;
import org.registrator.community.dto.ResourceDTO;
import org.registrator.community.dto.ResourceTypeDTO;
import org.registrator.community.dto.TypeParameterDTO;
import org.registrator.community.entity.DiscreteParameter;
import org.registrator.community.entity.LinearParameter;
import org.registrator.community.entity.Resource;
import org.registrator.community.entity.ResourceType;
import org.registrator.community.entity.Tome;
import org.registrator.community.entity.User;
import org.registrator.community.enumeration.ResourceStatus;
import org.registrator.community.service.ResourceService;
import org.registrator.community.service.ResourceTypeService;
import org.registrator.community.service.impl.ResourceTypeServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ResourceTypeServiceImplTest {
	@Mock
	private ResourceTypeRepository resourceTypeRepository;
	@Mock
	private LinearParameterRepository linearParameterRepository;
	@Mock
	private DiscreteParameterRepository discreteParameterRepository;
	@Mock
	private ResourceService resourceService;
	@InjectMocks
	private ResourceTypeService resourceTypeService = new ResourceTypeServiceImpl();

	private Logger logger = LoggerFactory.getLogger(resourceTypeService.getClass());
	private static final int DESIRED_RESOURCES = 10;
	private int resId = 0;

	@BeforeClass
	public void bindMocks() {
		logger.debug("Performing InjectMock operations");
		MockitoAnnotations.initMocks(this);

		try {
			MemberModifier.field(resourceTypeService.getClass(), "logger").set(
					resourceTypeService, logger);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@BeforeClass
	public void prepareMockForResourceTypeRep() {
		logger.debug("Preparing ResourceType repository emulation");

		List<ResourceType> mockList = new ArrayList<ResourceType>();

		when(resourceTypeRepository.saveAndFlush(any(ResourceType.class)))
				.then(new Answer<ResourceType>() {
					public ResourceType answer(InvocationOnMock invo) {
						ResourceType arg = invo.getArgumentAt(0,
								ResourceType.class);
						mockList.add(arg);
						return arg;
					}
				});

		when(resourceTypeRepository.findOne(anyInt())).then(
				new Answer<ResourceType>() {
					public ResourceType answer(InvocationOnMock invo) {
						Integer arg = invo.getArgumentAt(0, Integer.class);
						for (ResourceType res : mockList) {
							int resId = (res.getTypeId() != null) ? res.getTypeId() : -1;
							if (resId == arg) {
								return res;
							}
						}
						return null;
					}
				});

		when(resourceTypeRepository.findByName(anyString())).then(
				new Answer<ResourceType>() {
					public ResourceType answer(InvocationOnMock invo) {
						String arg = invo.getArgumentAt(0, String.class);
						for (ResourceType res : mockList) {
							String resName = res.getTypeName();
							if (resName.equals(arg)) {
								return res;
							}
						}
						return null;
					}
				});

		when(resourceTypeRepository.findAll()).then(
				new Answer<List<ResourceType>>() {
					public List<ResourceType> answer(InvocationOnMock invo) {
						return mockList;
					}
				});

		doAnswer(new Answer<Void>() {
			public Void answer(InvocationOnMock invo) {
				ResourceType arg = invo.getArgumentAt(0, ResourceType.class);
				mockList.remove(arg);
				return null;
			}
		}).when(resourceTypeRepository).delete(any(ResourceType.class));

		when(resourceTypeRepository.count()).then(new Answer<Long>() {
			public Long answer(InvocationOnMock invo) {
				Long size = new Long(mockList.size());
				return size;
			}
		});
	}

	@BeforeClass
	public void prepareMockForResourceServ() {
		logger.debug("Preparing Resource service emulation");

		List<Resource> mockList = new ArrayList<Resource>();

		when(resourceService.findByType(any(ResourceType.class))).then(
				new Answer<List<Resource>>() {
					public List<Resource> answer(InvocationOnMock invo) {
						ResourceType arg = invo.getArgumentAt(0,
								ResourceType.class);
						String argTypeName = arg.getTypeName();

						List<Resource> result = new ArrayList<Resource>();
						for (Resource res : mockList) {

							String resType = res.getType().getTypeName();

							if (resType.equals(argTypeName)) {
								result.add(res);
							}
						}
						return result;
					}
				});

		when(
				resourceService.saveResource(any(ResourceDTO.class),
						any(User.class))).then(
				new Answer<ResourceDTO>() {
					public ResourceDTO answer(InvocationOnMock invo) {
						ResourceDTO arg = invo.getArgumentAt(0,
								ResourceDTO.class);

						ResourceType resType = resourceTypeRepository
								.findByName(arg.getResourceType());
						String resId = arg.getIdentifier();
						String resDesc = arg.getDescription();
						User resRegistrator = new User();
						Date resDate = arg.getDate();
						String resStatus = arg.getStatus().toString();
						Tome resTome = new Tome();
						String resReason = arg.getReasonInclusion();

						Resource res = new Resource(resType, resId, resDesc,
								resRegistrator, resDate, resStatus, resTome,
								resReason);
						mockList.add(res);

						return arg;
					}
				});
	}

	// DataProviders

	@DataProvider
	public Object[][] formResourceTypes() {
		logger.debug("Generating basic input for ResourceType formation");

		Object[][] result = new Object[DESIRED_RESOURCES][];

		String resMask = "ResourceType#%03d";

		for (int i = 0; i < result.length; i++) {
			ResourceType res = new ResourceType(String.format(resMask, resId));
			res.setTypeId(resId++);
			result[i] = new Object[]{res};
		}
		return result;
	}

	@DataProvider
	public Object[][] formAndFlushResourceTypes() {
		logger.debug("Generating ReposityType and flushing it in the repository for find and delete test");
		Object[][] result = new Object[DESIRED_RESOURCES][];

		String resMask = "ResourceType#%03d";
		User user = new User();
		Date date = new Date();

		for (int i = 0; i < result.length; i++) {
			ResourceType res = new ResourceType(String.format(resMask, resId));
			res.setTypeId(resId++);
			result[i] = new Object[]{res};
			resourceTypeRepository.saveAndFlush(res);

			if (i % 2 == 0) {
				ResourceDTO resDTO = new ResourceDTO();
				resDTO.setResourceType(res.getTypeName());
				resDTO.setIdentifier(String.valueOf(res.getTypeId()));
				resDTO.setDate(date);
				resDTO.setStatus(ResourceStatus.ACTIVE);
				resDTO.setReasonInclusion("Reason");

				resourceService.saveResource(resDTO, user);
			}
		}
		return result;
	}

	@DataProvider
	public Object[][] formResourceTypesWithParams() {
		logger.debug("Generating ResourceType data with additional data");
		Object[][] result = new Object[DESIRED_RESOURCES][];

		String resMask = "ResourceType#%03d";
		String[] paramTypes = new String[]{"linearParameters",
				"discreteParameters"};

		List<TypeParameterDTO> paramList = new ArrayList<TypeParameterDTO>();

		for (int i = 0; i < result.length; i++) {
			ResourceType res = new ResourceType(String.format(resMask, resId));
			res.setTypeId(resId++);

			TypeParameterDTO tpDTO = new TypeParameterDTO();
			tpDTO.setParametersType(paramTypes[i % 2]);
			tpDTO.setDescription("mockVolts");
			tpDTO.setUnitName(paramTypes[i % 2] + "Unit");

			paramList.add(tpDTO);

			result[i] = new Object[]{res, paramList};
		}
		return result;
	}

	// Tests

	@Test(dataProvider = "formResourceTypes")
	public void addResourceType(ResourceType res) {
		logger.debug("Start");

		long size = resourceTypeRepository.count();

		ResourceType formed = resourceTypeService.addResourceType(res);

		Assert.assertEquals(res.getTypeName(), formed.getTypeName());
		Assert.assertEquals(res.getTypeId(), formed.getTypeId());
		Assert.assertEquals((size + 1), resourceTypeRepository.count());
		logger.debug("End");
	}

	@Test(dataProvider = "formResourceTypes")
	public void findById(ResourceType res) {
		logger.debug("Start");

		ResourceType expected = resourceTypeRepository.saveAndFlush(res), actual = resourceTypeService
				.findById(res.getTypeId());

		Assert.assertNotNull(actual);
		Assert.assertEquals(expected.getTypeName(), actual.getTypeName());
		Assert.assertEquals(expected.getTypeId(), actual.getTypeId());
		logger.debug("End");
	}

	@Test(dataProvider = "formResourceTypes")
	public void findByName(ResourceType res) {
		logger.debug("Start");

		ResourceType expected = resourceTypeRepository.saveAndFlush(res), actual = resourceTypeService
				.findByName(res.getTypeName());

		Assert.assertNotNull(actual);
		Assert.assertEquals(expected.getTypeName(), actual.getTypeName());
		Assert.assertEquals(expected.getTypeId(), actual.getTypeId());
		logger.debug("End");
	}

	@Test(dataProvider = "formResourceTypes")
	public void findAll(ResourceType res) {
		logger.debug("Start");

		resourceTypeRepository.saveAndFlush(res);

		long repSize = resourceTypeRepository.count();
		List<ResourceType> expected = resourceTypeRepository.findAll(), actual = resourceTypeService
				.findAll();

		Assert.assertEquals(repSize, expected.size());
		Assert.assertEquals(repSize, actual.size());

		expected.removeAll(actual);

		Assert.assertEquals(expected.size(), 0);
		logger.debug("End");
	}

	@Test(dataProvider = "formResourceTypes")
	public void editResourceType(ResourceType res) {
		logger.debug("Start");

		ResourceTypeDTO expected = new ResourceTypeDTO();

		expected.setTypeName(res.getTypeName());
		expected.setParameters(new ArrayList<TypeParameterDTO>());

		ResourceTypeDTO actual = resourceTypeService.editResourceType(expected);

		Assert.assertEquals(actual.getTypeName(), expected.getTypeName());
		Assert.assertEquals(actual.getParameters(), expected.getParameters());
		logger.debug("End");
	}

	@Test(dataProvider = "formAndFlushResourceTypes")
	public void delete(ResourceType res) {
		logger.debug("Start");

		List<Resource> resourcesOfGivenType = resourceService.findByType(res);

		boolean expected, actual;

		if (resourcesOfGivenType.size() > 0) {
			expected = false;
			actual = resourceTypeService.delete(res);
		} else {
			expected = true;
			actual = resourceTypeService.delete(res);
		}
		Assert.assertEquals(actual, expected);

		if (resourcesOfGivenType.size() == 0) {
			ResourceType expectedToBeNull = resourceTypeRepository.findOne(res.getTypeId());
			Assert.assertNull(expectedToBeNull);
		}
		logger.debug("End");
	}

	@Test(dataProvider = "formResourceTypesWithParams")
	public void addResourceTypeDTO(ResourceType res,
			List<TypeParameterDTO> paramList) {
		logger.debug("Start");

		resourceTypeRepository.saveAndFlush(res);

		ResourceTypeDTO expected = new ResourceTypeDTO();
		expected.setTypeName(res.getTypeName());

		List<TypeParameterDTO> typeList = paramList;
		List<LinearParameter> linearParList = new ArrayList<LinearParameter>();
		List<DiscreteParameter> discreteParList = new ArrayList<DiscreteParameter>();

		for (TypeParameterDTO tpar : typeList) {
			if (("linearParameters").equals(tpar.getParametersType())) {
				linearParList.add(new LinearParameter(res, tpar
						.getDescription(), tpar.getUnitName()));
			}
			if (("discreteParameters").equals(tpar.getParametersType())) {
				discreteParList.add(new DiscreteParameter(res, tpar
						.getDescription(), tpar.getUnitName()));
			}
		}

		expected.setParameters(typeList);

		ResourceTypeDTO tmp = new ResourceTypeDTO();
		tmp.setTypeName(res.getTypeName());
		tmp.setParameters(paramList);

		ResourceTypeDTO actual = resourceTypeService.addResourceTypeDTO(tmp);

		Assert.assertEquals(actual.getTypeName(), expected.getTypeName());

		List<TypeParameterDTO> actualParamList = actual.getParameters(), expectedParamList = expected
				.getParameters();
		Assert.assertEquals(actualParamList.size(), expectedParamList.size());

		for (int i = 0; i < actualParamList.size(); i++) {
			TypeParameterDTO arg0 = actualParamList.get(i), arg1 = expectedParamList
					.get(i);

			Assert.assertEquals(arg0.getParametersType(),
					arg1.getParametersType());
			Assert.assertEquals(arg0.getDescription(), arg1.getDescription());
			Assert.assertEquals(arg0.getUnitName(), arg1.getUnitName());
		}
		logger.debug("End");
	}

}
