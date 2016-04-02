package org.registrator.community.controller.administrator;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.DocumentException;
import org.registrator.community.dto.InquiryListDTO;
import org.registrator.community.dto.UserNameDTO;
import org.registrator.community.enumeration.InquiryType;
import org.registrator.community.service.InquiryService;
import org.registrator.community.service.PrintService;
import org.registrator.community.service.UserService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Class controller works with procurations of entering data into the register
 * (input inquiry) and with procurations for an extract from register (output
 * inquiry).
 *
 * @author Ann
 *
 */
@Controller
@RequestMapping(value = "/inquiry/add/")
public class InquiryController {

    @Autowired
    private Logger logger;
    @Autowired
    private InquiryService inquiryService;
    @Autowired
    private PrintService printService;
    @Autowired
    private UserService userService;

    private static String EXTRACT = "extract.pdf";
    private static String PROCURATION_ON_SUBMIT = "procuration_on_submit.pdf";
    private static String MANDATE_TO_EXTRACT = "mandate_to_extract.pdf";

    /**
     * Method for showing form on UI to input the parameters for inquiry to get
     * the certificate aboute the resource (with existing registrators and
     * resources).
     *
     * @param model
     *            - the model
     * @return inquiryAddOut.jsp
     */
    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/outputInquiry", method = RequestMethod.GET)
    public String showOutputInquiry(Model model) {
        String userLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        logger.info("userLogin = " + userLogin);
        List<UserNameDTO> listUserNameDTO = inquiryService.listUserNameDTO(userLogin);
        model.addAttribute("registrators", listUserNameDTO);
        return "inquiryAddOut";
    }

    /**
     * Method saves the data in the table inquiry_list.
     *
     * @param resourceIdentifier
     *            - identifier of the resource.
     * @param registratorLogin
     *            - login of chosen registrator.
     * @return listInqUserOut.jsp
     */
    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/addOutputInquiry", method = RequestMethod.POST)
    public String addOutputInquiry(String resourceIdentifier, String registratorLogin) {
        String userLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        logger.info("userLogin = " + userLogin);
        inquiryService.addOutputInquiry(resourceIdentifier, registratorLogin, userLogin);
        return "redirect:/inquiry/add/listInqUserOut";
    }

    /**
     * Method for showing all output inquiries from logged user on UI.
     *
     * @param model
     *            - the model
     * @return listInqUserOut.jsp
     */
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_REGISTRATOR') or hasRole('ROLE_COMMISSIONER')")
    @RequestMapping(value = "/listInqUserOut", method = RequestMethod.GET)
    public String listInqUserOut(Model model) {
        String userLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        String role = userService.getUserByLogin(userLogin).getRole().getType().toString();
        logger.info("user role = " + role);
        List<InquiryListDTO> listInquiryUserOut = inquiryService.listInquiryUser(userLogin, InquiryType.OUTPUT);
        model.addAttribute("listInquiryUserOut", listInquiryUserOut);
        model.addAttribute("role", role);
        return "listInqUserOut";
    }

    /**
     * Method for showing all input inquiries from logged user on UI.
     *
     * @param model
     *            - the model
     * @return listInquiryUserInput.jsp
     */
    @PreAuthorize("hasRole('ROLE_REGISTRATOR') or hasRole('ROLE_USER') or hasRole('ROLE_COMMISSIONER')")
    @RequestMapping(value = "/listInquiryUserInput", method = RequestMethod.GET)
    public String listInquiryUserInput(Model model) {
        String userLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        List<InquiryListDTO> listInquiryUserInput = inquiryService.listInquiryUser(userLogin, InquiryType.INPUT);
        model.addAttribute("listInquiryUser", listInquiryUserInput);
        return "listInquiryUserInput";
    }

    /**
     * Method for deleting chosen inquiry by Id.
     *
     * @param inquiryId
     *            - inquiry identifier.
     * @return listInqUserOut.jsp
     */
    @PreAuthorize("hasRole('ROLE_REGISTRATOR')")
    @RequestMapping(value = "/delete/{inquiryId}")
    public String deleteInquiry(@PathVariable Integer inquiryId) {
        inquiryService.removeInquiry(inquiryId);
        return "redirect:/inquiry/add/listInqUserOut";
    }

