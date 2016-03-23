package org.registrator.community.dto;

public class DiscreteParameterDTO {
	
	private String description;
	private String unitName;
	
	public DiscreteParameterDTO() {
		
	}
	
	public DiscreteParameterDTO(String description, String unitName) {
		super();
		this.description = description;
		this.unitName = unitName;
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
	
	public String toString(){
		String result;
		result = String.valueOf("Опис дискретних параметрів: " + description + "\n"
				+ "Одиниці виміру: " + unitName + "\n");
		return result;
		
	}
	
}
