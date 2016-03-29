package org.registrator.community.dto.json;

public class RoleTypeJson {
	private String login;
	private String role;

	public RoleTypeJson(String login, String role) {
		this.login = login;
		this.role = role;
	}
	public RoleTypeJson(){
		
	}
	
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	@Override
	public String toString() {
		return "RoleTypeJson [login=" + login + ", role=" + role + "]";
	}
}

