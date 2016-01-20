package org.registrator.community.service;

import org.registrator.community.dto.InquiryDTO;
import org.registrator.community.dto.InquiryListDTO;
import org.registrator.community.dto.ResourceDTO;
import org.registrator.community.dto.TomeDTO;
import org.registrator.community.dto.UserNameDTO;
import org.registrator.community.entity.Inquiry;
import org.registrator.community.enumeration.InquiryType;

import java.util.List;

/**
 * Interface for work with procurations of entering data into the register
 *(input inquiry) and with procurations for an extract from register (output inquiry).
 * @author Ann
 *
 */
public interface InquiryService {
	
	Inquiry addOutputInquiry(String resourceIdentifier, String registratorLogin, String userLogin);
	
	List<TomeDTO> listTomeDTO();
	List<UserNameDTO> listUserNameDTO(String userLogin);
	
	List<InquiryListDTO> listInquiryUser(String userLogin, InquiryType inquiryType);
		
	void removeInquiry (Integer inquiryId);
	
	//ResourceDTO addInputInquiry(ResourceDTO resourceDTO, String userLogin);

}


