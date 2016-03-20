package org.registrator.community.dto;

public class CommunityDTO {
	private String name;
	private Integer territorialCommunityId;

	public CommunityDTO(){
		
	}
	
	public CommunityDTO(String name, Integer territorialCommunityId) {
		this.name = name;
		this.territorialCommunityId = territorialCommunityId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getTerritorialCommunityId() {
		return territorialCommunityId;
	}

	public void setTerritorialCommunityId(Integer territorialCommunityId) {
		this.territorialCommunityId = territorialCommunityId;
	}
	
	
}
