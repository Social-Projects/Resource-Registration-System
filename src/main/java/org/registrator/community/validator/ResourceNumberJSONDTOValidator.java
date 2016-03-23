package org.registrator.community.validator;

import java.util.List;

import org.registrator.community.dao.ResourceNumberRepository;
import org.registrator.community.dao.TomeRepository;
import org.registrator.community.dao.UserRepository;
import org.registrator.community.dto.json.ResourceNumberJson;
import org.registrator.community.entity.ResourceNumber;
import org.registrator.community.entity.Tome;
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
		ResourceNumber resourceNumber = resourceNumberRepository.findResourceNumberByUser(user);
		Tome tome = tomeRepository.findTomeByRegistrator(user);
		List<ResourceNumber> registratorNumberList = resourceNumberRepository.findAll();
		List<Tome> tomeList = tomeRepository.findAll();
		if (resourceNumber != null) {
			errors.rejectValue("registrator_number", "msg.registration.registratornumber.exist");
		}

		for (ResourceNumber registratorNumber : registratorNumberList) {
			if (registratorNumber.getRegistratorNumber().equals(resourceNumberJson.getRegistrator_number())) {
				errors.rejectValue("registrator_number", "msg.registation.registratornumber.unique");
			}
		}
		
		if (tome != null) {
			errors.rejectValue("identifier", "msg.registration.tomenumber.exist");
		}
		
		for (Tome tomeNumber : tomeList) {
			if (resourceNumberJson.getIdentifier().equals(tomeNumber.getIdentifier())) {
				errors.rejectValue("identifier", "msg.registation.tomenumber.unique");
			}
		}
	}

}
