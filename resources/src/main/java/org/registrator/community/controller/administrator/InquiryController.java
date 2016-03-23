package org.registrator.community.controller.administrator;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


import org.registrator.community.dao.ResourceRepository;
import org.registrator.community.dto.InquiryDTO;
import org.registrator.community.dto.TomeDTO;
import org.registrator.community.entity.Inquiry;
import org.registrator.community.entity.Resource;
import org.registrator.community.entity.ResourceType;
import org.registrator.community.entity.Tome;
import org.registrator.community.service.InquiryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value ="/inquiry/add/")
public class InquiryController {
	
	@Autowired
	InquiryService inquiryService;
		
	@Autowired
	ResourceRepository resourceRepository;
	
	
	@RequestMapping(value = "/outputI", method = RequestMethod.GET)
	public String showOutputInquiry(Model model) {
		List<TomeDTO>  listTomeDTO = inquiryService.listTomeDTO();
		model.addAttribute("tomes", listTomeDTO);
		Iterable<Resource> resources = resourceRepository.findAll();
		model.addAttribute("resources", resources);
		return "inquiryAddOut";
	}
	
	@RequestMapping(value = "/addOutputI", method = RequestMethod.POST)
	public String addOutputInquiry(InquiryDTO inquiryDTO) {  			//(InquiryDTO inquiryDTO, HttpSession session)
		String userLogin = "ivan";
		//String userLogin =(String) session.getAttribute("login");	
		inquiryService.addOutputInquiry(inquiryDTO, userLogin);
		return  "confimAddOutputI";	
	}
	
	/*
	@ResponseBody
	@RequestMapping(value ="/output/{inquiryListDTO}",method = RequestMethod.GET)
	public Inquiry testAddOutputInquiry(@PathVariable("inquiryListDTO") String resourceIdentifier){
		 return inquiryService.testAddOutputInquiry(resourceIdentifier);
	}
	
	@RequestMapping(value="/page/",method=RequestMethod.GET)
	public String getOutputInquiryPage(){
		return "inquiryAddOutput";
	}*/
	
	
}
