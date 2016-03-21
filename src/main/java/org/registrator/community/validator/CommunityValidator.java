package org.registrator.community.validator;
import org.registrator.community.dao.CommunityRepository;
import org.registrator.community.dao.ResourceTypeRepository;
import org.registrator.community.dto.ResourceTypeDTO;
import org.registrator.community.entity.TerritorialCommunity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class CommunityValidator implements Validator {

    @Autowired
    CommunityRepository communityRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(TerritorialCommunity.class);
    }

    /**
     * Validate community name
     */
    @Override
    public void validate(Object target, Errors errors) {

        TerritorialCommunity territorialCommunity = (TerritorialCommunity) target;
        TerritorialCommunity found = communityRepository.findByName(territorialCommunity.getName());
        if (found != null) {
            if(territorialCommunity.getTerritorialCommunityId() != null){
            	if(territorialCommunity.getTerritorialCommunityId() != found.getTerritorialCommunityId())
            		errors.rejectValue("name", "msg.resourcetype.typename.exist");
            }else{
            	 errors.rejectValue("name", "msg.resourcetype.typename.exist");
            }
        }
        //validate registrationNumber
        if(territorialCommunity.getRegistrationNumber() != null &&
        		!territorialCommunity.getRegistrationNumber().isEmpty()){
        	if(!territorialCommunity.getRegistrationNumber().matches("^[0-9]{3}:[0-9]{2}:[0-9]{2}:[0-9]{3}:[0-9]{5}$"))
        		errors.rejectValue("registrationNumber", "typeMismatch");
        }
    }
}