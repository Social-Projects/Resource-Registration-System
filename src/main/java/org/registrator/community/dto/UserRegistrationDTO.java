package org.registrator.community.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * UserRegistrationDTO class contains additional information 
 * required only for new user registration
 *
 */
public class UserRegistrationDTO extends UserBasicInfoDTO{
     
    @NotEmpty(message = "{msg.notEmptyField}")
    @Pattern(regexp = "[a-zA-Z0-9].{5,20}",message = "{msg.registration.login}")
    private String login;
    
    @Pattern(regexp = "[a-zA-Z0-9].{5,20}",message = "{msg.registration.password}")
    private String password;
    
    @NotEmpty(message = "{msg.notEmptyField}")
    private String confirmPassword;
    
    @NotNull(message = "{msg.notEmptyField}")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date dateOfAccession;
    
    public UserRegistrationDTO() {
        
    }
   
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }



    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }


    public String getConfirmPassword() {
        return confirmPassword;
    }


    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }


    public Date getDateOfAccession() {
        return dateOfAccession;
    }


    public void setDateOfAccession(Date dateOfAccession) {
        this.dateOfAccession = dateOfAccession;
    }   

}