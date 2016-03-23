package org.registrator.community.dao;

import org.registrator.community.dto.json.ResourceSearchJson;
import org.registrator.community.entity.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.List;

/**
 * Implements search of resources by parameters. Adds additional behavior to ResourceRepository
 */
@Repository
@Transactional(readOnly = true)
public class ResourceFindByParamsImpl implements ResourceFindByParams, Serializable {
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ResourceTypeRepository resourceTypeRepository;

    @Autowired
    @Qualifier("linearParameterSearchStrategy")
    private ResourceSearchStrategy linearSearch;

    @Autowired
    @Qualifier("discreteParameterSearchStrategy")
    private ResourceSearchStrategy discreteSearch;

    /**
     * Default result size returned from a server
     */
    private static final int PAGE_SIZE = 200;

    /**
     * Search of resources by parameters. All parameters comparisons are combined with AND logical operation
     * @param parameters resource parameters and values for searching
     * @return List of resources
     */
    public List<Resource> findByParams(ResourceSearchJson parameters) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Resource> criteria = criteriaBuilder.createQuery(Resource.class);
        Root<Resource> criteriaRoot = criteria.from(Resource.class);
        CriteriaQuery<Resource> selection = criteria.select(criteriaRoot).distinct(true);

        // restriction on typeId
        selection.where(
                criteriaBuilder.equal(
                        criteriaRoot.get("type"),
                        resourceTypeRepository.getOne(parameters.getResourceTypeId())
                )
        );

        linearSearch.addCriteriaRestriction(parameters.getLinearParameters(), selection);
        discreteSearch.addCriteriaRestriction(parameters.getDiscreteParameters(), selection);

        int pageOffset = parameters.getPage() * PAGE_SIZE;

        TypedQuery<Resource> query = entityManager.createQuery(selection);
        query.setFirstResult(pageOffset);
        query.setMaxResults(PAGE_SIZE);

        return query.getResultList();
    }

}