    /**
     * <p>
     * Method for generating pdf document "mandate to extract" on button
     * pressing
     * </p>
     *
     * @author Vitalii Horban
     */

    @PreAuthorize("hasRole('ROLE_REGISTRATOR') or hasRole('ROLE_USER')")
    @RequestMapping(value = "/printOutput/{inquiryId}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> downloadFile(HttpServletResponse response,
                                               @PathVariable("inquiryId") Integer inquiryId) {

        ResponseEntity<byte[]> responseContent = null;
        try {
            logger.info(
                    "start controller: InquiryController.downloadFile(HttpServletResponse response, Integer inquiryId)");
            ByteArrayOutputStream bos = printService.printProcuration(inquiryId);
            byte[] array = bos.toByteArray();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("application/pdf"));
            String filename = MANDATE_TO_EXTRACT;
            headers.setContentDispositionFormData(filename, filename);
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
            responseContent = new ResponseEntity<byte[]>(array, headers, HttpStatus.OK);
            logger.info(
                    "finish controller: InquiryController.downloadFile(HttpServletResponse response,Integer inquiryId)");
        } catch (IOException | DocumentException e) {
            logger.error(
                    "Failed to finish controller: InquiryController.downloadFile(HttpServletResponse response,Integer inquiryId)"
                            + e.toString());
        }
        return responseContent;
    }

    /**
     * <p>
     * Method for generating pdf document "extract" on button pressing
     * </p>
     *
     * @author Vitalii Horban
     */
    @PreAuthorize("hasRole('ROLE_REGISTRATOR')")
    @RequestMapping(value = "/printExtract/{inquiryId}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> downloadExtractFile(HttpServletResponse response,
                                                      @PathVariable("inquiryId") Integer inquiryId) {

        ResponseEntity<byte[]> responseContent = null;
        try {
            logger.info(
                    "start controller: InquiryController.downloadExtractFile(HttpServletResponse response, Integer inquiryId)");
            ByteArrayOutputStream bos = printService.printExtract(inquiryId);
            byte[] array = bos.toByteArray();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("application/pdf"));
            String filename = EXTRACT;
            headers.setContentDispositionFormData(filename, filename);
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
            responseContent = new ResponseEntity<byte[]>(array, headers, HttpStatus.OK);
            logger.info(
                    "finish controller: InquiryController.downloadExtractFile(HttpServletResponse response,Integer inquiryId)");
        } catch (IOException | DocumentException e) {
            logger.error(
                    "Failed to finish controller: InquiryController.downloadExtractFile(HttpServletResponse response,Integer inquiryId)"
                            + e.toString());
        }
        return responseContent;
    }

    /**
     * <p>
     * Method for generate pdf document "ProcurationOnSubmit" on button pressing
     * </p>
     *
     * @author Vitalii Horban
     */
    @PreAuthorize("hasRole('ROLE_REGISTRATOR')")
    @RequestMapping(value = "/printdata/{inquiryId}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> downloadInfoFile(HttpServletResponse response,
                                                   @PathVariable("inquiryId") Integer inquiryId) {

        ResponseEntity<byte[]> responseContent = null;
        try {
            logger.info(
                    "start controller: InquiryController.downloadInfoFile(HttpServletResponse response, Integer inquiryId)");

            ByteArrayOutputStream bos = printService.printProcurationOnSubmitInfo(inquiryId);
            byte[] array = bos.toByteArray();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("application/pdf"));
            String filename = PROCURATION_ON_SUBMIT;
            headers.setContentDispositionFormData(filename, filename);
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
            responseContent = new ResponseEntity<byte[]>(array, headers, HttpStatus.OK);
            logger.info(
                    "finish controller: InquiryController.downloadInfoFile(HttpServletResponse response,Integer inquiryId)");
        } catch (IOException | DocumentException e) {
            logger.error(
                    "Failed to finish controller: InquiryController.downloadInfoFile(HttpServletResponse response,Integer inquiryId)"
                            + e.toString());
        }
        return responseContent;

    }

}
