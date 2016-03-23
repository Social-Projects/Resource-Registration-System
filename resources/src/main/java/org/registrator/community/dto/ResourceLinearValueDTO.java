package org.registrator.community.dto;

import java.util.List;

public class ResourceLinearValueDTO {
//	private LinearParameterDTO linearParameterDTO;
	private List<SegmentLinearDTO> segments;
	private String linearParameterDescription;
	private String linearParameterUnit;
	
	
	public ResourceLinearValueDTO() {
	
	}
	
	public List<SegmentLinearDTO> getSegments() {
		return segments;
	}

	public void setSegments(List<SegmentLinearDTO> segments) {
		this.segments = segments;
	}

	public String getLinearParameterDescription() {
		return linearParameterDescription;
	}

	public void setLinearParameterDescription(String linearParameterDescription) {
		this.linearParameterDescription = linearParameterDescription;
	}

	public String getLinearParameterUnit() {
		return linearParameterUnit;
	}

	public void setLinearParameterUnit(String linearParameterUnit) {
		this.linearParameterUnit = linearParameterUnit;
	}

	
	
/*	public LinearParameterDTO getLinearParameterDTO() {
		return linearParameterDTO;
	}

	public void setLinearParameterDTO(LinearParameterDTO linearParameterDTO) {
		this.linearParameterDTO = linearParameterDTO;
	}*/
	
	/*public String toString() {
		System.out.println("ResourceLinearDTO: ");
		System.out.println("LinearParameterDTO: " + linearParameterDTO.toString());*/


/*	public String toString(){
		return "\n\n\n" + "**************************************" + "\n" +
	"Лінійні параметри ресурсу: " + linearParameterDTO.toString() +
	"Лінійна величина параметру: " + segments.toString();
		

	public String toString() {
        StringBuilder result = new StringBuilder();
		result.append("---- Лінійні параметри -----\n");
		result.append("Лінійний параметр: " + linearParameterDTO.toString() + "\n");
		for (SegmentLinearDTO segment : segments) {
			result.append(segment.toString());
		}
		return null;
	}
	
	public String toString() {
		System.out.println("ResourceLinearDTO: ");
		System.out.println("LinearParameterDTO: " + linearParameterDTO.toString());
		for (SegmentLinearDTO segment : segments) {
			segment.toString();
		}
		return ;
	}

	}*/

	
}
