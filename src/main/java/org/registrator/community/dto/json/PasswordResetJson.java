package org.registrator.community.dto.json;

import javax.validation.constraints.Pattern;

public class PasswordResetJson {
    @Pattern(regexp = "^[\\w]{4,30}$")
    private String login;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
