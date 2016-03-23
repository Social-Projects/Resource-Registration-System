package org.registrator.community.dto.json;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.registrator.community.enumeration.ParameterValueCompare;

/**
 * Represents three values needed for resource search by parameter: parameter id (we don't know here is it discrete
 * or linear parameter), compare operation (equal, less, greater, linear) and value to compare.
 *
 */
public class ResourceSearchParameterJson {
    private Integer id;
    private ParameterValueCompare compare;
    private Double value;


    public Integer getId() {
        return id;
    }

    public ParameterValueCompare getCompare() {
        return compare;
    }

    public Double getValue() {
        return value;
    }


    public void setId(Integer id) {
        this.id = id;
    }

    @JsonIgnore
    public void setCompare(ParameterValueCompare compare) {
        this.compare = compare;
    }

    //used to deserialize from json, do not delete :-)
    public void setCompare(String compareString) {
        this.compare = ParameterValueCompare.from(compareString);
    }

    public void setValue(Double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "id:" + id.toString() + ",compare:" + compare + "value,:" + value;
    }
}
