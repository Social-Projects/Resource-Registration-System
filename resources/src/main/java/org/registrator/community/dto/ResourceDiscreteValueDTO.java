package org.registrator.community.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.AutoPopulatingList;

public class ResourceDiscreteValueDTO {
	//private DiscreteParameterDTO discreteParameterDTO;
	private List<Double> values = new ArrayList<Double>();
 
	private String discreteParameterDescription;
	private String discreteParameterUnit;
	
	public ResourceDiscreteValueDTO() {
		
	}

	public String getDiscreteParameterDescription() {
		return discreteParameterDescription;
	}

	public void setDiscreteParameterDescription(String discreteParameterDescription) {
		this.discreteParameterDescription = discreteParameterDescription;
	}

	public String getDiscreteParameterUnit() {
		return discreteParameterUnit;
	}

	public void setDiscreteParameterUnit(String discreteParameterUnit) {
		this.discreteParameterUnit = discreteParameterUnit;
	}

	
	public List<Double> getValues() {
		return values;
	}

	public void setValues(List<Double> values) {
		this.values = values;
	}

/*	public DiscreteParameterDTO getDiscreteParameterDTO() {
		return discreteParameterDTO;
	}

	public void setDiscreteParameterDTO(DiscreteParameterDTO discreteParameterDTO) {
		this.discreteParameterDTO = discreteParameterDTO;
	}*/

	/*public String toString() {

		return "\n" + "Дискретні параметри: " + discreteParameterDTO + "\n"+
				"Значення: " + values.toString();

		StringBuilder result = new StringBuilder();
		result.append(discreteParameterDTO.toString());
		for (int i = 0, valuesSize = values.size(); i < valuesSize; i++) {
			Double value = values.get(i);
			result.append("Значення " + i + ": " + value + "\n");
		}
		return result.toString();

	}*/	
}
