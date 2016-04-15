package org.registrator.community.controller;

import com.google.gson.Gson;
import org.registrator.community.dto.ParameterSearchResultDTO;
import org.registrator.community.dto.ResourceDTO;
import org.registrator.community.dto.UserDTO;
import org.registrator.community.dto.json.PolygonJson;
import org.registrator.community.dto.json.ResourceSearchJson;
import org.registrator.community.dto.json.SearchResultJson;
import org.registrator.community.entity.*;
import org.registrator.community.exceptions.ResourceEntityNotFound;
import org.registrator.community.service.*;
import org.registrator.community.utils.HttpUtils;
import org.registrator.community.validator.ResourceDTOValidator;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;

@Controller
@RequestMapping(value = "/registrator/resource")
public class ResourceController {

    @Autowired
    private Logger logger;

    @Autowired
    private ResourceDTOValidator validator;

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private ResourceDeleteService resourceDeleteService;

    @Autowired
    private ResourceTypeService resourceTypeService;

    @Autowired
    private DiscreteParameterService discreteParameterService;

    @Autowired
    private LinearParameterService linearParameterService;

    @Autowired
    private UserService userService;

    /**
     * Method for loading form for input the parameter of resource (with
     * existing resource types)
     *
     * @param model
     * @return addResource.jsp
     */
    @PreAuthorize("hasRole('ROLE_REGISTRATOR')")
    @RequestMapping(value = "new", method = RequestMethod.GET)
    public String addResourceForm(Model model) {
        ResourceDTO resourceDTO = resourceService.createNewResourceDTO();

        List<ResourceType> listOfResourceType = resourceTypeService.findAll();
        model.addAttribute("listOfResourceType", listOfResourceType);
        model.addAttribute("resource", resourceDTO);
        model.addAttribute("editMode", false);
        return "addResource";
    }

    @PreAuthorize("hasRole('ROLE_REGISTRATOR')")
    @RequestMapping(value = "edit", method = RequestMethod.GET)
    public String editResourceForm(@RequestParam(value = "id") String identifier,
                                  Model model) throws ResourceEntityNotFound {
        ResourceDTO resourceDTO = resourceService.findByIdentifier(identifier);
        if (!resourceService.userCanEditResource(resourceDTO)) {
            model.addAttribute("noEdit", true);
            model.addAttribute("id", resourceDTO.getIdentifier());
            return "redirect:get?id={id}";
        }

        List<ResourceType> listOfResourceType = resourceTypeService.findAll();
        model.addAttribute("listOfResourceType", listOfResourceType);
        model.addAttribute("resource", resourceDTO);
        model.addAttribute("editMode", true);
        return "addResource";
    }


    /**
     * Method save the resource with all parameters from UI in database
     *
     * @param resourceDTO
     * @param result
     * @param model
     * @return showResource.jsp (addResource.jsp page if resource not valid)
     */
    @PreAuthorize("hasRole('ROLE_REGISTRATOR')")
    @RequestMapping(value = {"/new", "/edit"}, method = RequestMethod.POST)
    public String addResource(@Valid @ModelAttribute("resource") ResourceDTO resourceDTO,
                              BindingResult result,
                              Model model) {

        logger.debug("The ownerLogin is " + resourceDTO.getOwnerLogin());

        validator.validate(resourceDTO, result);
        if (result.hasErrors()) {
            logger.info("The resoursrDTO is not valid");
            model.addAttribute("resource", resourceDTO);
            return "addResource";
        }

        User registrator = userService.getLoggedUser();

        resourceDTO = resourceService.saveResource(resourceDTO, registrator);
        logger.debug("Resource was successfully saved");
        model.addAttribute("id", resourceDTO.getIdentifier());
        return "redirect:get?id={id}";

    }

    /**
     * Show the information about resource by identifier
     *
     * @param identifier
     * @param model
     * @return
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public String getResourceByIdentifier(@RequestParam(value = "id") String identifier,
                                          @RequestParam(value = "noEdit", defaultValue = "false") boolean noEdit,
                                          Model model)
            throws ResourceEntityNotFound {

        ResourceDTO resourceDTO = resourceService.findByIdentifier(identifier);
        resourceDTO.setNoEdit(noEdit);
        model.addAttribute("resource", resourceDTO);
        return "showResource";
    }
    
    /**
     * Handling ResourceEntityNotFound exception
     */
    @ExceptionHandler(ResourceEntityNotFound.class)
    public ModelAndView handleResourceEntityNotFound(HttpServletRequest request, ResourceEntityNotFound exception) {
        logger.warn("Request: " + HttpUtils.getFullRequestURL(request)
                + " raised " + exception.getClass().getName());

        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", exception);
        mav.setViewName("resourceEntityNotFound");
        return mav;
    }

