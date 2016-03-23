package org.registrator.community.dao;
import java.util.List;

import org.registrator.community.entity.LinearParameter;
import org.registrator.community.entity.ResourceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LinearParameterRepository extends JpaRepository<LinearParameter, Integer>{
	
	@Query("Select l"+
			" From LinearParameter l" +
			" Where l.description = :description and l.resourceType = :resourceType" )
	public LinearParameter findByResourceAndName(@Param("description")String description,
			@Param("resourceType")ResourceType resourceType);
	
	@Query("Select l"+
			" From LinearParameter l" +
			" Where l.resourceType = :resourceType" )
	List<LinearParameter> findByResourceType(@Param("resourceType")ResourceType resourceType);
	
}
