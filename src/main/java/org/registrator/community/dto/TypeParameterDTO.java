package org.registrator.community.dto;

public class TypeParameterDTO {

    private String parametersType;

    private String description;

    private String unitName;

    private boolean sortable;

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

    public boolean isSortable() {
        return sortable;
    }

    public void setSortable(boolean sortable) {
        this.sortable = sortable;
    }

    public String getParametersType() {
        return parametersType;
    }

    public void setParametersType(String parametersType) {
        this.parametersType = parametersType;
    }

}
