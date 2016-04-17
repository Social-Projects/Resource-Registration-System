package org.registrator.community.dto.json;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

public class UserStatusJson {
	@NotEmpty
	@Pattern(regexp = "^[\\w]{4,30}$")
    private String login;
	
    @NotEmpty
    @Pattern(regexp = "BLOCK|ACTIVE|INACTIVE|NOTCOMFIRMED")
    private String status;

    public UserStatusJson(String login, String status) {
        this.login = login;
        this.status = status;
    }

	public UserStatusJson() {
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
	public String toString() {
		return "UserStatusJson [login=" + login + ", status=" + status + "]";
	}
}
