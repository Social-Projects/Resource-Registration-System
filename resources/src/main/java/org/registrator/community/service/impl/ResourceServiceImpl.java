package org.registrator.community.service.impl;


import java.util.ArrayList;
import java.util.List;

import org.registrator.community.dao.AreaRepository;
import org.registrator.community.dao.DiscreteParameterRepository;
import org.registrator.community.dao.LinearParameterRepository;
import org.registrator.community.dao.ResourceDiscreteValueRepository;
import org.registrator.community.dao.ResourceLinearValueRepository;
import org.registrator.community.dao.ResourceRepository;
import org.registrator.community.dao.ResourceTypeRepository;
import org.registrator.community.dao.TomeRepository;
import org.registrator.community.dto.PointAreaDTO;
import org.registrator.community.dto.PoligonAreaDTO;
import org.registrator.community.dto.ResourceAreaDTO;
import org.registrator.community.dto.ResourceDTO;
import org.registrator.community.dto.ResourceDiscreteValueDTO;
import org.registrator.community.dto.ResourceLinearValueDTO;
import org.registrator.community.dto.SegmentLinearDTO;
import org.registrator.community.entity.Area;
import org.registrator.community.entity.DiscreteParameter;
import org.registrator.community.entity.LinearParameter;
import org.registrator.community.entity.Resource;
import org.registrator.community.entity.ResourceDiscreteValue;
import org.registrator.community.entity.ResourceLinearValue;
import org.registrator.community.entity.ResourceType;
import org.registrator.community.enumeration.ResourceStatus;
import org.registrator.community.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResourceServiceImpl implements ResourceService {

	@Autowired
	ResourceRepository resourceRepository;

	@Autowired
	TomeRepository tomeRepository;

	@Autowired
	ResourceTypeRepository resourceTypeRepository;

	@Autowired
	AreaRepository areaRepository;

	@Autowired
	ResourceLinearValueRepository linearValueRepository;

	@Autowired
	ResourceDiscreteValueRepository discreteValueRepository;

	@Autowired
	LinearParameterRepository linearParameterRepository;

	@Autowired
	DiscreteParameterRepository discreteParameterRepository;

	@Override
	public ResourceDTO addNewResource(ResourceDTO resourceDTO) {
		Resource resourceEntity = new Resource();
		resourceEntity.setIdentifier(resourceDTO.getIdentifier());
		resourceEntity.setDescription(resourceDTO.getDescription());
		resourceEntity.setReasonInclusion(resourceDTO.getReasonInclusion());
		resourceEntity.setTome(tomeRepository.findTomeByIdentifier(resourceDTO.getTomeIdentifier()));
		resourceEntity.setRegistrator(tomeRepository.findTomeByIdentifier(resourceDTO.getTomeIdentifier()).getRegistrator());
		resourceEntity.setStatus(ResourceStatus.ACTIVE);
		ResourceType resourceType = resourceTypeRepository.findByName(resourceDTO.getResourceType().getTypeName());
		resourceEntity.setType(resourceType);
		resourceEntity.setDate(resourceDTO.getDate());
		resourceEntity = resourceRepository.save(resourceEntity);
		List<Area> areas = new ArrayList<Area>();
		for (PoligonAreaDTO poligonAreaDTO : resourceDTO.getResourceArea().getPoligons()) {
			for (PointAreaDTO point : poligonAreaDTO.getPoints()) {
				Area area = new Area();
				area.setResource(resourceEntity);
				area.setOrderNumber(point.getOrderNumber());
				area.setLatitude(point.getDecimalLatitude());
				area.setLongitude(point.getDecimalLongitude());
				areas.add(area);
			}
		}
		areaRepository.save(areas);

		List<ResourceLinearValue> resourceLinearValues = new ArrayList<ResourceLinearValue>();
		List<ResourceDiscreteValue> resourceDiscreteValues = new ArrayList<ResourceDiscreteValue>();
		if (!resourceDTO.getResourceLinear().isEmpty()) {
			System.out.println("NOT EMPTY");
			for (ResourceLinearValueDTO linValueDTO : resourceDTO.getResourceLinear()) {
				LinearParameter linParameter = linearParameterRepository
						.findByResourceAndName(linValueDTO.getLinearParameterDescription(), resourceType);
				for (SegmentLinearDTO segment : linValueDTO.getSegments()) {
					ResourceLinearValue linearValue = new ResourceLinearValue();
					linearValue.setResource(resourceEntity);
					linearValue.setLinearParameter(linParameter);
					linearValue.setMinValue(segment.getBegin());
					linearValue.setMaxValue(segment.getEnd());
					resourceLinearValues.add(linearValue);
				}
			}
			linearValueRepository.save(resourceLinearValues);
		}

		if (!resourceDTO.getResourceDiscrete().isEmpty()) {
			System.out.println("NOT EMPTY");
			for (ResourceDiscreteValueDTO discValueDTO : resourceDTO.getResourceDiscrete()) {
				DiscreteParameter discreteParameter = discreteParameterRepository
						.findByResourceAndName(discValueDTO.getDiscreteParameterDescription(), resourceType);

				for (Double value : discValueDTO.getValues()) {
					ResourceDiscreteValue discreteValue = new ResourceDiscreteValue();
					discreteValue.setResource(resourceEntity);
					discreteValue.setDiscreteParameter(discreteParameter);
					discreteValue.setValue(value);
					resourceDiscreteValues.add(discreteValue);
				}
			}
			discreteValueRepository.save(resourceDiscreteValues);
		}
		return resourceDTO;
	}

	@Override
	public ResourceDTO getResourceByIdentifier(String identifier) {
		Resource resourceEntity = resourceRepository.findResourceByIdentifier(identifier);
		List<LinearParameter> linearParameters = linearParameterRepository.
				findByResourceType(resourceEntity.getType());
		List<ResourceLinearValue> linearValues = linearValueRepository.
				findByResource(resourceEntity);
		List<ResourceDiscreteValue> discreteValues = discreteValueRepository.
				findByResource(resourceEntity);
		List<Area> areas = areaRepository.findByResource(resourceEntity);
			
		ResourceDTO resourceDTO = new ResourceDTO();
		resourceDTO.setDescription(resourceEntity.getDescription());
		resourceDTO.setDate(resourceEntity.getDate());
		resourceDTO.setIdentifier(resourceEntity.getIdentifier());
		resourceDTO.setReasonInclusion(resourceEntity.getReasonInclusion());
		resourceDTO.setRegistratorName(resourceEntity.getRegistrator().getFirstName() +
				resourceEntity.getRegistrator().getMiddleName() +resourceEntity.getRegistrator().getLastName());
		resourceDTO.setIdentifier(resourceEntity.getTome().getIdentifier());
		resourceDTO.setResourceType(resourceEntity.getType());		
			
		List<ResourceLinearValueDTO> resLinDTOs = new ArrayList<ResourceLinearValueDTO>();
		List<ResourceDiscreteValueDTO> resDiscDTOs = new ArrayList<ResourceDiscreteValueDTO>();
		ResourceAreaDTO resourceArea = new ResourceAreaDTO();
		List<PoligonAreaDTO> poligons = new ArrayList<PoligonAreaDTO>();
		
		for(LinearParameter lp :linearParameters) {
			ResourceLinearValueDTO resLinDTO = new ResourceLinearValueDTO();
			resLinDTO.setLinearParameterDescription(lp.getDescription());
			resLinDTO.setLinearParameterUnit(lp.getUnitName());
			List<SegmentLinearDTO> segments = new ArrayList<SegmentLinearDTO>();
			for (ResourceLinearValue linearValue : linearValues) {
				//System.out.println("PaRAM"linearValue.getLinearParameter());
				System.out.println(lp);
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
		
		for (ResourceDiscreteValue discreteValue : discreteValues) {
			
			//resDiscDTOs.add(e);
		}
//		resourceDTO.setResourceLinear(resLinDTOs);
		
		PoligonAreaDTO poligon = new PoligonAreaDTO();
		List<PointAreaDTO> pointDTOs = new ArrayList<PointAreaDTO>();
		for (Area area : areas) {
			// TODO write logic for polygon analysis
			PointAreaDTO pointDTO = new PointAreaDTO();
			pointDTO.setOrderNumber(area.getOrderNumber());
			pointDTO.setLatitudeValues(area.getLatitude());
			pointDTO.setLongitudeValues(area.getLongitude());
			pointDTOs.add(pointDTO);
			poligon.setPoints(pointDTOs);
		}
		poligons.add(poligon);
		resourceArea.setPoligons(poligons);
		resourceDTO.setResourceArea(resourceArea);
		
		System.out.println(resourceEntity.getDescription());
		return resourceDTO;
	}

    @Override
    public List<Resource> findByType(ResourceType type) {
        return resourceRepository.findByType(type);
    }

    @Override
    public long count() {
        return resourceRepository.count();
    }
}
