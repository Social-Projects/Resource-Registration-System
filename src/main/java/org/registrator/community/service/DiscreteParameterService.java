package org.registrator.community.service;

import org.registrator.community.entity.DiscreteParameter;
import org.registrator.community.entity.ResourceType;

import java.util.List;

/**
 * Created by Oleksiy on 23.12.2015.
 */

public interface DiscreteParameterService {
    DiscreteParameter findById (Integer id);
    List<DiscreteParameter> findAllByResourceType(ResourceType resourceType);
}
