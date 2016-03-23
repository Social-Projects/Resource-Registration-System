package org.registrator.community.entity;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "passport_data")
public class PassportInfo implements Serializable,Comparable<PassportInfo>{

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "passport_data_id")
	@GeneratedValue
	private Integer passportId;

	@ManyToOne(fetch=FetchType.EAGER)
	@JsonBackReference
	@JoinColumn(name="user_id", nullable = false)
	private User user;

	@Column(name = "seria", nullable = false)
	private String seria;

	@Column(name = "number", nullable = false)
	private String number;

	@Column(name = "published_by_data", nullable = false)
	private String publishedByData;

	@Column(name = "comment")
	private String comment;

	public PassportInfo() {

	}

	public PassportInfo(User user, String seria, String number, String publishedByData) {
		this.user = user;
		this.seria = seria;
		this.number = number;
		this.publishedByData = publishedByData;
	}

	public Integer getPassportId() {
		return passportId;
	}

	public void setPassportId(Integer passportId) {
		this.passportId = passportId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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

	public String getPublishedByData() {
		return publishedByData;
	}

	public void setPublishedByData(String publishedByData) {
		this.publishedByData = publishedByData;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Override
	public int compareTo(PassportInfo passport) {
		if(this.seria.equals(passport.seria) && (this.number.equals(passport.number) && (this.publishedByData.equals(passport.publishedByData)))) {
			return 0;
		} else {
			return 1;
		}

	}
}