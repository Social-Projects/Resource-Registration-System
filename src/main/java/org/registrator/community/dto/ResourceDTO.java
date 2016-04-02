package org.registrator.community.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;
import org.registrator.community.enumeration.ResourceStatus;
import org.springframework.format.annotation.DateTimeFormat;

public class ResourceDTO {
    
    @NotEmpty(message = "{msg.notEmptyField}")
    private String resourceType;
    
    @NotEmpty(message = "{msg.notEmptyField}")
    @Pattern(regexp ="^[\\d]{3}:[\\d]{2}:[\\d]{2}:[\\d]{3}:[\\d]{5}:[\\d]{4}$", message = "{msg.resource.identifier.patternMissmatch}")
    private String identifier;

    @NotEmpty(message = "{msg.notEmptyField}")
    @Pattern(regexp ="\\p{IsCyrillic}*", message = "Лише літери лише від А до Я ")
    private String description;
    
    private String registratorName;
    
    @NotNull(message = "{msg.notEmptyField}")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date date;
    
    private ResourceStatus status;
    
    @NotEmpty(message = "{msg.notEmptyField}")
    private String reasonInclusion;
        
    private String tomeIdentifier;
    
    @Valid
    private ResourceAreaDTO resourceArea;
    
    @Valid
    private List<ResourceLinearValueDTO> resourceLinear = new ArrayList<ResourceLinearValueDTO>();
    
    @Valid
    private List<ResourceDiscreteValueDTO> resourceDiscrete = new ArrayList<ResourceDiscreteValueDTO>();

    @Valid
    private String ownerLogin;

    @Valid
    private Integer resourceId;

    @Valid
    private boolean noEdit;
    
    public ResourceDTO() {
        ownerLogin = "";
    }

    
    public String getIdentifier() {
        return identifier;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
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

    public String getOwnerLogin() {
        return ownerLogin;
    }

    public void setOwnerLogin(String ownerLogin) {
        this.ownerLogin = ownerLogin;
    }

    public Integer getResourceId() {
        return resourceId;
    }

    public void setResourceId(Integer id) {
        this.resourceId = id;
    }

    public boolean getNoEdit() {
        return noEdit;
    }

    public void setNoEdit(boolean noEdit) {
        this.noEdit = noEdit;
    }
}
