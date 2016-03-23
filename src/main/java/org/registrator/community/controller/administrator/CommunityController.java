package org.registrator.community.controller.administrator;

import java.util.List;

import javax.validation.Valid;

import org.registrator.community.dto.CommunityDTO;
import org.registrator.community.entity.TerritorialCommunity;
import org.registrator.community.service.CommunityService;
import org.registrator.community.validator.CommunityValidator;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/administrator/communities/")
public class CommunityController {
    
    @Autowired
    private Logger logger;

    @Autowired
    private CommunityService communityService;
    
    @Autowired
    private CommunityValidator validator;
    
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/show-all-communities", method = RequestMethod.GET)
    public String showCommunity(Model model) {
        List<TerritorialCommunity> listOfTerritorialCommunity = communityService.findAll();
        model.addAttribute("listOfTerritorialCommunity", listOfTerritorialCommunity);
        return "showAllCommunity";
    }

    /**
     * Method for loading form for input new territorial community name
     * 
     * @param model
     * @return addNewCommunity.jsp
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/addCommunity", method = RequestMethod.GET)
    public String addNewCommunity(Model model) {
        model.addAttribute("newCommunity", new TerritorialCommunity());
        return "addNewCommunity";
    }

    /**
     * Method for saving new territorial community in the database. Also there
     * is validation for checking whether inputing name already exists in
     * database or not.
     * 
     * @param territorialCommunity
     * @param result
     * @param model
     * @return addNewCommunity.jsp (showAllCommunity.jsp page if community name
     *         is not valid)
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/addCommunity", method = RequestMethod.POST)
    public String addCommunity(@Valid @ModelAttribute("newCommunity") TerritorialCommunity territorialCommunity,
            BindingResult result, Model model) {
        validator.validate(territorialCommunity, result);
        if (result.hasErrors()) {
            logger.info("end method: community name is not valid, "
                    + "return to page for adding new territorial community");
            return "addNewCommunity";

        }
        communityService.addCommunity(territorialCommunity);
        model.addAttribute("territorialCommunity", territorialCommunity);
        return "redirect:/administrator/communities/show-all-communities";
    }

    /**
     * Method for deleting chosen community by territorialCommunityId. If chosen
     * community has at least one user who is in this community we will get
     * bad_request and it will not be deleted nor from UI by Ajax neither from
     * database
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/deleteCommunity/{territorialCommunityId}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<String> deleteCommunity(@PathVariable Integer territorialCommunityId) {
        boolean isDeleted = communityService.deleteCommunity(communityService.findById(territorialCommunityId));
        if (isDeleted) {
            logger.info("end: deleted chosen community");
            return new ResponseEntity<String>(HttpStatus.OK);
        }
        logger.info("end: it's impossible to delete territorial community");
        return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
    }
    
    /**
     * Method display page for edition 
     * @param id community ID to edit
     * @param model 
     * @return editCommunity.jsp
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/editCommunity", method = RequestMethod.GET)
    public String editCommunity(@ModelAttribute("id") Integer id, Model model) {
        logger.info(String.format("open page for edit community with id=%d",id));
        TerritorialCommunity territorialCommunity = communityService.findById(id);
        CommunityDTO communityDTO = new CommunityDTO(territorialCommunity.getName(), territorialCommunity.getTerritorialCommunityId());
        if(territorialCommunity.getRegistrationNumber() != null)
            communityDTO.setRegistrationNumber(territorialCommunity.getRegistrationNumber());
        model.addAttribute("community", communityDTO);
        return "editCommunity";
    }
    
    /**
     * Method update community in database
     * @param id community ID to edit
     * @param model 
     * @return editCommunity.jsp
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/editCommunity", method = RequestMethod.POST)
    public String editCommunity(@Valid @ModelAttribute("communityDTO") CommunityDTO communityDTO, BindingResult result, Model model) {
        logger.info(String.format("request from edit page, to save community with id=%d",communityDTO.getTerritorialCommunityId()));
        TerritorialCommunity territorialCommunity = new TerritorialCommunity();
        territorialCommunity.setName(communityDTO.getName());
        territorialCommunity.setTerritorialCommunityId(communityDTO.getTerritorialCommunityId());
        if(communityDTO.getRegistrationNumber() != null)
            territorialCommunity.setRegistrationNumber(communityDTO.getRegistrationNumber());
        validator.validate(territorialCommunity, result);
        if (result.hasErrors()) {
            logger.info("bad input: Territorial Community with the same unique field exist");
            model.addAttribute("community", communityDTO);
            return "editCommunity";
        }
        boolean isUpdated = communityService.updateCommunity(communityDTO);
        if(!isUpdated) return "editCommunity";
        
        return "redirect:/administrator/communities/show-all-communities";
    }
}