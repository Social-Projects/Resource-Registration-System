package org.registrator.community.dao;

import org.registrator.community.dto.json.ResourceSearchParameterJson;
import org.registrator.community.entity.Resource;
import org.registrator.community.entity.ResourceLinearValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Strategy to add Criteria joins and restrictions for searching resources by discrete parameters
 */
@Component("linearParameterSearchStrategy")
public class LinearParameterSearchStrategy implements ResourceSearchStrategy{
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private LinearParameterRepository linearParameterRepository;

    /**
     * Adds joins to Criteria from Resource to Linear values.
     * Restrictions for linear values are build by formula
     *     value > minValueParameter && value < maxValueParameter
     * where 'value' is data got from client
     *       'minValueParameter', 'maxValueParameter' corresponding data in ResourceLinearValue entity
     * @param parameters discrete parameters to form restriction
     * @param selection root selection of Resource.class
     */
    @Override
    @SuppressWarnings("unchecked")
    public void addCriteriaRestriction(List<ResourceSearchParameterJson> parameters,
                                       CriteriaQuery<Resource> selection) {


        Root<Resource> criteriaRoot = (Root<Resource>) selection.getRoots().iterator().next();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        for (ResourceSearchParameterJson parameter : parameters) {
            Join<Resource, ResourceLinearValue> join = criteriaRoot.join("resourceLinearValues");

            selection.where(
                    selection.getRestriction(),
                    criteriaBuilder.equal(join.get("linearParameter"),
                            linearParameterRepository.findByLinearParameterId(parameter.getId())),
                    criteriaBuilder.lessThan(join.get("minValue"), parameter.getValue()),
                    criteriaBuilder.greaterThan(join.get("maxValue"), parameter.getValue())
            );
        }

    }

}
