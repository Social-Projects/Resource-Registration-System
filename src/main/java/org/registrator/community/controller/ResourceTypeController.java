package org.registrator.community.controller;

import java.util.List;

import org.registrator.community.entity.ResourceType;
import org.registrator.community.service.ResourceTypeService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping(value = "/registrator/resourcetypes/")
public class ResourceTypeController {

    @Autowired
    private Logger logger;
    @Autowired
    private ResourceTypeService resourceTypeService;
    
    /**
     * Method for showing all types of resources on UI
     * @param model
     * @return allResourcesTypes.jsp
     */
    @PreAuthorize("hasRole('ROLE_REGISTRATOR')")
    @RequestMapping(value = "/show-res-types", method = RequestMethod.GET)
    public String showResourceType(Model model) {
        logger.info("begin method for showing all types of resources");
        List<ResourceType> listOfResourceType = resourceTypeService.findAll();
        model.addAttribute("listOfResourceType", listOfResourceType);
        logger.info("end method for showing all types of resources");
        return "allResourcesTypes";
    }
    
    /**
     * Method for deleting chosen resource type by typeId. If chosen resource
     * type has at least one resource we will get bad_request and it will not be
     * deleted nor from UI by Ajax neither from database
     */
    @PreAuthorize("hasRole('ROLE_REGISTRATOR')")
    @RequestMapping(value = "/delete/{typeId}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<String> deleteResourceType(@PathVariable Integer typeId) {
        logger.info("begin method for deleting chosen resource type with its parameters");
        boolean isDeleted = resourceTypeService.delete(resourceTypeService.findById(typeId));
        if (isDeleted) {
            logger.warn("end method: resource type must be deleted");
            return new ResponseEntity<String>(HttpStatus.OK);
        }
        logger.info("end method: it's impossible to delete resource type");
        return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
    }
}
