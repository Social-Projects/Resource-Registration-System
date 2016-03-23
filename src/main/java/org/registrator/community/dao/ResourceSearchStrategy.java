package org.registrator.community.dao;

import org.registrator.community.dto.json.ResourceSearchParameterJson;
import org.registrator.community.entity.Resource;

import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

/**
 * Adds joins to Criteria from Resource to Linear values or Discrete values.
 */
public interface ResourceSearchStrategy {

    void addCriteriaRestriction(List<ResourceSearchParameterJson> parameters, CriteriaQuery<Resource> selection);
}
