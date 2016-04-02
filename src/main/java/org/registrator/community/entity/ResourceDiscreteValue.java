package org.registrator.community.entity;


import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "resource_discrete_values")
public class ResourceDiscreteValue implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "resource_discrete_value_id")
    @GeneratedValue
    private Integer resourceDiscreteId;

    @ManyToOne
    @JoinColumn(name = "resource_id", nullable = false)
    private Resource resource;
    
    
    @Override
    public String toString() {
        return "ResourceDiscreteValue [resourceDiscreteId=" + resourceDiscreteId + ", resource=" + resource + ", value="
                + value + ", comment=" + comment + ", discreteParameter=" + discreteParameter + "]";
    }

    @Column(name = "value", nullable = false)
    private Double value;
    
    @Column(name = "comment")
    private String comment;
    
    @ManyToOne
    @JoinColumn(name = "discrete_parameter_id", nullable = false)
    private DiscreteParameter discreteParameter;    

    public ResourceDiscreteValue() {
        
    }
    
    public ResourceDiscreteValue(Resource resource, DiscreteParameter discreteParameter,
            Double value) {
        this.resource = resource;
        this.discreteParameter = discreteParameter;
        this.value = value;
    }



    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }



    public Integer getResourceDiscreteId() {
        return resourceDiscreteId;
    }



    public void setResourceDiscreteId(Integer resourceDiscreteId) {
        this.resourceDiscreteId = resourceDiscreteId;
    }



    public DiscreteParameter getDiscreteParameter() {
        return discreteParameter;
    }



    public void setDiscreteParameter(DiscreteParameter discreteParameter) {
        this.discreteParameter = discreteParameter;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}
