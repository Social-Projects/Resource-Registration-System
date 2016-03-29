package org.registrator.community.dto.json;

import java.util.List;

/**
 * Created by Oleksiy on 15.02.2016.
 */
public class SearchResultJson {
    private List<PolygonJson> polygons;
    private long countPolygons;

    public List<PolygonJson> getPolygons() {
        return polygons;
    }

    public void setPolygons(List<PolygonJson> polygons) {
        this.polygons = polygons;
    }

    public long getCountPolygons() {
        return countPolygons;
    }

    public void setCountPolygons(long countPolygons) {
        this.countPolygons = countPolygons;
    }
}
