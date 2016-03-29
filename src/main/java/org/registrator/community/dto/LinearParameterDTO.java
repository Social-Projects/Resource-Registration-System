package org.registrator.community.dto;

public class LinearParameterDTO {
private String description;
private String unitName;

public LinearParameterDTO() {

}
public LinearParameterDTO(String description, String unitName) {
this.description = description;
this.unitName = unitName;
}
public String getDescription() {
return description;
}
public void setDescription(String description) {
this.description = description;
}
public String getUnitName() {
return unitName;
}
public void setUnitName(String unitName) {
this.unitName = unitName;
}
@Override
public String toString() {
return "Опис лінійних параметрів " + description + "\n" + "Одиниці виміру: " + unitName + "\n";

}

}
