package org.registrator.community.dao.specification;

import java.util.ArrayList;
import java.util.List;

import org.registrator.community.dto.search.SearchColumn;
import org.registrator.community.entity.User;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;

public class SpecificationsBuilder {
	
	private  List<SearchColumn> colums;
	
	public SpecificationsBuilder() {
		colums = new ArrayList<SearchColumn>();
    }
	
	public Specification<User> build() {
        if (colums==null && colums.size() == 0) {
            return null;
        }
 
        List<Specification<User>> specs = new ArrayList<Specification<User>>();
        for (SearchColumn scolumn : colums) {
        	UserSpecification userSpecification =new UserSpecification();
        	userSpecification.setCriteria(scolumn);
            specs.add(userSpecification);
        }
 
        Specification<User> result = specs.get(0);
        for (int i = 1; i < specs.size(); i++) {
            result = Specifications.where(result).and(specs.get(i));
        }
        return result;
    }

	public List<SearchColumn> getColums() {
		return colums;
	}

	public SpecificationsBuilder setColums(List<SearchColumn> colums) {
		this.colums = colums;
		return this;
	}
	
	

}
