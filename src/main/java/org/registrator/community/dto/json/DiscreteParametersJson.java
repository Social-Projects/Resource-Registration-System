package org.registrator.community.dto.json;

/**
 * Created by Oleksiy on 23.12.2015.
 */
public class DiscreteParametersJson {
    private Integer discreteParameterId;
    private Integer resourceTypeId;
    private String description;
    private String unitName ;

    public Integer getDiscreteParameterId() {
        return discreteParameterId;
    }

    public void setDiscreteParameterId(Integer discreteParameterId) {
        this.discreteParameterId = discreteParameterId;
    }

    public Integer getResourceTypeId() {
        return resourceTypeId;
    }

    public void setResourceTypeId(Integer resourceTypeId) {
        this.resourceTypeId = resourceTypeId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }
}
