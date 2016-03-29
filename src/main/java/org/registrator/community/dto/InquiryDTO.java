package org.registrator.community.dto;

import java.io.Serializable;

public class InquiryDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	private String resourceIdentifier;
	private String tomeIdentifier;
	
	public InquiryDTO() {		
	}
	
	public InquiryDTO(String resourceIdentifier, String tomeIdentifier) {
		this.resourceIdentifier = resourceIdentifier;
		this.tomeIdentifier = tomeIdentifier;
	}

	public String getResourceIdentifier() {
		return resourceIdentifier;
	}

	public void setResourceIdentifier(String resourceIdentifier) {
		this.resourceIdentifier = resourceIdentifier;
	}

	public String getTomeIdentifier() {
		return tomeIdentifier;
	}

	public void setTomeIdentifier(String tomeIdentifier) {
		this.tomeIdentifier = tomeIdentifier;
	}
	
}
