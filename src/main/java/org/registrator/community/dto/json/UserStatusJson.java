package org.registrator.community.dto.json;

public class UserStatusJson {
	private String login;
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

}
