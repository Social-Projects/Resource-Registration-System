package org.registrator.community.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "linear_parameters")
public class LinearParameter implements Serializable {


	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "linear_parameter_id")
	@GeneratedValue
	private Integer linearParameterId;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "resource_type_id", nullable = false)
	private ResourceType resourceType;

	@Column(name = "description", nullable = false)
	private String description;
	
	@Column(name = "unit_name", nullable = false)
	private String unitName ;
	
	public LinearParameter() {
		
	}
	
	

	public LinearParameter(ResourceType resourceType, String description,
			String unitName) {

		this.resourceType = resourceType;
		this.description = description;
		this.unitName = unitName;
	}

	public Integer getLinearParameterId() {
		return linearParameterId;
	}



	public void setLinearParameterId(Integer linearParameterId) {
		this.linearParameterId = linearParameterId;
	}



	public ResourceType getResourceType() {
		return resourceType;
	}

	public void setResourceType(ResourceType resourceType) {
		this.resourceType = resourceType;
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
