package org.registrator.community.dao.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.registrator.community.dto.search.SearchColumn;
import org.registrator.community.entity.User;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecification<User> implements Specification<User>{
	
	private SearchColumn criteria;
	
	public SearchColumn getCriteria() {
		return criteria;
	}

	public void setCriteria(SearchColumn criteria) {
		this.criteria = criteria;
	}

	@Override
	public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query,
			CriteriaBuilder builder) {
		if (criteria.getData().equalsIgnoreCase("greater")) {
            return builder.greaterThanOrEqualTo(
              root.<String> get(criteria.getName()), criteria.getSearch().getValue());
        } 
        else if (criteria.getData().equalsIgnoreCase("less")) {
            return builder.lessThanOrEqualTo(
              root.<String> get(criteria.getName()), criteria.getSearch().getValue());
        } 
        else if (criteria.getData().equalsIgnoreCase(":")) {
            if (root.get(criteria.getName()).getJavaType() == String.class) {
                return builder.like(
                  root.<String>get(criteria.getName()), "%" + criteria.getSearch().getValue() + "%");
            } else {
                return builder.equal(root.get(criteria.getName()), criteria.getSearch().getValue());
            }
        }
        else if (criteria.getData().equalsIgnoreCase("equal")){
        	return builder.equal(root.get(criteria.getName()), criteria.getSearch().getValue());
        }
        return null;
	}

}
