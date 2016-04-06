package org.registrator.community.service.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.registrator.community.dao.DiscreteParameterRepository;
import org.registrator.community.dao.LinearParameterRepository;
import org.registrator.community.dao.PolygonRepository;
import org.registrator.community.dao.ResourceDiscreteValueRepository;
import org.registrator.community.dao.ResourceFindByParams;
import org.registrator.community.dao.ResourceLinearValueRepository;
import org.registrator.community.dao.ResourceNumberRepository;
import org.registrator.community.dao.ResourceRepository;
import org.registrator.community.dao.ResourceTypeRepository;
import org.registrator.community.dao.TomeRepository;
import org.registrator.community.dao.UserRepository;
import org.registrator.community.dto.ParameterSearchResultDTO;
import org.registrator.community.dto.PointAreaDTO;
import org.registrator.community.dto.PointDTO;
import org.registrator.community.dto.PoligonAreaDTO;
import org.registrator.community.dto.ResourceAreaDTO;
import org.registrator.community.dto.ResourceDTO;
import org.registrator.community.dto.ResourceDiscreteValueDTO;
import org.registrator.community.dto.ResourceLinearValueDTO;
import org.registrator.community.dto.SegmentLinearDTO;
import org.registrator.community.dto.ValueDiscreteDTO;
import org.registrator.community.dto.json.PointJson;
import org.registrator.community.dto.json.PolygonJson;
import org.registrator.community.dto.json.ResourceSearchJson;
import org.registrator.community.entity.DiscreteParameter;
import org.registrator.community.entity.LinearParameter;
import org.registrator.community.entity.Polygon;
import org.registrator.community.entity.Resource;
import org.registrator.community.entity.ResourceDiscreteValue;
import org.registrator.community.entity.ResourceLinearValue;
import org.registrator.community.entity.ResourceNumber;
import org.registrator.community.entity.ResourceType;
import org.registrator.community.entity.TerritorialCommunity;
import org.registrator.community.entity.User;
import org.registrator.community.enumeration.ResourceStatus;
import org.registrator.community.exceptions.ResourceEntityNotFound;
import org.registrator.community.service.InquiryService;
import org.registrator.community.service.ResourceService;
import org.registrator.community.service.SettingsService;
import org.registrator.community.service.UserService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

@Service
public class ResourceServiceImpl implements ResourceService {

    @Autowired
    private Logger logger;

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private TomeRepository tomeRepository;

    @Autowired
    private ResourceTypeRepository resourceTypeRepository;

    @Autowired
    private PolygonRepository polygonRepository;

    @Autowired
    private ResourceLinearValueRepository linearValueRepository;

    @Autowired
    private ResourceDiscreteValueRepository discreteValueRepository;

    @Autowired
    private LinearParameterRepository linearParameterRepository;

    @Autowired
    private DiscreteParameterRepository discreteParameterRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InquiryService inquiryService;

    @Autowired
    private ResourceNumberRepository resourceNumberRepository;

    @Autowired
    private ResourceFindByParams resourceFindByParams;

    @Autowired
    private SettingsService settingsService;

    @Autowired
    private UserService userService;

    @Override
    public ResourceDTO createNewResourceDTO() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        ResourceDTO result = new ResourceDTO();
        result.setIdentifier(getRegistrationNumber(auth.getName()));

