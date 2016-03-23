package org.registrator.community.dto.json;

import java.util.List;

/**
 * Created by Oleksiy on 02.01.2016.
 */
public class PolygonJson {
    private String DT_RowId;
    private String resourceDescription;
    private String identifier;
    private String resourceType;
    private String date;
    private List<PointJson> points;

    public String getResourceDescription() {
        return resourceDescription;
    }

    public void setResourceDescription(String resourceDescription) {
        this.resourceDescription = resourceDescription;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDT_RowId() {
        return DT_RowId;
    }

    public void setDT_RowId(String DT_RowId) {
        this.DT_RowId = DT_RowId;
    }

    public List<PointJson> getPoints() {
        return points;
    }

    public void setPoints(List<PointJson> points) {
        this.points = points;
    }
}
