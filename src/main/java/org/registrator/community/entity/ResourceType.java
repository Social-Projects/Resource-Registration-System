package org.registrator.community.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Table(name = "resource_types")
public class ResourceType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "resource_type_id")
    @GeneratedValue
    private Integer typeId;

    @Column(name = "type_name", unique = true, nullable = false)
    private String typeName;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "resourceType")
    @Cascade({ CascadeType.REMOVE })
    private List<DiscreteParameter> discreteParameters = new ArrayList<DiscreteParameter>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "resourceType")
    @Cascade({ CascadeType.REMOVE })
    private List<LinearParameter> linearParameters = new ArrayList<LinearParameter>();

    public ResourceType() {
    }

    public ResourceType(String typeName) {
        this.typeName = typeName;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public List<DiscreteParameter> getDiscreteParameters() {
        return discreteParameters;
    }

    public void setDiscreteParameters(List<DiscreteParameter> discreteParameters) {
        this.discreteParameters = discreteParameters;
    }

    public List<LinearParameter> getLinearParameters() {
        return linearParameters;
    }

    public void setLinearParameters(List<LinearParameter> linearParameters) {
        this.linearParameters = linearParameters;
    }

}
