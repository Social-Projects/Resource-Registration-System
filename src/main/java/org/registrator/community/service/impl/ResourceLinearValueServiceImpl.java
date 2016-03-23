package org.registrator.community.service.impl;

import org.registrator.community.dao.LinearParameterRepository;
import org.registrator.community.dao.ResourceLinearValueRepository;
import org.registrator.community.entity.LinearParameter;
import org.registrator.community.entity.Resource;
import org.registrator.community.entity.ResourceLinearValue;
import org.registrator.community.service.ResourceLinearValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Oleksiy on 24.12.2015.
 */
@Service
public class ResourceLinearValueServiceImpl implements ResourceLinearValueService {
    @Autowired
    private ResourceLinearValueRepository resourceLinearValueRepository;

    @Autowired
    private LinearParameterRepository linearParameterRepository;

    @Override
    public List<ResourceLinearValue> findAllByLinearParameter(LinearParameter linearParameter) {
        return resourceLinearValueRepository.findAllByLinearParameter(linearParameter);
    }

    @Override
    public List<ResourceLinearValue> findByResource(Resource resource) {
        return resourceLinearValueRepository.findByResource(resource);
    }

    @Override
    public List<ResourceLinearValue> findAllByValueAndLinearParameter(Double d, LinearParameter linearParameter) {
        return resourceLinearValueRepository.findByValueAndLinearParameterId(d, linearParameter);
    }

    @Override
    public Set<String> findResourcesbyLinearParam(Integer linearParamId, Double searchValue) {
        LinearParameter linearParameter = linearParameterRepository.findByLinearParameterId(linearParamId);
        List<ResourceLinearValue> values = findAllByValueAndLinearParameter(searchValue,linearParameter);
        Set<String> resources = new HashSet<>();

        for (ResourceLinearValue value : values) {
            resources.add(value.getResource().getIdentifier());
        }

        return resources;
    }

    @Override
    public Set<String> findResourcesByLinParamList (List<Integer> paramIds, List<Double> searchValues) {
        Set<String> identifiers = new HashSet<>();

        for (int i = 0; i < paramIds.size(); i++) {
            if (searchValues.get(i) != null) {
                Set<String> foundResources = findResourcesbyLinearParam(
                        paramIds.get(i), searchValues.get(i));
                if (identifiers.size() > 0) {
                    identifiers.retainAll(foundResources);
                } else {
                    identifiers.addAll(foundResources);
                }
            }
        }

        return identifiers;
    }
}
