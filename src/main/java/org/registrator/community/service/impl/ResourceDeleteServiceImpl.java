package org.registrator.community.service.impl;

import org.registrator.community.dao.ResourceRepository;
import org.registrator.community.entity.Resource;
import org.registrator.community.service.ResourceDeleteService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service class implements ResourceDeleteService interface.
 * Give functionality to delete resource.
 * @author Ann
 *
 */
@Service
public class ResourceDeleteServiceImpl implements ResourceDeleteService {

	@Autowired
	private Logger logger;
	@Autowired
	private ResourceRepository resourceRepository;
	
	/**
     * Method delete resource with given identifier.
     * 
     * @param resourceIdentifier - identifier of the resource.
     */
	@Transactional
	@Override
	public void deleteResource(String resourceIdentifier) {
		logger.info("begin deleteResource, param resourceIdentifier = " + resourceIdentifier);
		Resource resource = resourceRepository.findByIdentifier(resourceIdentifier);
		resourceRepository.delete(resource);
		logger.info("Resource with identifier " + resourceIdentifier + "was succesfully deleted");

	}

}
