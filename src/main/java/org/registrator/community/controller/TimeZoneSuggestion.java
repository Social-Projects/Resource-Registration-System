package org.registrator.community.controller;

import java.util.Collections;
import java.util.List;

import org.registrator.community.dto.TimeZoneDTO;
import org.registrator.community.exceptions.ExternalApiCallException;
import org.registrator.community.service.TimeZoneService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for providing REST service for suggesting time zone by name or city
 */
@RestController
public class TimeZoneSuggestion {
    
    public enum Status {
        OK,
        ERROR
    }
    
    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(TimeZoneSuggestion.class);
    @Autowired
    private TimeZoneService timeZoneService;
    /**
     * Find the list of suitable time zones by name or by city
     *
     * @param searchValue either name of the time zone or city name to look up for time zone
     * @return list of suitable time zones
     */

    @RequestMapping(value = "/timeZones", method = RequestMethod.GET)
    public ResponseEntity<TimeZoneResponse> getTimeZonesSuggestions(@RequestParam("value") String searchValue,
                                                                    @RequestParam(value = "lang", defaultValue = "uk") String language) 
                                                                            throws ExternalApiCallException {
        List<TimeZoneDTO> timeZones = timeZoneService.findByNameOrCity(searchValue, language);
        TimeZoneResponse data = new TimeZoneResponse(timeZones);
        HttpStatus httpStatus = HttpStatus.OK;
        if (timeZones.isEmpty()) {
            httpStatus = HttpStatus.NOT_FOUND;
            data.setStatus(Status.ERROR.toString());
            data.setMessage("Not Found");
        }

        return new ResponseEntity<>(data, httpStatus);
    }

    @ExceptionHandler(ExternalApiCallException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public TimeZoneResponse externalApiException(ExternalApiCallException ex) {
        LOGGER.error("External API error, message {}", ex.getMessage(), ex);
        return new TimeZoneResponse(Status.ERROR.toString(), ex.getMessage(), Collections.emptyList());
    }

    @SuppressWarnings("unused")
    private static class TimeZoneResponse {
        private String status;
        private String message;
        private List<TimeZoneDTO> data;

        public TimeZoneResponse(List<TimeZoneDTO> data) {
            this.status = Status.OK.toString();
            this.message = "";
            this.data = data;
        }

        public TimeZoneResponse(String status, String message, List<TimeZoneDTO> data) {
            this.status = status;
            this.message = message;
            this.data = data;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getStatus() {
            return status;
        }

        public String getMessage() {
            return message;
        }

        public List<TimeZoneDTO> getData() {
            return data;
        }
    }


}
