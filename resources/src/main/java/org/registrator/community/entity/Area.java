package org.registrator.community.entity;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "area")
public class Area implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "area_id")
	@GeneratedValue
	private Integer area_id;

	@ManyToOne
	@JoinColumn(name = "resource_id", nullable = false)
	private Resource resource;
	
	@Column(name = "latitude", nullable = false)
	private Double latitude;
	
	@Column(name = "longitude", nullable = false)
	private Double longitude;
	
//	@Column(name = "number_of_point", nullable = false)
//	private Integer numberOfPoint;
	@Column(name = "order_number", nullable = false)
	private Integer orderNumber;

	public Area() {
		
	}
	
	public Area(Resource resource, Integer orderNumber, Double latitude, Double longitude) {
		this.resource = resource;
		this.orderNumber = orderNumber;
		this.latitude = latitude;
		this.longitude = longitude;
	}



	public Integer getArea_id() {
		return area_id;
	}

	public void setArea_id(Integer area_id) {
		this.area_id = area_id;
	}

	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

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

	public Integer getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(Integer orderNumber) {
		this.orderNumber = orderNumber;
	}
}
