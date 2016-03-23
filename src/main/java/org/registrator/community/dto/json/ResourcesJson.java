package org.registrator.community.dto.json;

import org.registrator.community.enumeration.ResourceStatus;

import java.util.Date;

/**
 * Created by Oleksiy on 20.12.2015.
 */
public class ResourcesJson {

    private Integer id;
    private Integer typeId;
    private String identifier;
    private String description;
    private Integer registratorId;
    private Date date;
    private ResourceStatus status;
    private Integer tomeId;
    private String reasonInclusion;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
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

    public Integer getRegistratorId() {
        return registratorId;
    }

    public void setRegistratorId(Integer registratorId) {
        this.registratorId = registratorId;
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

    public Integer getTomeId() {
        return tomeId;
    }

    public void setTomeId(Integer tomeId) {
        this.tomeId = tomeId;
    }

    public String getReasonInclusion() {
        return reasonInclusion;
    }

    public void setReasonInclusion(String reasonInclusion) {
        this.reasonInclusion = reasonInclusion;
    }
}
