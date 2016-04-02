package org.registrator.community.dto;

import javax.validation.constraints.NotNull;

public class ValueDiscreteDTO {
    
    @NotNull(message = "Поле є обов'язковим для введення")
    private Double value;
    private String comment;
    
    public ValueDiscreteDTO() {
        
    }
    
    public Double getValue() {
        return value;
    }


    public void setValue(Double value) {
        this.value = value;
    }


    public String getComment() {
        return comment;
    }
    
    public void setComment(String comment) {
        this.comment = comment;
    }

}
