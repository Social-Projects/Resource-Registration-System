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
	private Integer ResourceDiscreteId;

	@ManyToOne
	@JoinColumn(name = "resource_id", nullable = false)
	private Resource resource;
	
	
	@Column(name = "value", nullable = false)
	private Double value;
	
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
		return ResourceDiscreteId;
	}



	public void setResourceDiscreteId(Integer resourceDiscreteId) {
		ResourceDiscreteId = resourceDiscreteId;
	}



	public DiscreteParameter getDiscreteParameter() {
		return discreteParameter;
	}



	public void setDiscreteParameter(DiscreteParameter discreteParameter) {
		this.discreteParameter = discreteParameter;
	}

}
