package org.registrator.community.dto;

import java.io.Serializable;

import javax.validation.constraints.Pattern;

public class PassportDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Pattern(regexp = "[[А-ЯІЇЄ]){2}]*", message = "{msg.registration.onlytwoletters}")
	private String seria;

	@Pattern(regexp = "[[0-9]){6}]*", message = "{msg.registration.only6digits}")
	private String number;

	private String published_by_data;

	private String comment;

	public PassportDTO(String seria, String number, String published_by_data) {
		this.seria = seria;
		this.number = number;
		this.published_by_data = published_by_data;
	}

	public PassportDTO() {
	}

	public String getSeria() {
		return seria;
	}

	public void setSeria(String seria) {
		this.seria = seria;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getPublished_by_data() {
		return published_by_data;
	}

	public void setPublished_by_data(String published_by_data) {
		this.published_by_data = published_by_data;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	/*
	 * @Override public String toString() { return "Серія паспорту: " + seria +
	 * "\n" + "Номер паспорту: " + number + "\n" + "Виданий: " +
	 * published_by_data + "\n"; }
	 */

}