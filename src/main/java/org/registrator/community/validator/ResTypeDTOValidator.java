package org.registrator.community.validator;
import org.registrator.community.dao.ResourceTypeRepository;
import org.registrator.community.dto.ResourceTypeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ResTypeDTOValidator implements Validator {

	@Autowired
	ResourceTypeRepository resourceTypeRepository;

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.equals(ResourceTypeDTO.class);
	}

	/**
	 * Validate resource type name
	 */
	@Override
	public void validate(Object target, Errors errors) {

		ResourceTypeDTO resourceTypeDTO = (ResourceTypeDTO) target;
		if (resourceTypeRepository.findByName(resourceTypeDTO.getTypeName()) != null) {
			errors.rejectValue("typeName", "msg.resourcetype.typename.exist");
		}
	}
}
