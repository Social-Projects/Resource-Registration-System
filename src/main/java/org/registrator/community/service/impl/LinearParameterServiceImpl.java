package org.registrator.community.service.impl;

import org.registrator.community.dao.LinearParameterRepository;
import org.registrator.community.entity.LinearParameter;
import org.registrator.community.entity.ResourceType;
import org.registrator.community.service.LinearParameterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Oleksiy on 24.12.2015.
 */

@Service
public class LinearParameterServiceImpl implements LinearParameterService {
    @Autowired
    LinearParameterRepository linearParameterRepository;

    @Override
    public LinearParameter findById(Integer id) {
        return linearParameterRepository.findByLinearParameterId(id);
    }

    @Override
    public List<LinearParameter> findAllByResourceType(ResourceType resourceType) {
        return linearParameterRepository.findByResourceType(resourceType);
    }
}
