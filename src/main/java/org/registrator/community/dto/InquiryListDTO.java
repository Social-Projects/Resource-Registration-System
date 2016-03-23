package org.registrator.community.dto;

/**
 * A class that offers Data transfer object
 *  - an object that carries data between processes.
 */

import java.io.Serializable;
import java.util.Date;

import org.registrator.community.enumeration.ResourceStatus;

public class InquiryListDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer inquiryId;
	private String inquiryType;
	private Date date;
	private String userName;
	private String registratorName;
	private String resourceIdentifier;
	private ResourceStatus resourceStatus;

	public InquiryListDTO() {
	}

	public InquiryListDTO(Integer inquiryId, String inquiryType, Date date, String userName,
			String registratorName, String resourceIdentifier, ResourceStatus resourceStatus) {
		this.inquiryId = inquiryId;
		this.inquiryType = inquiryType;
		this.date = date;
		this.userName = userName;
		this.registratorName = registratorName;
		this.resourceIdentifier = resourceIdentifier;
		this.resourceStatus = resourceStatus;
	}

	public Integer getInquiryId() {
		return inquiryId;
	}

	public void setInquiryId(Integer inquiryId) {
		this.inquiryId = inquiryId;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRegistratorName() {
		return registratorName;
	}

	public void setRegistratorName(String registratorName) {
		this.registratorName = registratorName;
	}

	public String getResourceIdentifier() {
		return resourceIdentifier;
	}

	public void setResourceIdentifier(String resourceIdentifier) {
		this.resourceIdentifier = resourceIdentifier;
	}

	public ResourceStatus getResourceStatus() {
		return resourceStatus;
	}

	public void setResourceStatus(ResourceStatus resourceStatus) {
		this.resourceStatus = resourceStatus;
	}

}
