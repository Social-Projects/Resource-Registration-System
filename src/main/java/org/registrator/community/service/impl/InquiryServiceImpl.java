package org.registrator.community.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.registrator.community.dao.InquiryRepository;
import org.registrator.community.dao.ResourceRepository;
import org.registrator.community.dao.UserRepository;
import org.registrator.community.dto.InquiryListDTO;
import org.registrator.community.dto.UserNameDTO;
import org.registrator.community.entity.Inquiry;
import org.registrator.community.entity.Resource;
import org.registrator.community.entity.TerritorialCommunity;
import org.registrator.community.entity.User;
import org.registrator.community.enumeration.InquiryType;
import org.registrator.community.enumeration.RoleType;
import org.registrator.community.service.InquiryService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service class implements InquiryService interface.
 * Works with procurations of entering data into the register
 *(input inquiry) and with procurations for an extract from register (output inquiry).
 * @author Ann
 *
 */
@Service
public class InquiryServiceImpl implements InquiryService{
	
	@Autowired
	private Logger logger;
	@Autowired
	private InquiryRepository inquiryRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ResourceRepository resourceRepository;

	
	/**
	 * Method saves the data in the table inquiry_list.
	 * 
	 * @param resourceIdentifier - identifier of the resource
	 * @param registratorLogin - login of the chosen registrator
	 * @param userLogin - login of the logged user
	 * @return  inquiry entity
	 */
	@Transactional
	@Override
	public Inquiry addOutputInquiry(String resourceIdentifier, String registratorLogin, String userLogin){
		User user = userRepository.findUserByLogin(userLogin);
		User registrator = userRepository.findUserByLogin(registratorLogin);		
		Resource resource = resourceRepository.findByIdentifier(resourceIdentifier);		
		Inquiry inquiry = new Inquiry("OUTPUT", new Date(), user, registrator, resource);
		logger.info("wrote line to inquiry_list table");
		inquiryRepository.save(inquiry);	
		return inquiry;
	}
	
	/**
     * Method save input inquiry in database for selected user and saved resource
     * 
     * @param userLogin - login of the user 
     * @param resourceEntity - resource 
     * @param registrator - entity of the registrator who adds the resource
     */
	@Transactional
	@Override
	public void addInputInquiry(String userLogin, Resource resourceEntity, User registrator){
		User user = userRepository.findUserByLogin(userLogin);
        Inquiry inquiry = new Inquiry("INPUT", resourceEntity.getDate(), user, registrator, resourceEntity);
        inquiryRepository.save(inquiry);
	}
	
		
	/**
	 * Method for showing form on UI to input the parameters 
	 * for inquiry to get the certificate about the resource 
	 * forms List<UserNameDTO> - all registrars linked to logged user 
	 * using the same territorial community to fill inquiryAddOut.jsp.
	 * 
	 * @param userLogin - login of the logged user
	 * @return list to fill inquiryAddOut.jsp
	 */	
	@Override
	public List<UserNameDTO> listUserNameDTO(String userLogin){			
		User user = userRepository.findUserByLogin(userLogin);
		TerritorialCommunity territorialCommunity = user.getTerritorialCommunity();
		List<User> registrators = userRepository.getUsersByRoleAndCommunity(RoleType.REGISTRATOR,
								territorialCommunity);
		logger.info("set of registrators is empty " + registrators.isEmpty());
		List<UserNameDTO> aListUserNameDTO = new ArrayList<>();
		UserNameDTO userNameDTO;
		for (User registrator : registrators){
			userNameDTO = new UserNameDTO(registrator.getFirstName(), registrator.getLastName(),
					registrator.getMiddleName(), registrator.getLogin());
			aListUserNameDTO.add(userNameDTO);			
		}			
		return aListUserNameDTO;
	}
	

	/**
	 * Method for showing all output inquiries from logged user on UI 
	 * (if role of logged user is USER)
	 * or for showing all inquiries to registator of type inquiryType
	 * forms List<InquiryListDTO> to fill listInqUserOut.jsp or listInquiryUserInput.jsp.
	 * 
	 * @param userLogin - login of the logged user
	 * @param inquiryType - type of the inquiry (InquiryType.INPUT or InquiryType.OUTPUT)
	 * @return list of InquiryListDTO
	 */
	@Transactional
	@Override
	public List<InquiryListDTO> listInquiryUser(String userLogin, InquiryType inquiryType){		
		List<InquiryListDTO> listInquiryDTO = new ArrayList<InquiryListDTO>();
		InquiryListDTO inquiryListDTO;
		User user = userRepository.findUserByLogin(userLogin);
		
		List<Inquiry> listInquiry;
		if (user.getRole().getType().equals(RoleType.USER)){		
			listInquiry = inquiryRepository.findByUserAndInquiryType(user, inquiryType);
		} else {
			listInquiry = inquiryRepository.findByRegistratorAndInquiryType(user, inquiryType);
		}
		for (Inquiry inquiry : listInquiry){
			inquiryListDTO = new InquiryListDTO(inquiry.getInquiryId(), inquiry.getInquiryType().toString(), 
						inquiry.getDate(), null, null, inquiry.getResource().getIdentifier(),
						inquiry.getResource().getStatus());
			User userFrom =inquiry.getUser();
			inquiryListDTO.setUserName(userFrom.getLastName()+ " " +userFrom.getFirstName()
						+ " " +userFrom.getMiddleName());
			User registrator = inquiry.getRegistrator();
			inquiryListDTO.setRegistratorName(registrator.getLastName()+ " " +registrator.getFirstName()
						+ " " +registrator.getMiddleName());
			listInquiryDTO.add(inquiryListDTO);
		}		
		return listInquiryDTO;
	}
		
	/**
	 * Method for deleting chosen inquiry by Id.
	 * 
	 * inquiryId - id of the chosen inquiry
	 */
	@Transactional
	@Override
	public void removeInquiry (Integer inquiryId){
		inquiryRepository.delete(inquiryId);
		logger.info("delete line from inquiry_list table with inqury_id = " + inquiryId);
	}
	
}


