package org.registrator.community.service.impl;

import org.registrator.community.dao.DiscreteParameterRepository;
import org.registrator.community.dao.ResourceDiscreteValueRepository;
import org.registrator.community.entity.DiscreteParameter;
import org.registrator.community.entity.Resource;
import org.registrator.community.entity.ResourceDiscreteValue;
import org.registrator.community.service.ResourceDiscreteValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Oleksiy on 24.12.2015.
 */
@Service
public class ResourceDiscreteValueServiceImpl implements ResourceDiscreteValueService {
    @Autowired
    private ResourceDiscreteValueRepository resourceDiscreteValueRepository;

    @Autowired
    private DiscreteParameterRepository discreteParameterRepository;

    @Override
    public List<ResourceDiscreteValue> findByResource(Resource resource) {
        return resourceDiscreteValueRepository.findByResource(resource);
    }

    @Override
    public List<ResourceDiscreteValue> findAllByDiscreteParameter(DiscreteParameter discreteParameter) {
        return resourceDiscreteValueRepository.findAllByDiscreteParameter(discreteParameter);
    }

    @Override
    public List<ResourceDiscreteValue> findAllByValueAndDiscreteParameter(Double d, DiscreteParameter discreteParameter) {
        return resourceDiscreteValueRepository.findAllByValueAndDiscreteParameter(d, discreteParameter);
    }

    @Override
    public List<ResourceDiscreteValue> findAllBySmallerValueAndDiscreteParameter(Double d, DiscreteParameter discreteParameter) {
        return resourceDiscreteValueRepository.findAllBySmallerValueAndDiscreteParameter(d, discreteParameter);
    }

    @Override
    public List<ResourceDiscreteValue> findAllByBiggerValueAndDiscreteParameter(Double d, DiscreteParameter discreteParameter) {
        return resourceDiscreteValueRepository.findAllByBiggerValueAndDiscreteParameter(d, discreteParameter);
    }

    @Override
    public Set<String> findResourcesByDiscreteParam(Integer discreteParameterId,
                                                    String compareSign,
                                                    Double searchValue) {
        DiscreteParameter discreteParameter = discreteParameterRepository.findByDiscreteParameterId(discreteParameterId);
        Set<ResourceDiscreteValue> values = new HashSet<>();
        Set<String> identifiers = new HashSet<>();

        if ("less".equals(compareSign)) {
            values.addAll(findAllBySmallerValueAndDiscreteParameter(searchValue, discreteParameter));
        } else if ("greater".equals(compareSign)) {
            values.addAll(findAllByBiggerValueAndDiscreteParameter(searchValue, discreteParameter));
        } else {
            values.addAll(findAllByValueAndDiscreteParameter(searchValue, discreteParameter));
        }

        for (ResourceDiscreteValue value : values) {
            identifiers.add(value.getResource().getIdentifier());
        }

        return identifiers;
    }

    @Override
    public Set<String> findResourcesByParamsList(List<Integer> paramsId,
                                                 List<String> compareSign,
                                                 List<Double> searchValue) {
        Set<String> identifiers = new HashSet<>();
        for (int i = 0; i < paramsId.size(); i++) {
            if (searchValue.get(i) != null) {
                Set<String> foundResources = findResourcesByDiscreteParam(
                        paramsId.get(i),
                        compareSign.get(i),
                        searchValue.get(i));
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

