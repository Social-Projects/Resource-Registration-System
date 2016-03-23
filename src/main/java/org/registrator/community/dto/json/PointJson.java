package org.registrator.community.dto.json;

/**
 * Created by Oleksiy on 02.01.2016.
 */
public class PointJson {
    private Double latitude;
    private Double longitude;
    private Integer point_order;

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Integer getPoint_order() {
        return point_order;
    }

    public void setPoint_order(Integer point_order) {
        this.point_order = point_order;
    }
}
