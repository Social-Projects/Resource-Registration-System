package org.registrator.community.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.registrator.community.dao.DiscreteParameterRepository;
import org.registrator.community.dao.LinearParameterRepository;
import org.registrator.community.dao.ResourceTypeRepository;
import org.registrator.community.dto.ResourceTypeDTO;
import org.registrator.community.dto.TypeParameterDTO;
import org.registrator.community.entity.DiscreteParameter;
import org.registrator.community.entity.LinearParameter;
import org.registrator.community.entity.ResourceType;
import org.registrator.community.service.ResourceTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResourceTypeServiceImpl implements ResourceTypeService {

	@Autowired
	private ResourceTypeRepository resourceTypeRepository;
	@Autowired
	private LinearParameterRepository linearParameterRepository;
	@Autowired
	private DiscreteParameterRepository discreteParameterRepository;

	@Override
	public ResourceType addResourceType(ResourceType resourceType) {
		ResourceType savedResourceType = resourceTypeRepository.saveAndFlush(resourceType);
		return savedResourceType;
	}

	@Override
	public ResourceType findById(Integer id) {
		return resourceTypeRepository.findOne(id);
	}

	@Override
	public ResourceType findByName(String name) {
		return resourceTypeRepository.findByName(name);
	}

	@Override
	public ResourceTypeDTO editResourceType(ResourceTypeDTO resourceTypeDTO) {
		/*
		 * ResourceType rt =
		 * resourceTypeRepository.findByName(resourceTypeDTO.getTypeName());
		 * rt.setTypeName("newname(edited)");
		 * rt.setTypeName(resourceTypeDTO.getTypeName()); rt =
		 * resourceTypeRepository.saveAndFlush(resourceTypeEntity);
		 * List<TypeParameterDTO> ltp = resourceTypeDTO.getParameters();
		 * List<LinearParameter> lp = new ArrayList<LinearParameter>();
		 * List<DiscreteParameter> dp = new ArrayList<DiscreteParameter>();
		 * for(TypeParameterDTO tpar: ltp){
		 * if((tpar.getParametersType()).equals("linearParameters")){ lp.add(new
		 * LinearParameter(resourceTypeEntity, tpar.getDescription(),
		 * tpar.getUnitName())); }
		 * if((tpar.getParametersType()).equals("discreteParameters")){
		 * dp.add(new DiscreteParameter(resourceTypeEntity,
		 * tpar.getDescription(), tpar.getUnitName())); } }
		 * linearParameterRepository.save(lp);
		 * discreteParameterRepository.save(dp);
		 * resourceTypeRepository.save(rt);
		 */
		return resourceTypeDTO;
	}

	@Override
	public void delete(ResourceType resourceType) {
		resourceTypeRepository.delete(resourceType);
	}

	@Override
	public List<ResourceType> findAll() {
		List<ResourceType> listOfResourceType = resourceTypeRepository.findAll();
		return listOfResourceType;
	}

	@Override
	public ResourceTypeDTO addResourceTypeDTO(ResourceTypeDTO resourceTypeDTO) {
		ResourceType resourceTypeEntity = new ResourceType();
		resourceTypeEntity.setTypeName(resourceTypeDTO.getTypeName());
		resourceTypeEntity = resourceTypeRepository.saveAndFlush(resourceTypeEntity);
		List<TypeParameterDTO> ltp = resourceTypeDTO.getParameters();
		List<LinearParameter> lp = new ArrayList<LinearParameter>();
		List<DiscreteParameter> dp = new ArrayList<DiscreteParameter>();

		for (TypeParameterDTO tpar : ltp) {
			if ((tpar.getParametersType()).equals("linearParameters")) {
				lp.add(new LinearParameter(resourceTypeEntity, tpar.getDescription(), tpar.getUnitName()));
			}
			if ((tpar.getParametersType()).equals("discreteParameters")) {
				dp.add(new DiscreteParameter(resourceTypeEntity, tpar.getDescription(), tpar.getUnitName()));
			}

		}
		linearParameterRepository.save(lp);
		discreteParameterRepository.save(dp);

		return resourceTypeDTO;

	}
}
