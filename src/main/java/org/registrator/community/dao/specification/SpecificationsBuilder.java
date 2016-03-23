package org.registrator.community.dao.specification;

import java.util.ArrayList;
import java.util.List;

import org.registrator.community.dto.search.SearchColumn;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;

public class SpecificationsBuilder<T> {
	
	private  List<SearchColumn> colums;
	
	public SpecificationsBuilder() {
		colums = new ArrayList<SearchColumn>();
    }
	
	public Specification<T> build() {
        if (colums==null && colums.size() == 0) {
            return null;
        }
 
        List<Specification<T>> specs = new ArrayList<Specification<T>>();
        for (SearchColumn scolumn : colums) {
        	if(scolumn.getSearch().getValue() != null && scolumn.getSearch().getValue()!="" ){
        		
//        		BaseSpecification<T> entitySpecification = new BaseSpecification<T>();
//        		entitySpecification.setCriteria(scolumn);
        		BaseSpecification<T> entitySpecification = new BaseSpecification<T>(
        				scolumn.getData(),scolumn.getSearch().getValue(),scolumn.getSearch().getCompareSign()
        				);
//        		entitySpecification.setCriteria(scolumn);
        		
                specs.add((Specification<T>) entitySpecification);
        	}	
        }
		
        Specification<T> result = specs.get(0);
        for (int i = 1; i < specs.size(); i++) {
            result = Specifications.where(result).and(specs.get(i));
        }
        return result;
    }

	public List<SearchColumn> getColums() {
		return colums;
	}

	public SpecificationsBuilder<T> setColums(List<SearchColumn> colums) {
		this.colums = colums;
		return this;
	}
	
}