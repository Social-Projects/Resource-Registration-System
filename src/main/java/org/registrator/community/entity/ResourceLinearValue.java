package org.registrator.community.entity;


import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "resource_linear_values")
public class ResourceLinearValue implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "resource_linear_param_id")
    @GeneratedValue
    private Integer StoreOfLineSizesId;

    @ManyToOne
    @JoinColumn(name = "resource_id", nullable = false)
    private Resource resource;
        
    @Column(name = "minimal_value", nullable = false)
    private Double minValue;

    @Column(name = "maximal_value", nullable = false)
    private Double maxValue;
    
    @ManyToOne
    @JoinColumn(name = "linear_parameter_id", nullable = false)
    private LinearParameter linearParameter;
    
    public ResourceLinearValue() {
        
    }
        
    public ResourceLinearValue(Resource resource, LinearParameter linearParameter, Double minValue,
            Double maxValue) {
        this.resource = resource;
        this.linearParameter = linearParameter;
        this.minValue = minValue;
        this.maxValue = maxValue;
    }



    public Integer getStoreOfLineSizesId() {
        return StoreOfLineSizesId;
    }

    public void setStoreOfLineSizesId(Integer storeOfLineSizesId) {
        StoreOfLineSizesId = storeOfLineSizesId;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public Double getMinValue() {
        return minValue;
    }

    public void setMinValue(Double minValue) {
        this.minValue = minValue;
    }

    public Double getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(Double maxValue) {
        this.maxValue = maxValue;
    }



    public LinearParameter getLinearParameter() {
        return linearParameter;
    }


    public void setLinearParameter(LinearParameter linearParameter) {
        this.linearParameter = linearParameter;
    }


}