    /**
     * Load the list of all resource parameters of selected resource type
     *
     * @param typeName
     * @param model
     * @return resourceValues.jsp
     */
    @RequestMapping(value = "/getParameters", method = RequestMethod.POST)
    public String add(@RequestParam("resourceTypeName") String typeName, Model model) {

        /* find the resource type in database by type name */
        ResourceType resType = resourceTypeService.findByName(typeName);

        /* find the resource parameters on resType */
        List<DiscreteParameter> discreteParameters = discreteParameterService.findAllByResourceType(resType);
        List<LinearParameter> linearParameters = linearParameterService.findAllByResourceType(resType);

        model.addAttribute("discreteParameters", discreteParameters);
        model.addAttribute("linearParameters", linearParameters);
        return "resourceValues";
    }

    /**
     * Depending on chosen resource type store all parameters and send them to
     * view at the page Resource search by parameters
     *
     * @param i     - Resource type, received from view
     * @param model
     * @return
     */
    @RequestMapping(value = "/getResourcesByTypeId", method = RequestMethod.POST)
    public String showAllResourcesByTypeId(@RequestParam("resourceTypeId") Integer i, Model model) {
        ResourceType type = resourceTypeService.findById(i);

        List<DiscreteParameter> discreteParameters = discreteParameterService.findAllByResourceType(type);
        List<LinearParameter> linearParameters = linearParameterService.findAllByResourceType(type);

        model.addAttribute("discreteParameters", discreteParameters);
        model.addAttribute("linearParameters", linearParameters);
        return "parameters";
    }

    /**
     * Depending on received parameters create List of resourceDTO and send them
     * to view
     *
     * @param json  - search parameters in JSON format
     * @return
     */

