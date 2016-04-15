package org.registrator.community.controller.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.text.MessageFormat;

@Controller
@RequestMapping("/error/")
class ErrorController {
    
    private Logger logger = LoggerFactory.getLogger(ErrorController.class);


    /**
     * Display page for HTTP 404 Not Found error
     */
    @RequestMapping("/httpResourceNotFound")
    public String httpResourceNotFoundError() {
        return "errorPage404";
	}

    /**
     * Display an uncaught exception error page, as defined in web.xml <code>generalError</code> element.
     */
    @RequestMapping("/generalError")
    public String generalError(HttpServletRequest request, Model model) {
        String requestUri = (String) request.getAttribute("javax.servlet.error.request_uri");
        if (requestUri == null) {
            requestUri = "Unknown";
        }
        
        // retrieve some useful information from the request
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
        String exceptionMessage = getExceptionMessage(throwable, statusCode);
        
        String message = MessageFormat.format("{0} returned for {1} with message {2}", 
            statusCode, requestUri, exceptionMessage
        ); 
        logger.error(message, throwable);
        
        model.addAttribute("errorMessage", message);
        model.addAttribute("requestUri", requestUri);
        model.addAttribute("exception", throwable);
        return "generalError";
    }
	
	

	private String getExceptionMessage(Throwable throwable, Integer statusCode) {
        Throwable cause;
        // get root cause of exception
        if (throwable != null) {
            while((cause = throwable.getCause()) != null) {
                throwable = cause;
            }
			return throwable.getMessage();
		}
		HttpStatus httpStatus = HttpStatus.valueOf(statusCode);
		return httpStatus.getReasonPhrase();
	}
}
