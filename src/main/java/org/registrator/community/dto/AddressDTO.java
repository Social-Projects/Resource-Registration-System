package org.registrator.community.dto;

import java.io.Serializable;

import javax.validation.constraints.Pattern;


public class AddressDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public static final String ONLY_LITERALS = "[а-яіїєА-ЯІЇЄa-zA-Z,\\s,\\.,\\-]+";
	public static final String ONLY_DIGITS = "[0-9]+";

	@Pattern(regexp = "[[0-9])]*", message = "{msg.registration.onlydigits}")
	private String postcode;

	private String region;

	private String district;

	private String city;

	private String street;

	private String building;

	private String flat;

	public AddressDTO(String postcode, String region, String district, String city, String street, String building,
			String flat) {
		this.postcode = postcode;
		this.region = region;
		this.district = district;
		this.city = city;
		this.street = street;
		this.building = building;
		this.flat = flat;
	}

	public AddressDTO() {

	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
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
	public String toString() {
		return "\n" + "Postcode " + postcode + "\n" + "Region " + region + "\n" + "District " + district + "\n"
				+ "City " + city + "\n" + "Street " + street + "\n" + "Building " + building + "\n" + "Flat " + flat
				+ "\n";
	}
}
