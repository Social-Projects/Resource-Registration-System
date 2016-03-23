package org.registrator.community.dto;

/**
 * A class that offers Data transfer object
 *  - an object that carries data between processes.
 */

import java.io.Serializable;
import java.util.Date;


public class InquiryListDTO implements Serializable {
	private String inquiryType;
	private Date date;
	private String userLogin;
	private String registratorLogin;
	private ResourceDTO resource;	 
	
	public InquiryListDTO() {
		super();
	}

	public InquiryListDTO(String inquiryType, Date date, String userLogin, String registratorLogin,
			ResourceDTO resource) {
		super();
		this.inquiryType = inquiryType;
		this.date = date;
		this.userLogin = userLogin;
		this.registratorLogin = registratorLogin;
		this.resource = resource;
	}

	public String getInquiryType() {
		return inquiryType;
	}

	public void setInquiryType(String inquiryType) {
		this.inquiryType = inquiryType;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getUserLogin() {
		return userLogin;
	}

	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
	}

	public String getRegistratorLogin() {
		return registratorLogin;
	}

	public void setRegistratorLogin(String registratorLogin) {
		this.registratorLogin = registratorLogin;
	}

	public ResourceDTO getResource() {
		return resource;
	}

	public void setResource(ResourceDTO resource) {
		this.resource = resource;
	}

	public String toString(){
		String result;
		result = String.format("Тип запиту: " + inquiryType + "\n"
                + "Запит внесено: " + date + "\n"
                + "Запит вніс: " + userLogin + "\n"
                + "Запит відправлено на: " + registratorLogin + "\n"
                + "Ідентифікатор ресурсу: " + resource.getIdentifier() + "\n");

		return result;
	}

		
}
