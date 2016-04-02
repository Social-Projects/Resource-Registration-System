package org.registrator.community.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "registration_number_of_the_resource")
public class ResourceNumber implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "number_id")
	@GeneratedValue
	private Integer numberId;

	@Column(name = "number", nullable = false)
	private Integer number;

	@Column(name = "registrator_number", nullable = false)
	private String registratorNumber;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	public ResourceNumber() {
	}

	public ResourceNumber(Integer number, String registraorNumber, User user) {
		this.number = number;
		this.registratorNumber = registraorNumber;
		this.user = user;
	}

	public Integer getNumberId() {
		return numberId;
	}

	public void setNumberId(Integer numberId) {
		this.numberId = numberId;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getRegistratorNumber() {
		return registratorNumber;
	}

	public void setRegistratorNumber(String registratorNumber) {
		this.registratorNumber = registratorNumber;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
