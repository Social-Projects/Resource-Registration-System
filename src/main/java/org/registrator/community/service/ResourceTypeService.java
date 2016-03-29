package org.registrator.community.service;

import java.util.List;

import org.registrator.community.dto.ResourceTypeDTO;
import org.registrator.community.entity.ResourceType;
import org.springframework.security.access.prepost.PreAuthorize;


public interface ResourceTypeService{
    
	ResourceType addResourceType (ResourceType resourceType);
	
	boolean delete(ResourceType resourceType);
	
	ResourceType findByName(String name);
	@PreAuthorize("hasRole('ROLE_REGISTRATOR') or hasRole('ROLE_USER')")
	List<ResourceType> findAll();
	
	ResourceTypeDTO editResourceType(ResourceTypeDTO resourceTypeDTO);
	
	ResourceType findById(Integer id);
	
	ResourceTypeDTO addResourceTypeDTO(ResourceTypeDTO resourceTypeDTO);
}

