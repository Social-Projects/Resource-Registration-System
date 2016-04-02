package org.registrator.community.validator;

import java.util.List;

import org.registrator.community.dao.ResourceNumberRepository;
import org.registrator.community.dao.TomeRepository;
import org.registrator.community.dao.UserRepository;
import org.registrator.community.dto.json.ResourceNumberJson;
import org.registrator.community.entity.ResourceNumber;
import org.registrator.community.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ResourceNumberJSONDTOValidator implements Validator {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ResourceNumberRepository resourceNumberRepository;

    @Autowired
    TomeRepository tomeRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(ResourceNumberJson.class);
    }

    @Override
    public void validate(Object object, Errors errors) {
        ResourceNumberJson resourceNumberJson = (ResourceNumberJson) object;

        User user = userRepository.findUserByLogin(resourceNumberJson.getLogin());
        ResourceNumber newResourceNumber = new ResourceNumber(1, resourceNumberJson.getRegistrator_number(), user);

        List<ResourceNumber> resNumList = resourceNumberRepository
                .findResourceNumbersByCommunity(user.getTerritorialCommunity());

        ResourceNumber tmpNumber = null;
        for (ResourceNumber num : resNumList) {
            if (num.getRegistratorNumber().equals(newResourceNumber.getRegistratorNumber())) {
                tmpNumber = num;
            }
        }
        if (tmpNumber != null) {
            if (!tmpNumber.getUser().getLogin().equals(user.getLogin())) {
                errors.rejectValue("resourceNumberJson.registrator_number", "msg.registation.registratornumber.unique");
            }
        }
    }

}
