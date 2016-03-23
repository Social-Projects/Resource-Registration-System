package org.registrator.community.dao;

import org.registrator.community.dto.json.ResourceSearchParameterJson;
import org.registrator.community.entity.Resource;
import org.registrator.community.entity.ResourceDiscreteValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.List;
import java.util.Set;

/**
 * Strategy to add Criteria joins and restrictions for searching resources by discrete parameters
 */
@Component("discreteParameterSearchStrategy")
public class DiscreteParameterSearchStrategy implements ResourceSearchStrategy {
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private DiscreteParameterRepository discreteParameterRepository;

    /**
     * Adds joins to Criteria from Resource to Discrete values by compare operation
     * @param parameters discrete parameters to form restriction
     * @param selection root selection of Resource.class
     * @throws UnsupportedOperationException if the specified operation in 'parameters' is not supported
     *
     */
    @Override
    @SuppressWarnings("unchecked")
    public void addCriteriaRestriction(List<ResourceSearchParameterJson> parameters,
                                       CriteriaQuery<Resource> selection) {

        Root<Resource> criteriaRoot = (Root<Resource>) selection.getRoots().iterator().next();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        for (ResourceSearchParameterJson parameter : parameters) {
            Join<Resource, ResourceDiscreteValue> join = criteriaRoot.join("resourceDiscreteValues");

            Predicate restriction;
            Path<Double> path = join.get("value");
            switch (parameter.getCompare()) {
                case LESS:
                    restriction = criteriaBuilder.lessThan(path, parameter.getValue());
                    break;
                case GREATER:
                    restriction = criteriaBuilder.greaterThan(path, parameter.getValue());
                    break;
                case EQUAL:
                    restriction = criteriaBuilder.equal(path, parameter.getValue());
                    break;
                default:
                    throw new UnsupportedOperationException("Compare operation" + parameter.getCompare() +
                            "not supported for discrete values");
            }


            selection.where(
                    selection.getRestriction(),
                    criteriaBuilder.equal(join.get("discreteParameter"),
                            discreteParameterRepository.findByDiscreteParameterId(parameter.getId())),
                    restriction
            );
        }
    }
}
