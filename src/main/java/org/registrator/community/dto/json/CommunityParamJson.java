package org.registrator.community.dto.json;

public class CommunityParamJson {
	private String login;
	private String communityId;
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getCommunityId() {
		return communityId;
	}
	public void setCommunityId(String communityId) {
		this.communityId = communityId;
	}
	public CommunityParamJson(String login, String communityId) {
		this.login = login;
		this.communityId = communityId;
	}
	public CommunityParamJson() {
	}
	@Override
	public String toString() {
		return "CommunityParamJson [login=" + login + ", communityId=" + communityId + "]";
	}
}
