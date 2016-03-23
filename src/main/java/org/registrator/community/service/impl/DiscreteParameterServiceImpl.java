package org.registrator.community.service.impl;

import org.registrator.community.dao.DiscreteParameterRepository;
import org.registrator.community.entity.DiscreteParameter;
import org.registrator.community.entity.ResourceType;
import org.registrator.community.service.DiscreteParameterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Oleksiy on 23.12.2015.
 */
@Service
public class DiscreteParameterServiceImpl implements DiscreteParameterService{

    @Autowired
    private DiscreteParameterRepository  discreteParameterRepository;

    @Override
    public DiscreteParameter findById(Integer id) {
        return discreteParameterRepository.findByDiscreteParameterId(id);
    }

    @Override
    public List<DiscreteParameter> findAllByResourceType(ResourceType resourceType) {
        return discreteParameterRepository.findAllByResourceType(resourceType);
    }
}
