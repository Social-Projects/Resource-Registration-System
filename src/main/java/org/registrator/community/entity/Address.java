package org.registrator.community.entity;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "address")
public class Address implements Serializable, Comparable<Address> {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "address_id")
	@GeneratedValue
	private Integer addressId;

	@ManyToOne(fetch = FetchType.EAGER)
	@JsonBackReference
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@Column(name = "postcode", nullable = false)
	private String postCode;

	@Column(name = "region", nullable = false)
	private String region;

	@Column(name = "district")
	private String district;

	@Column(name = "city", nullable = false)
	private String city;

	@Column(name = "street", nullable = false)
	private String street;

	@Column(name = "building", nullable = false)
	private String building;

	@Column(name = "flat")
	private String flat;

	public Address() {

	}

	public Address(User user, String postCode, String region, String district, String city, String street,
			String building, String flat) {
		this.user = user;
		this.postCode = postCode;
		this.region = region;
		this.district = district;
		this.city = city;
		this.street = street;
		this.building = building;
		this.flat = flat;
	}

	public Integer getAddressId() {
		return addressId;
	}

	public void setAddressId(Integer addressId) {
		this.addressId = addressId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getBuilding() {
		return building;
	}

	public void setBuilding(String building) {
		this.building = building;
	}

	public String getFlat() {
		return flat;
	}

	public void setFlat(String flat) {
		this.flat = flat;
	}

	@Override
	public int compareTo(Address address) {
		if (this.region.equals(address.region) && this.city.equals(address.city)
				&& this.district.equals(address.district) && this.street.equals(address.street)
				&& this.building.equals(address.building) && this.flat.equals(address.flat) && this.postCode.equals(address.postCode)) {
			return 0;
		} else {
			return 1;
		}
	}

}
