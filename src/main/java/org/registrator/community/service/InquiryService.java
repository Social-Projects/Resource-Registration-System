package org.registrator.community.service;

import java.util.List;

import org.registrator.community.dto.InquiryListDTO;
import org.registrator.community.dto.UserNameDTO;
import org.registrator.community.entity.Inquiry;
import org.registrator.community.entity.Resource;
import org.registrator.community.entity.User;
import org.registrator.community.enumeration.InquiryType;

/**
 * Interface for work with procurations of entering data into the register
 *(input inquiry) and with procurations for an extract from register (output inquiry).
 * @author Ann
 *
 */
public interface InquiryService {
	
	Inquiry addOutputInquiry(String resourceIdentifier, String registratorLogin, String userLogin);
		
	List<UserNameDTO> listUserNameDTO(String userLogin);
	
	List<InquiryListDTO> listInquiryUser(String userLogin, InquiryType inquiryType);
		
	void removeInquiry (Integer inquiryId);
	
	void addInputInquiry(String ownerLogin, Resource resourceEntity, User registrator);

}


