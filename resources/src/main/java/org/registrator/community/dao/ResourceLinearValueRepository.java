package org.registrator.community.dao;

import java.util.List;

import org.registrator.community.entity.Resource;
import org.registrator.community.entity.ResourceLinearValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ResourceLinearValueRepository extends JpaRepository<ResourceLinearValue, Integer>{
	
	@Query("Select lv"+
			" From ResourceLinearValue lv" +
			" Where lv.resource = :resource" )
	List<ResourceLinearValue> findByResource(@Param("resource")Resource resource);

}
