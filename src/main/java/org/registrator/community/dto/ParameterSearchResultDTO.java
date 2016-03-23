package org.registrator.community.dto;

import org.registrator.community.entity.Resource;

import java.util.List;

/**
 * Created by Oleksiy on 25.02.2016.
 */
public class ParameterSearchResultDTO {
    private List<Resource> resources;
    private long count;

    public List<Resource> getResources() {
        return resources;
    }

    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
