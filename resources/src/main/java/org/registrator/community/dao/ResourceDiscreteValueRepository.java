package org.registrator.community.dao;

import java.util.List;

import org.registrator.community.entity.Resource;
import org.registrator.community.entity.ResourceDiscreteValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ResourceDiscreteValueRepository extends JpaRepository<ResourceDiscreteValue, Integer>{
	
	@Query("Select dv"+
			" From ResourceDiscreteValue dv" +
			" Where dv.resource = :resource" )
	List<ResourceDiscreteValue> findByResource(@Param("resource")Resource resource);

}
