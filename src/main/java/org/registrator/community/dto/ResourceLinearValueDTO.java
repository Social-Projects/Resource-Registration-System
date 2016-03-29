package org.registrator.community.dto;

import java.util.List;

public class ResourceLinearValueDTO {

    private Integer id;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}