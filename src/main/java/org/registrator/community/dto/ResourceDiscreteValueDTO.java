package org.registrator.community.dto;

import org.registrator.community.enumeration.CalculatedParameter;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

public class ResourceDiscreteValueDTO {
    
    
    @Valid
    private List<ValueDiscreteDTO> valueDiscretes = new ArrayList<ValueDiscreteDTO>();
 
    private String discreteParameterDescription;
    
    private String discreteParameterUnit;

    private CalculatedParameter calculatedParameter;

    public ResourceDiscreteValueDTO() {
        
    }

    public String getDiscreteParameterDescription() {
        return discreteParameterDescription;
    }

    public void setDiscreteParameterDescription(String discreteParameterDescription) {
        this.discreteParameterDescription = discreteParameterDescription;
    }

    public String getDiscreteParameterUnit() {
        return discreteParameterUnit;
    }

    public void setDiscreteParameterUnit(String discreteParameterUnit) {
        this.discreteParameterUnit = discreteParameterUnit;
    }

    public List<ValueDiscreteDTO> getValueDiscretes() {
        return valueDiscretes;
    }

    public void setValueDiscretes(List<ValueDiscreteDTO> valueDiscretes) {
        this.valueDiscretes = valueDiscretes;
    }

    public CalculatedParameter getCalculatedParameter() {
        return calculatedParameter;
    }

    public void setCalculatedParameter(CalculatedParameter calculatedParameter) {
        this.calculatedParameter = calculatedParameter;
    }
}
