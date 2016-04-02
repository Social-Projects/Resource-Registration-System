package org.registrator.community.dto;

import java.io.Serializable;

public class CommunityDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String name;
    private Integer territorialCommunityId;
    private String registrationNumber;

    public CommunityDTO(){
        
    }
    
    public CommunityDTO(String name, Integer territorialCommunityId) {
        this.name = name;
        this.territorialCommunityId = territorialCommunityId;
    }
    
    public CommunityDTO(String name, Integer territorialCommunityId, String registrationNumber){
        this.name = name;
        this.territorialCommunityId = territorialCommunityId;
        this.registrationNumber = registrationNumber;
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

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }
    
}
