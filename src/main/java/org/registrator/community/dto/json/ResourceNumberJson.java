package org.registrator.community.dto.json;

import javax.validation.constraints.Pattern;

public class ResourceNumberJson {
    @Pattern(regexp = "^[\\w]{4,30}$")
    private String login;
    
    private String resource_number;
    private String registrator_number;
    private String identifier;

    public ResourceNumberJson(String resource_number, String registrator_number, String identifier) {
        this.identifier = identifier;
        this.resource_number = resource_number;
        this.registrator_number = registrator_number;
    }

    public ResourceNumberJson() {
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getRegistratorNumber() {
        return registrator_number;
    }

    public void setRegistratorNumber(String registrator_number) {
        this.registrator_number = registrator_number;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getResourceNumber() {
        return resource_number;
    }

    public void setResourceNumber(String resource_number) {
        this.resource_number = resource_number;
    }

}
