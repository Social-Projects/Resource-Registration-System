package org.registrator.community.service;

import org.registrator.community.entity.LinearParameter;
import org.registrator.community.entity.Resource;
import org.registrator.community.entity.ResourceLinearValue;

import java.util.List;
import java.util.Set;

/**
 * Created by Oleksiy on 24.12.2015.
 */
public interface ResourceLinearValueService {
    List<ResourceLinearValue> findByResource(Resource resource);

    List<ResourceLinearValue> findAllByLinearParameter (LinearParameter linearParameter);

    List<ResourceLinearValue> findAllByValueAndLinearParameter (Double d, LinearParameter linearParameter);

    Set<String> findResourcesbyLinearParam(Integer linearParamId, Double searchValue);

    Set<String> findResourcesByLinParamList (List<Integer> paramIds, List<Double> searchValues);
}
