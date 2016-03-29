package org.registrator.community.service;

import org.registrator.community.entity.LinearParameter;
import org.registrator.community.entity.ResourceType;

import java.util.List;

/**
 * Created by Oleksiy on 24.12.2015.
 */
public interface LinearParameterService {
    LinearParameter findById (Integer id);
    List<LinearParameter> findAllByResourceType (ResourceType resourceType);
}
