package org.registrator.community.dto;

import java.io.Serializable;

public class TomeDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String tomeIdentifier;
	private String registratorFirstName;
	private String registratorLastName;
	private String registratorMiddleName;
	
	public TomeDTO(String tomeIdentifier, String registratorFirstName, String registratorLastName,
				String registratorMiddleName) {
		this.tomeIdentifier = tomeIdentifier;
		this.registratorFirstName = registratorFirstName;
		this.registratorLastName = registratorLastName;
		this.registratorMiddleName = registratorMiddleName;
	}

	public String getTomeIdentifier() {
		return tomeIdentifier;
	}

	public void setTomeIdentifier(String tomeIdentifier) {
		this.tomeIdentifier = tomeIdentifier;
	}

	public String getRegistratorFirstName() {
		return registratorFirstName;
	}

	public void setRegistratorFirstName(String registratorFirstName) {
		this.registratorFirstName = registratorFirstName;
	}

	public String getRegistratorLastName() {
		return registratorLastName;
	}

	public void setRegistratorLastName(String registratorLastName) {
		this.registratorLastName = registratorLastName;
	}

	public String getRegistratorMiddleName() {
		return registratorMiddleName;
	}

	public void setRegistratorMiddleName(String registratorMiddleName) {
		this.registratorMiddleName = registratorMiddleName;
	}	
		
}
