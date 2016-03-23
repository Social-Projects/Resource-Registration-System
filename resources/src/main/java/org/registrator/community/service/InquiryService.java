package org.registrator.community.service;

import org.registrator.community.dto.InquiryDTO;
import org.registrator.community.dto.InquiryListDTO;
import org.registrator.community.dto.TomeDTO;
import org.registrator.community.entity.Inquiry;
import java.util.List;

public interface InquiryService {
	public Inquiry addOutputInquiry(InquiryDTO inquiryDTO, String userLogin);
	
	//public Inquiry testAddOutputInquiry(String resourceIdentifier);
	
	public List<TomeDTO> listTomeDTO();

}

