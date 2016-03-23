package org.registrator.community.entity;

import javax.persistence.*;

@Entity
@Table(name = "resource_parameters")
public class ResourceParameters {

	@Id
	@Column(name = "resource_parameters_id")
	@GeneratedValue
	private Integer ResourceParameterId;

	@ManyToOne
	@JoinColumn(name = "resource_type_id", nullable = false)
	private ResourceType resourceType;

	@ManyToOne
	@JoinColumn(name = "parameter_id", nullable = false)
	private LinearParameter lineSize;
	
	public ResourceParameters() {
		
	}

	// add getters and setters
}
