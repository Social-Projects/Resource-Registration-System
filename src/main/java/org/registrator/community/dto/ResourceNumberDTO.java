package org.registrator.community.dto;

public class ResourceNumberDTO {
    private Integer number;
    private String registratorNumber;

    public ResourceNumberDTO() {
    }

    public ResourceNumberDTO(Integer number, String resourceNumber) {
        this.number = number;
        this.registratorNumber = resourceNumber;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getRegistratorNumber() {
        return registratorNumber;
    }

    public void setRegistratorNumber(String resourceNumber) {
        this.registratorNumber = resourceNumber;
    }

}
