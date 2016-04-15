package org.registrator.community.controller.error;

import org.registrator.community.enumeration.UIMessages;
import org.registrator.community.exceptions.BadInputDataException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Exception handler for application
 */

public class MainExceptionHandler {
    

    @ExceptionHandler(BadInputDataException.class)
    @ResponseBody
    public String handleCustomException(BadInputDataException ex) {
        return UIMessages.WRONG_INPUT.toString();
    }

}


