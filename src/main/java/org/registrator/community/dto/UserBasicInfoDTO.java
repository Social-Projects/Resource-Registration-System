package org.registrator.community.dto;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * UserBasicInfoDTO class contains all basic information about user 
 * required for registration and editing user
 *
 */
public class UserBasicInfoDTO {
    
    private static final String ONLY_LITERALS = "[а-яіїєА-ЯІЇЄa-zA-Z,\\-]+";
    
    @Size(min=1, max=30, message="{msg.registration.nameRestriction}")
    @Pattern(regexp = ONLY_LITERALS, message = "{msg.registration.onlyLetters}")
    private String firstName;
    
    @Size(min=1, max=30, message="{msg.registration.secondname}")
    @Pattern(regexp = ONLY_LITERALS, message = "{msg.registration.onlyLetters}")
    private String lastName;
    
    @Pattern(regexp = "[[а-яіїєА-ЯІЇЄa-zA-Z,\\-]){1,30}]*", message = "{msg.registration.restriction}")
    private String middleName;
    
    @NotEmpty(message = "{msg.notEmptyField}")
    @Email(message="{msg.registration.wrongemail}")
    private String email;
     
    @Valid
    private AddressDTO address;
    
    @Valid
    private PassportDTO passport;
    
    @Pattern (regexp = "[[0-9]{10}]*", message = "{msg.registration.incorrectPhoneNumber}")
    private String phoneNumber;
    
    @NotEmpty(message = "{msg.notEmptyField}")
    private String territorialCommunity;
    
    public UserBasicInfoDTO() {
        
    }
    
    public UserBasicInfoDTO(String firstName, String lastName, String middleName, String email,
            AddressDTO address, PassportDTO passport) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.email = email;
        this.address = address;
        this.passport = passport;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }

    public PassportDTO getPassport() {
        return passport;
    }

    public void setPassport(PassportDTO passport) {
        this.passport = passport;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getTerritorialCommunity() {
        return territorialCommunity;
    }

    public void setTerritorialCommunity(String territorialCommunity) {
        this.territorialCommunity = territorialCommunity;
    }
    
}
