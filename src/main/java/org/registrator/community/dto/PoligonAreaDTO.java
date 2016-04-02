package org.registrator.community.dto;

import java.util.List;

import org.springframework.util.AutoPopulatingList;

public class PoligonAreaDTO {
    private Long polygonId;

    private List<PointAreaDTO> points = new AutoPopulatingList<PointAreaDTO>(PointAreaDTO.class);
    
    public PoligonAreaDTO() {
        
    }

    public Long getPolygonId() {
        return polygonId;
    }

    public void setPolygonId(Long polygonId) {
        this.polygonId = polygonId;
    }

    public List<PointAreaDTO> getPoints() {
        return points;
    }

    public void setPoints(List<PointAreaDTO> points) {
        this.points = points;
    }
}
