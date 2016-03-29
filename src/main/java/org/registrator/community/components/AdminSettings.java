package org.registrator.community.components;

import org.registrator.community.enumeration.RegistrationMethod;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AdminSettings {
    
    @Autowired
    private Logger logger;  
    private RegistrationMethod registrationMethod = RegistrationMethod.MANUAL;
    
    public AdminSettings(){}
    
    public AdminSettings(RegistrationMethod registrationMethod){
        this.registrationMethod=registrationMethod;
    }
    /**
     * This method changes a value of private field of registration method 
     * @param methodName - one of three possible parameter (registration method)
     */
    public void changeRegMethod(String methodName){
        logger.info("begin change registration method" + "registration method: " + registrationMethod);
        this.registrationMethod = RegistrationMethod.valueOf(methodName.toUpperCase());
        logger.info("end change registration method" + "registration method: " + registrationMethod);
    }

    public RegistrationMethod getRegistrationMethod() {
        return registrationMethod;
    }    
}