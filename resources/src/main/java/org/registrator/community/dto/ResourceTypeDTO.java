package org.registrator.community.dto;

import java.util.List;

public class ResourceTypeDTO {
	
	private String typeName;
	
	private List<TypeParameterDTO> parameters;
	
	public ResourceTypeDTO() {
	}
	
/*	public ResourceTypeDTO(String typeName, List<LinearParameterDTO> linearParameters,
			List<DiscreteParameterDTO> discreteParameters) {
	}*/
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public List<TypeParameterDTO> getParameters() {
		return parameters;
	}
	public void setParameters(List<TypeParameterDTO> parameters) {
		this.parameters = parameters;
	}
	
}