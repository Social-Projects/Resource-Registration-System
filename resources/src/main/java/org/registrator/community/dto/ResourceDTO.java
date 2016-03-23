package org.registrator.community.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.registrator.community.entity.ResourceType;
import org.registrator.community.enumeration.ResourceStatus;
import org.springframework.util.AutoPopulatingList;

public class ResourceDTO {
	
	//private ResourceTypeDTO resourceType;
	private ResourceType resourceType;
	private String identifier;
	private String description;
	private String registratorName;
	private Date date;
	private ResourceStatus status;
	private String reasonInclusion;
	private String tomeIdentifier;
	
	
	private ResourceAreaDTO resourceArea;
	
	private List<ResourceLinearValueDTO> resourceLinear = new ArrayList<ResourceLinearValueDTO>();
	private List<ResourceDiscreteValueDTO> resourceDiscrete = new ArrayList<ResourceDiscreteValueDTO>();;
	
	public ResourceDTO() {
		
	}
		
	/*
	public ResourceDTO(ResourceTypeDTO resourceType, String identifier, String description, String registratorName, Date date,
			ResourceStatus status, String reasonInclusion, String tomeIdentifier, ResourceAreaDTO resourceArea,
			List<ResourceLinearValueDTO> resourceLinear, List<ResourceDiscreteValueDTO> resourceDiscrete) {
		this.resourceType = resourceType;
		this.identifier = identifier;
		this.description = description;
		this.registratorName = registratorName;
		this.date = date;
		this.status = status;
		this.reasonInclusion = reasonInclusion;
		this.tomeIdentifier = tomeIdentifier;
		this.resourceArea = resourceArea;
		this.resourceLinear = resourceLinear;
		this.resourceDiscrete = resourceDiscrete;
		
	}
	*/
	
	public ResourceType getResourceType() {
		return resourceType;
	}
	
	public void setResourceType(ResourceType resourceType) {
		this.resourceType = resourceType;
	}
	
	
	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getRegistratorName() {
		return registratorName;
	}


	public void setRegistratorName(String registratorName) {
		this.registratorName = registratorName;
	}


	public Date getDate() {
		return date;
	}


	public void setDate(Date date) {
		this.date = date;
	}


	public ResourceStatus getStatus() {
		return status;
	}


	public void setStatus(ResourceStatus status) {
		this.status = status;
	}


	public String getReasonInclusion() {
		return reasonInclusion;
	}


	public void setReasonInclusion(String reasonInclusion) {
		this.reasonInclusion = reasonInclusion;
	}


	public String getTomeIdentifier() {
		return tomeIdentifier;
	}


	public void setTomeIdentifier(String tomeIdentifier) {
		this.tomeIdentifier = tomeIdentifier;
	}

	public ResourceAreaDTO getResourceArea() {
		return resourceArea;
	}

	public void setResourceArea(ResourceAreaDTO resourceArea) {
		this.resourceArea = resourceArea;
	}

	public List<ResourceLinearValueDTO> getResourceLinear() {
		return resourceLinear;
	}

	public void setResourceLinear(List<ResourceLinearValueDTO> resourceLinear) {
		this.resourceLinear = resourceLinear;
	}

	public List<ResourceDiscreteValueDTO> getResourceDiscrete() {
		return resourceDiscrete;
	}

	public void setResourceDiscrete(List<ResourceDiscreteValueDTO> resourceDiscrete) {
		this.resourceDiscrete = resourceDiscrete;
	}


	
/*	public String toString(){
		return "\n\n\n" + "**************************************" + "\n" +
	"Ідентифікатор ресурсу: " + identifier + "\n"+
	"Тип ресурсу: " + resourceType.getTypeName() + "\n"+
	"Опис ресурсу: " + description + "\n" + 
	"Причина внесення в базу:  " + reasonInclusion + "\n" +
	"Ім'я та прізвище реєстратора   " + registratorName + "\n"+
	"Номер тому: " + tomeIdentifier + "\n"+
	"Дата внесення: " + date + "\n"+
	"Статус: " + status.toString() + "\n"+
	"Територія: " + resourceArea + "\n" +
	"Лінійні параметри: " + resourceLinear + "\n" +
 	"Дискретні параметри: "	+ resourceDiscrete;
		
	}*/
}
