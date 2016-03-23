package org.registrator.community.service;

import java.util.List;

import org.registrator.community.dto.ResourceTypeDTO;
import org.registrator.community.entity.ResourceType;


public interface ResourceTypeService{

	ResourceType addResourceType (ResourceType resourceType);
	void delete(ResourceType resourceType);
	ResourceType findByName(String name);
	List<ResourceType> findAll();
	ResourceTypeDTO editResourceType(ResourceTypeDTO resourceTypeDTO);
	ResourceType findById(Integer id);
	ResourceTypeDTO addResourceTypeDTO(ResourceTypeDTO resourceTypeDTO);
}