    @ResponseBody
    @PreAuthorize("hasRole('ROLE_REGISTRATOR') or hasRole('ROLE_USER')")
    @RequestMapping(value = "/resourceSearch", method = RequestMethod.POST)
    public SearchResultJson resourceSearch(@RequestBody ResourceSearchJson json) {
        SearchResultJson result = new SearchResultJson();
        ParameterSearchResultDTO searchResult = resourceService.getAllByParameters(json);
        long countResults = searchResult.getCount();
        List<Resource> resources = searchResult.getResources();
        List<PolygonJson> polygons = new ArrayList<>();

        int countPolygons = 0;
        for (Resource resource : resources) {
            polygons.addAll(resourceService.createPolygonJSON(resource, countPolygons));
            countPolygons = polygons.size();
        }

        result.setPolygons(polygons);
        result.setCountPolygons(countResults);

        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/decs", method = RequestMethod.GET)
    public Map<String, Set<String>> getDescriptionProposition(@RequestParam("descTag") String descTag) {
        Map<String, Set<String>> suggestions = new HashMap<String, Set<String>>();
        // suggestions.put("query", new TreeSet<String>().add("unit"));
        suggestions.put("suggestions", resourceService.getDescriptionBySearchTag(descTag));
        return suggestions;
    }

    /**
     * Create the Set of resources identifiers depending on received parameters
     * and generate JSON response
     *
     * @param minLat  - minimum latitude
     * @param maxLat  - maximum latitude
     * @param minLng  - minimum longitude
     * @param maxLng  - maximum longitude
     * @param resType - resource type id
     * @param model
     * @return JSON with information about polygons which can be located between
     * received coordinates
     */

    @ResponseBody
    @RequestMapping(value = "/getResourcesByAreaLimits", method = RequestMethod.POST)
    public String showAllResourcesByAreaLimits(@RequestParam("minLat") Double minLat,
                                               @RequestParam("maxLat") Double maxLat, @RequestParam("minLng") Double minLng,
                                               @RequestParam("maxLng") Double maxLng, @RequestParam("resType") String resType,
                                               @RequestParam("page") Integer page, Model model) {
        SearchResultJson result = new SearchResultJson();
        Integer countResults = resourceService.countAllByAreaLimits(minLat, maxLat, minLng, maxLng);

        int countPolygons = 0;
        Set<Resource> resources = resourceService.getAllByAreaLimits(minLat, maxLat, minLng, maxLng, resType, page);
        List<PolygonJson> polygons = new ArrayList<>();

        for (Resource resource : resources) {
            polygons.addAll(resourceService.createPolygonJSON(resource, countPolygons));
            countPolygons = polygons.size();
        }

        result.setPolygons(polygons);
        result.setCountPolygons(countResults);

        Gson gson = new Gson();
        return gson.toJson(result);
    }

    /**
     * Search on map by point Create set of resource identifiers depending on
     * received point coordinates
     *
     * @param lat   - point latitude
     * @param lng   - point longitude
     * @param model
     * @return JSON with information about polygons where received point can be
     * located
     */
    @ResponseBody
    @RequestMapping(value = "/getResourcesByPoint", method = RequestMethod.POST)
    public String showAllResourcesByAreaLimits(@RequestParam("lat") Double lat, @RequestParam("lng") Double lng,
                                               @RequestParam("page") Integer page, Model model) {
        SearchResultJson result = new SearchResultJson();
        Integer countResults = resourceService.countAllByPoint(lat, lng);

        Set<Resource> resources = resourceService.getAllByPoint(lat, lng, page);
        List<PolygonJson> polygons = new ArrayList<>();

        int countPolygons = 0;
        for (Resource resource : resources) {
            polygons.addAll(resourceService.createPolygonJSON(resource, countPolygons));
            countPolygons = polygons.size();
        }

        result.setPolygons(polygons);
        result.setCountPolygons(countResults);

        Gson gson = new Gson();
        return gson.toJson(result);
    }

    /**
     * View for the Search on map page
     *
     * @param model
     * @return
     */
    @PreAuthorize("hasRole('ROLE_REGISTRATOR') or hasRole('ROLE_USER')")
    @RequestMapping(value = "/searchOnMap", method = RequestMethod.GET)
    public String searchOnMap(Model model) {
        List<ResourceType> resourceTypes = resourceTypeService.findAll();
        model.addAttribute("resourceTypes", resourceTypes);
        return "searchOnMap";
    }

    /**
     * Find the list of owners with similar surname
     *
     * @param ownerDesc corresponds to first letters of surname
     * @return userList list of users
     */
    @ResponseBody
    @RequestMapping(value = "/owners", method = RequestMethod.POST)
    public List<UserDTO> getOwnersSuggestions(@RequestParam("ownerDesc") String ownerDesc) {
        List<UserDTO> userList = userService.getUserBySearchTag(ownerDesc);
        return userList;
    }

    /**
     * Find the selected owner by login
     *
     * @param ownerLogin
     * @return owner
     */
    @ResponseBody
    @RequestMapping(value = "/getOwnerInfo", method = RequestMethod.GET)
    public UserDTO getOwnerInfo(@RequestParam("ownerLogin") String ownerLogin) {
        return userService.getUserDto(ownerLogin);
    }

    @ResponseBody
    @RequestMapping(value = "/showAllResources", method = RequestMethod.POST)
    public String showAllResources(@RequestParam("resType") Integer resType) {
        ResourceType resourceType = resourceTypeService.findById(resType);
        List<Resource> resources = resourceService.findByType(resourceType);
        List<PolygonJson> polygons = new ArrayList<>();

        int countPolygons = 0;
        for (Resource resource : resources) {
            polygons.addAll(resourceService.createPolygonJSON(resource, countPolygons));
            countPolygons = polygons.size();
        }

        Gson gson = new Gson();
        return gson.toJson(polygons);
    }

    /**
     * Method delete resource with given identifier.
     *
     * @param resourceIdentifier - identifier of the resource.
     * @return searchOnMap.jsp
     */
    @PreAuthorize("hasRole('ROLE_REGISTRATOR')")
    @RequestMapping(value = "/delete/{resourceIdentifier}")
    public String deleteResource(@PathVariable String resourceIdentifier) {
        logger.info("begin deleteResource, param resourceIdentifier = " + resourceIdentifier);
        resourceDeleteService.deleteResource(resourceIdentifier);
        logger.info("end deleteResource");
        return "redirect:/registrator/resource/searchOnMap";
    }
    
    

}