package org.registrator.community.dto;

import org.registrator.community.entity.ResourceParameter;
import org.registrator.community.enumeration.ParameterValueCompare;

/**
 * Created by roman.golyuk on 12.03.2016.
 */
public class ResourceSearchParameter {
    private ResourceParameter parameterEntity;
    private ParameterValueCompare CompareOperation;
    private double value;

    public ResourceSearchParameter(ResourceParameter parameterEntity, ParameterValueCompare compareOperation, double value) {
        this.parameterEntity = parameterEntity;
        CompareOperation = compareOperation;
        this.value = value;
    }

    public ResourceParameter getParameterEntity() {
        return parameterEntity;
    }

    public ParameterValueCompare getCompareOperation() {
        return CompareOperation;
    }

    public double getValue() {
        return value;
    }
}
