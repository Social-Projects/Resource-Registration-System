package org.registrator.community.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "polygon")
public class Polygon implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
    @Column(name = "id")
    @GeneratedValue
    private Long id;

    @Column(name = "minLat", nullable = false)
    private Double minLat;

    @Column(name = "maxLat", nullable = false)
    private Double maxLat;

    @Column(name = "minLng", nullable = false)
    private Double minLng;

    @Column(name = "maxLng", nullable = false)
    private Double maxLng;

    @ManyToOne
    @JoinColumn(name = "resource_id", nullable = false)
    private Resource resource;

    @Lob
    @Column(name = "coordinates", nullable = false)
    private String coordinates;

  //for deleting the resource with its childs
//    @OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.REMOVE, mappedBy="polygon")
//    public List<Area> areas;

    public Polygon() {
    }

    public Polygon(Double minLat, Double maxLat, Double minLng, Double maxLng, Resource resource) {
        this.minLat = minLat;
        this.maxLat = maxLat;
        this.minLng = minLng;
        this.maxLng = maxLng;
        this.resource = resource;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getMinLat() {
        return minLat;
    }

    public void setMinLat(Double minLat) {
        this.minLat = minLat;
    }

    public Double getMaxLat() {
        return maxLat;
    }

    public void setMaxLat(Double maxLat) {
        this.maxLat = maxLat;
    }

    public Double getMinLng() {
        return minLng;
    }

    public void setMinLng(Double minLng) {
        this.minLng = minLng;
    }

    public Double getMaxLng() {
        return maxLng;
    }

    public void setMaxLng(Double maxLng) {
        this.maxLng = maxLng;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }
}
