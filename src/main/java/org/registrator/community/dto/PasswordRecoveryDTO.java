package org.registrator.community.dto;

import javax.validation.constraints.Pattern;

public class PasswordRecoveryDTO {

    private String password;

    private String confirmPassword;

    private String hash;
    
    @Pattern(regexp = "^[\\w]{4,30}$",message = "{msg.registration.login}")
    private String login;

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

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

}
