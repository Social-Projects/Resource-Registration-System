package org.registrator.community.controller;

import javax.validation.Valid;

import org.registrator.community.dto.ResourceTypeDTO;
import org.registrator.community.service.ResourceTypeService;
import org.registrator.community.validator.ResTypeDTOValidator;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/registrator/resourcetypes/")
public class AddResourceTypeController {

    @Autowired
    private Logger logger;  
    @Autowired
    private ResTypeDTOValidator validator;
    @Autowired
    private ResourceTypeService resourceTypeService;

    /**
     * Method for loading form for input the parameters and resource type name
     * @param model
     * @return addResType.jsp
     */
    @PreAuthorize("hasRole('ROLE_REGISTRATOR')")
    @RequestMapping(value = "/addrestype", method = RequestMethod.GET)
    public String addResourceTypeForm(Model model) {
        logger.info("begin method for showing settings for admin");
        model.addAttribute("newrestype", new ResourceTypeDTO());
        logger.info("end method for showing settings for admin");
        return "addResType";
    }

    /**
     * Method save the resource type in resource_types. Also there is validation for checking whether 
     * inputing resource type already exists in database or not.
     * @param resourceTypeDTO
     * @param result
     * @param model
     * @return addResType.jsp (allResourcesTypes.jsp page if resource type is not valid)
     */
    @PreAuthorize("hasRole('ROLE_REGISTRATOR')")
    @RequestMapping(value = "/addrestype", method = RequestMethod.POST)
    public String addResourceType(@Valid @ModelAttribute("newrestype") ResourceTypeDTO resourceTypeDTO,
            BindingResult result, Model model) {
        logger.info("begin method for adding new resource type (subclass)");
        validator.validate(resourceTypeDTO, result);
        if (result.hasErrors()) {
            logger.warn("end method: resource type is not valid, "
                    + "return to page for adding all resource types");
            return "addResType";
            
        } 
        resourceTypeService.addResourceTypeDTO(resourceTypeDTO);
        model.addAttribute("resourceType", resourceTypeDTO);
        logger.info("end method: return to page for showing all resource types (subclasses)");
        return "redirect:/registrator/resourcetypes/show-res-types";
        
    }
}