        return result;
    }

    /**
     * Method parse the resourceDTO into entity objects and save them into
     * database
     * 
     * @param resourceDTO
     * @param registrator
     * @return
     */
    @Override
    @Transactional
    public ResourceDTO saveResource(ResourceDTO resourceDTO, User registrator) {
        logger.info("Method saveResource");
        logger.info("Owner Login -" + resourceDTO.getOwnerLogin());
        logger.info("Registrator Login -" + registrator.getLogin());
         /* form the resource entity and save in into database */

        Resource resourceEntity = resourceRepository.findByIdentifier(resourceDTO.getIdentifier());
        boolean isNew = (resourceEntity == null);
        resourceEntity = parseToResourseEntity(resourceDTO, registrator);
        resourceEntity = resourceRepository.save(resourceEntity);

        /* save all additional parameters of given resource */
        saveResourceParameters(resourceDTO, resourceEntity);

        if (isNew) {
            // save data in the table inquiry_list
            if (!resourceDTO.getOwnerLogin().isEmpty()) {
                inquiryService.addInputInquiry(resourceDTO.getOwnerLogin(), resourceEntity, registrator);
            }


            //increment registration number of resource for authenticated registrar if needed
            if (resourceEntity.getIdentifier().equals(getRegistrationNumber(registrator.getLogin()))) {
                incrementRegistrationNumber(registrator.getLogin());
            }
        }

        return formResourceDTO(resourceEntity);
    }



    /**
     * Find the resource with given identifier and form resourceDTO object
     * 
     * @param identifier
     * @return resourseDTO if exist
     */
    @Override
    public ResourceDTO findByIdentifier(String identifier) throws ResourceEntityNotFound {

        logger.info("Method findByIdentifier");
        Resource resourceEntity = resourceRepository.findByIdentifier(identifier);

        if (resourceEntity != null) {
            logger.info("Resource with identifier " + identifier + " was found");

            ResourceDTO resourceDTO = formResourceDTO(resourceEntity);

            return resourceDTO;

        } else {
            logger.info("Resource with identifier " + identifier + " was not found in database");
            throw new ResourceEntityNotFound(identifier);
        }

    }

    @Override
    public List<Resource> findByType(ResourceType type) {
        return resourceRepository.findByType(type);
    }

    @Override
    public long count() {
        return resourceRepository.count();
    }

    @Override
    public Set<String> getDescriptionBySearchTag(String searchTag) {
        return resourceRepository.findDescriptionsLikeProposed(searchTag);
    }

    @Override
    public Set<Resource> getAllByAreaLimits(Double minLat, Double maxLat, Double minLng, Double maxLng, String resType, Integer page) {
        Set<Resource> resources = new HashSet<>();
        Pageable pageable = new PageRequest(page, 200);
        List<Polygon> polygons = polygonRepository.findByLimits(minLat, maxLat, minLng, maxLng, pageable);
        for (Polygon polygon : polygons) {
            if ("all".equals(resType)) {
                resources.add(polygon.getResource());
            } else if (resType.equals(polygon.getResource().getType().getTypeName())) {
                resources.add(polygon.getResource());
            }
        }

        return resources;
    }

    @Override
    public Set<Resource> getAllByPoint(Double lat, Double lng, Integer page) {
        Set<Resource> resources = new HashSet<>();
        Pageable pageable = new PageRequest(page, 200);
        List<Polygon> polygons = polygonRepository.findByPoint(lat, lng, pageable);
        for (Polygon polygon : polygons) {
            resources.add(polygon.getResource());
        }
        return resources;
    }


    @Override
    public ParameterSearchResultDTO getAllByParameters(ResourceSearchJson parameters) {

        List<Resource> resources = resourceFindByParams.findByParams(parameters);
        ParameterSearchResultDTO result = new ParameterSearchResultDTO();
        result.setCount(resources.size());
        result.setResources(resources);
        return result;
    }

    @Override
    public List<PolygonJson> createPolygonJSON(Resource resource, int i) {
        List<PolygonJson> polygonsJSON = new ArrayList<>();

        List<Polygon> polygons = polygonRepository.findByResource(resource);

        for (Polygon polygon : polygons) {
            PolygonJson polygonJson = new PolygonJson();
            List<PointJson> points = new ArrayList<>();
            Gson gson = new Gson();
            List<PointDTO> pointDTOs = gson.fromJson(polygon.getCoordinates(), new TypeToken<List<PointDTO>>() {}.getType());

            for (PointDTO pointDTO: pointDTOs) {
                PointJson point = new PointJson();
                point.setLatitude(pointDTO.getLat());
                point.setLongitude(pointDTO.getLng());
                points.add(point);
            }

            polygonJson.setResourceDescription(resource.getDescription());
            polygonJson.setIdentifier(resource.getIdentifier());
            polygonJson.setResourceType(resource.getType().getTypeName());
            polygonJson.setDate(new SimpleDateFormat("dd.MM.yyyy").format(resource.getDate()));
            polygonJson.setDT_RowId("row" + i);
            polygonJson.setPoints(points);

            polygonsJSON.add(polygonJson);
            i++;
        }
        return polygonsJSON;
    }

    @Override
    public String getRegistrationNumber(String login) {
        User user = userRepository.findUserByLogin(login);
        TerritorialCommunity community = user.getTerritorialCommunity();
        
        ResourceNumber resourceNumber = resourceNumberRepository.findResourceNumberByUser(user);
        String regObjectPre = (community.getRegistrationNumber() != null) ? community.getRegistrationNumber() : "0000000000000000";

        if (resourceNumber != null) {
            StringBuilder resourceNumberPattern = new StringBuilder();

            resourceNumberPattern.append(regObjectPre); // NEED THE MODIFIED TC
            resourceNumberPattern.append("/");
            resourceNumberPattern.append(resourceNumber.getRegistratorNumber());
            resourceNumberPattern.append(":");
            String incrementNumber = String.format("%04d", resourceNumber.getNumber());
            resourceNumberPattern.append(incrementNumber);

            return resourceNumberPattern.toString();
        }
        return null;
    }

    /**
     * Method create the Resource object from given resourceDTO
     * 
     * @param resourceDTO
     * @param registrator
     * @return
     */

    private Resource parseToResourseEntity(ResourceDTO resourceDTO, User registrator) {

        Resource resourceEntity = resourceRepository.findByIdentifier(resourceDTO.getIdentifier());
        if (resourceEntity == null) {
            resourceEntity = new Resource();
            resourceEntity.setIdentifier(resourceDTO.getIdentifier());
            resourceEntity.setRegistrator(registrator);
            resourceEntity.setStatus(ResourceStatus.ACTIVE);
            resourceEntity.setTome(tomeRepository.findTomeByRegistrator(registrator));
        }
        resourceEntity.setDescription(resourceDTO.getDescription());
        resourceEntity.setReasonInclusion(resourceDTO.getReasonInclusion());

        ResourceType resourceType = resourceTypeRepository.findByName(resourceDTO.getResourceType());
        resourceEntity.setType(resourceType);
        resourceEntity.setDate(resourceDTO.getDate());

        return resourceEntity;
    }

    /**
     * Save additional parameters of resource entity in database
     * 
     * @param resourceDTO
     * @param resourceEntity
     */
    private void saveResourceParameters(ResourceDTO resourceDTO, Resource resourceEntity) {
        /* save list of area points */

        polygonRepository.deleteByResource(resourceEntity);
        List<Polygon> polygons = new ArrayList<>();
        for (PoligonAreaDTO poligonAreaDTO : resourceDTO.getResourceArea().getPoligons()) {

            Polygon polygonEntity = getPolygonEntity(resourceEntity, poligonAreaDTO);
            polygonEntity.setCoordinates(createCoordinatesJson(poligonAreaDTO));
            polygonEntity = polygonRepository.save(polygonEntity);
            polygons.add(polygonEntity);
        }
        resourceEntity.setPolygons(polygons);

        /* save list of resource linear values if exist */
        List<ResourceLinearValue> resourceLinearValues = new ArrayList<>();
        if (!resourceDTO.getResourceLinear().isEmpty()) {
            logger.info("save linear values of resource");
            resourceLinearValues = parseToLinearValueList(resourceDTO, resourceEntity);
            linearValueRepository.save(resourceLinearValues);
        }
        resourceEntity.setResourceLinearValues(resourceLinearValues);

        /* save list of resource discrete values if exist */
        List<ResourceDiscreteValue> resourceDiscreteValues = new ArrayList<>();
        if (!resourceDTO.getResourceDiscrete().isEmpty()) {
            logger.info("save discrete values of resource");
            resourceDiscreteValues = parseToDiscreteValueList(resourceDTO, resourceEntity);
            discreteValueRepository.save(resourceDiscreteValues);
        }
        resourceEntity.setResourceDiscreteValues(resourceDiscreteValues);
    }

    /**
     * Create Polygon object from poligonAreaDTO
     * 
     * @param resourceEntity
     * @param poligonAreaDTO
     * @return Polygon
     */
    private Polygon getPolygonEntity(Resource resourceEntity, PoligonAreaDTO poligonAreaDTO) {
        Polygon polygonEntity = new Polygon();

        Double minLat = 90.0;
        Double maxLat = -90.0;
        Double minLng = 180.0;
        Double maxLng = -180.0;

        for (PointAreaDTO point : poligonAreaDTO.getPoints()) {
            if (point.getOrderNumber() != 0) {
                if (minLat > point.getDecimalLatitude()) {
                    minLat = point.getDecimalLatitude();
                }
                if (maxLat < point.getDecimalLatitude()) {
                    maxLat = point.getDecimalLatitude();
                }
                if (minLng > point.getDecimalLongitude()) {
                    minLng = point.getDecimalLongitude();
                }
                if (maxLng < point.getDecimalLongitude()) {
                    maxLng = point.getDecimalLongitude();
                }
            }
        }

        polygonEntity.setMinLat(minLat);
        polygonEntity.setMaxLat(maxLat);
        polygonEntity.setMinLng(minLng);
        polygonEntity.setMaxLng(maxLng);
        polygonEntity.setResource(resourceEntity);

        return polygonEntity;
    }

    /**
     * Create list of Area objects from poligonAreaDTO
     * 
     * @param poligonAreaDTO
     * @return List<Area>
     */

    private String createCoordinatesJson(PoligonAreaDTO poligonAreaDTO) {
        String coordinates;
        List<PointDTO> pointDTOs = new ArrayList<>();
        for (PointAreaDTO point : poligonAreaDTO.getPoints()) {
            if (point.getOrderNumber() != 0) {
                PointDTO pointDTO = new PointDTO();
                pointDTO.setLat(point.getDecimalLatitude());
                pointDTO.setLng(point.getDecimalLongitude());
                pointDTOs.add(pointDTO);
            }
        }
        Gson gson = new Gson();
        coordinates = gson.toJson(pointDTOs);
        return coordinates;
    }

    /**
     * Create list of ResourceDiscreteValue objects given in resourceDTO
     * 
     * @param resourceDTO
     * @param resourceEntity
     * @return List<ResourceDiscreteValue>
     */
    private List<ResourceDiscreteValue> parseToDiscreteValueList(ResourceDTO resourceDTO, Resource resourceEntity) {
        discreteValueRepository.deleteByResource(resourceEntity);

        List<ResourceDiscreteValue> resourceDiscreteValues = new ArrayList<ResourceDiscreteValue>();
        for (ResourceDiscreteValueDTO discValueDTO : resourceDTO.getResourceDiscrete()) {
            DiscreteParameter discreteParameter = discreteParameterRepository
                    .findByResourceAndName(discValueDTO.getDiscreteParameterDescription(), resourceEntity.getType());
            for (ValueDiscreteDTO valueDiscrete : discValueDTO.getValueDiscretes()) {
                ResourceDiscreteValue discreteValue = new ResourceDiscreteValue();
                discreteValue.setResource(resourceEntity);
                discreteValue.setDiscreteParameter(discreteParameter);
                discreteValue.setValue(valueDiscrete.getValue());
                discreteValue.setComment(valueDiscrete.getComment());
                resourceDiscreteValues.add(discreteValue);
            }
        }
        return resourceDiscreteValues;
    }

    /**
     * Create list of ResourceLinearValue objects given in resourceDTO
     * 
     * @param resourceDTO
     * @param resourceEntity
     * @return List<ResourceLinearValue>
     */
    private List<ResourceLinearValue> parseToLinearValueList(ResourceDTO resourceDTO, Resource resourceEntity) {
        linearValueRepository.deleteByResource(resourceEntity);

        List<ResourceLinearValue> resourceLinearValues = new ArrayList<ResourceLinearValue>();
        for (ResourceLinearValueDTO linValueDTO : resourceDTO.getResourceLinear()) {
            LinearParameter linParameter = linearParameterRepository
                    .findByResourceAndName(linValueDTO.getLinearParameterDescription(), resourceEntity.getType());
            for (SegmentLinearDTO segment : linValueDTO.getSegments()) {
                ResourceLinearValue linearValue = new ResourceLinearValue();
                linearValue.setResource(resourceEntity);
                linearValue.setLinearParameter(linParameter);
                linearValue.setMinValue(segment.getBegin());
                linearValue.setMaxValue(segment.getEnd());
                resourceLinearValues.add(linearValue);
            }
        }
        return resourceLinearValues;
    }

    /**
     * Method return ResourseDTO object with all fields based on resourceEntity
     * 
     * @param resourceEntity
     * @return resourceDTO
     */
    private ResourceDTO formResourceDTO(Resource resourceEntity) {
        /* fill standard resource fields */
        ResourceDTO resourceDTO = fillBasicParametersResourceDTO(resourceEntity);

        /* fill linear values of resource */
        resourceDTO = fillLinearValuesDTO(resourceDTO, resourceEntity);

        /* fill discrete values of resource */
        resourceDTO = fillDiscreteValuesDTO(resourceDTO, resourceEntity);

        /* fill area values of resource */
        resourceDTO = fillAreaDTO(resourceDTO, resourceEntity);
        return resourceDTO;
    }

    /**
     * Method create ResourceDTO with basics parameters from resoureEntity
     * 
     * @param resourceEntity
     * @return ResourceDTO
     */
    private ResourceDTO fillBasicParametersResourceDTO(Resource resourceEntity) {
        ResourceDTO resourceDTO = new ResourceDTO();
        resourceDTO.setResourceId(resourceEntity.getResourcesId());
        resourceDTO.setDescription(resourceEntity.getDescription());
        resourceDTO.setDate(resourceEntity.getDate());
        resourceDTO.setIdentifier(resourceEntity.getIdentifier());
        resourceDTO.setReasonInclusion(resourceEntity.getReasonInclusion());
        resourceDTO.setRegistratorName(resourceEntity.getRegistrator().toString());
        resourceDTO.setTomeIdentifier(resourceEntity.getTome().getIdentifier());
        resourceDTO.setResourceType(resourceEntity.getType().getTypeName());
        return resourceDTO;
    }

    /**
     * Fill LinearValues list in ResourceDTO object
     * 
     * @param resourceDTO
     * @param resourceEntity
     * @return ResourceDTO
     */
    private ResourceDTO fillLinearValuesDTO(ResourceDTO resourceDTO, Resource resourceEntity) {
        /* find linear parameters of resource type */
        List<LinearParameter> linearParameters = linearParameterRepository.findByResourceType(resourceEntity.getType());
        /* find linear values of given resource */
        List<ResourceLinearValue> linearValues = linearValueRepository.findByResource(resourceEntity);
        List<ResourceLinearValueDTO> resLinDTOs = new ArrayList<ResourceLinearValueDTO>();
        for (LinearParameter lp : linearParameters) {
            ResourceLinearValueDTO resLinDTO = new ResourceLinearValueDTO();
            resLinDTO.setLinearParameterDescription(lp.getDescription());
            resLinDTO.setLinearParameterUnit(lp.getUnitName());
            List<SegmentLinearDTO> segments = new ArrayList<SegmentLinearDTO>();
            for (ResourceLinearValue linearValue : linearValues) {
                if (linearValue.getLinearParameter().getDescription().equals(lp.getDescription())) {
                    SegmentLinearDTO segment = new SegmentLinearDTO();
                    segment.setBegin(linearValue.getMinValue());
                    segment.setEnd(linearValue.getMaxValue());
                    segments.add(segment);
                }
            }
            resLinDTO.setSegments(segments);
            resLinDTOs.add(resLinDTO);
        }
        resourceDTO.setResourceLinear(resLinDTOs);
        return resourceDTO;
    }

    /**
     * Fill DiscreteValues list in ResourceDTO object
     * 
     * @param resourceDTO
     * @param resourceEntity
     * @return ResourceDTO
     */
    private ResourceDTO fillDiscreteValuesDTO(ResourceDTO resourceDTO, Resource resourceEntity) {
        /* find discrete parameters of resource type */
        List<DiscreteParameter> discreteParameters = discreteParameterRepository
                .findAllByResourceType(resourceEntity.getType());
        /* find discrete values of given resource */
        List<ResourceDiscreteValue> discreteValues = discreteValueRepository.findByResource(resourceEntity);
        List<ResourceDiscreteValueDTO> resDiscDTOs = new ArrayList<ResourceDiscreteValueDTO>();
        for (DiscreteParameter dp : discreteParameters) {
            ResourceDiscreteValueDTO resDisDTO = new ResourceDiscreteValueDTO();
            resDisDTO.setDiscreteParameterDescription(dp.getDescription());
            resDisDTO.setDiscreteParameterUnit(dp.getUnitName());
            resDisDTO.setCalculatedParameter(dp.getCalculatedParameter());
            List<ValueDiscreteDTO> valuediscretes = new ArrayList<>();
            for (ResourceDiscreteValue dv : discreteValues) {
                if (dv.getDiscreteParameter().getDiscreteParameterId().equals(dp.getDiscreteParameterId())) {
                    ValueDiscreteDTO valueDiscrete = new ValueDiscreteDTO();
                    valueDiscrete.setValue(dv.getValue());
                    valueDiscrete.setComment(dv.getComment());
                    valuediscretes.add(valueDiscrete);
                }

            }
            resDisDTO.setValueDiscretes(valuediscretes);
            resDiscDTOs.add(resDisDTO);
        }
        resourceDTO.setResourceDiscrete(resDiscDTOs);
        return resourceDTO;
    }

    /**
     * Fill Area in ResourceDTO object
     * 
     * @param resourceDTO
     * @param resourceEntity
     * @return ResourceDTO
     */
    private ResourceDTO fillAreaDTO(ResourceDTO resourceDTO, Resource resourceEntity) {
        List<Polygon> polygons = polygonRepository.findByResource(resourceEntity);
        /* fill additional resource fields */
        ResourceAreaDTO resourceArea = new ResourceAreaDTO();
        List<PoligonAreaDTO> poligonsDTO = new ArrayList<PoligonAreaDTO>();

        for (Polygon polygon : polygons) {
//            List<Area> areas = new ArrayList<>();
//            areas.addAll(areaRepository.findByPolygon(polygon));

            Gson gson = new Gson();

            PoligonAreaDTO poligon = new PoligonAreaDTO();
            poligon.setPolygonId(polygon.getId());
            List<PointAreaDTO> pointDTOs = new ArrayList<>();
            List<PointDTO> coordinates = gson.fromJson(polygon.getCoordinates(), new TypeToken<List<PointDTO>>(){}.getType());

            int pointNumber = 1;
            for (PointDTO coordinate : coordinates) {
                PointAreaDTO pointDTO = new PointAreaDTO();
                pointDTO.setOrderNumber(pointNumber++);
                pointDTO.setLatitudeValues(coordinate.getLat());
                pointDTO.setLongitudeValues(coordinate.getLng());
                pointDTOs.add(pointDTO);
            }
            poligon.setPoints(pointDTOs);
            poligonsDTO.add(poligon);
            logger.info("==========================================");
            logger.info("Gson list: " + coordinates);
        }
        resourceArea.setPoligons(poligonsDTO);
        resourceDTO.setResourceArea(resourceArea);
        return resourceDTO;
    }

    /**
     * Method increment registration number of resource for registrator which
     * add new resource
     * 
     * @param login
     *            - login of registrator authorized
     */
    private void incrementRegistrationNumber(String login) {
        User user = userRepository.findUserByLogin(login);
        ResourceNumber resourceNumber = resourceNumberRepository.findResourceNumberByUser(user);
        Integer incrementedNumber = resourceNumber.getNumber() + 1;
        resourceNumber.setNumber(incrementedNumber);
        resourceNumberRepository.save(resourceNumber);
    }

    @Override
    public Integer countAllByAreaLimits(Double minLat, Double maxLat, Double minLng, Double maxLng) {
        Integer count = polygonRepository.countByLimits(minLat, maxLat, minLng, maxLng);
        return count;
    }

    @Override
    public Integer countAllByPoint(Double lat,Double lng) {
        Integer count = polygonRepository.countByPoint(lat, lng);
        return count;
    }

    @Override
    public boolean userCanEditResource(ResourceDTO resourceDTO)  {
        if (resourceDTO == null) {
            return false;
        }

        Resource resourceEntity = resourceRepository.findByIdentifier(resourceDTO.getIdentifier());

        User user = userService.getLoggedUser();
        if (user == null) {
            return false;
        }

        Calendar today = Calendar.getInstance(settingsService.getTimeZone());
        Calendar createdAt = resourceEntity.getCreatedAt();

        boolean sameDate =  ((today != null) && (createdAt != null)
                && (today.get(Calendar.YEAR) == createdAt.get(Calendar.YEAR))
                && (today.get(Calendar.MONTH) == createdAt.get(Calendar.MONTH))
                && (today.get(Calendar.DATE) == createdAt.get(Calendar.DATE))
        );

        return user.equals(resourceEntity.getRegistrator()) && sameDate;
    }

}
