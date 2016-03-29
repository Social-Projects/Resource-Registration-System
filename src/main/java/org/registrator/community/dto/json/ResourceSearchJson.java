package org.registrator.community.dto.json;

import java.util.List;

/**
 * Request parameters from a client-side to search for resource by parameters values.
 */
public class ResourceSearchJson {
    private List<ResourceSearchParameterJson> discreteParameters;
    private List<ResourceSearchParameterJson> linearParameters;
    private Integer resourceTypeId;
    private Integer page;

    public List<ResourceSearchParameterJson> getDiscreteParameters() {
        return discreteParameters;
    }

    public List<ResourceSearchParameterJson> getLinearParameters() {
        return linearParameters;
    }

    public Integer getResourceTypeId() {
        return resourceTypeId;
    }

    public void setResourceTypeId(Integer resourceTypeId) {
        this.resourceTypeId = resourceTypeId;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public String toString() {
        return "=============================================\n"
                +"discreteParams: " + discreteParameters.toString() + "\n"
                +"linearParams: " + linearParameters.toString() + "\n"
                +"resourceTypeId: " + resourceTypeId.toString() +"\n"
                +"==============================================";
    }
}
