package org.registrator.community.controller;

import org.registrator.community.dto.ResourceTypeDTO;
import org.registrator.community.service.ResourceTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/registrator/resourcetypes/")
public class AddResourceTypeController {

	@Autowired
	ResourceTypeService resourceTypeService;

	/**
	 * Method for adding form for input the parameter of resource type
	 */
	@RequestMapping(value = "/addrestype", method = RequestMethod.GET)
	public String addResourceTypeForm(Model model) {
		model.addAttribute("newrestype", new ResourceTypeDTO());
		return "addResType";
	}
	
	/**
	 * Method save the resource type in resource_types
	 */
	@RequestMapping(value = "/addrestype", method = RequestMethod.POST)
	public String addResource(@ModelAttribute("newrestype") ResourceTypeDTO resourceTypeDTO, Model model) {
		resourceTypeService.addResourceTypeDTO(resourceTypeDTO);
		model.addAttribute("resourceType", resourceTypeDTO);
		return "redirect:/registrator/resourcetypes/show-res-types";
	}
}
