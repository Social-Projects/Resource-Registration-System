package org.registrator.community.dao;

import org.registrator.community.entity.DiscreteParameter;
import org.registrator.community.entity.ResourceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DiscreteParameterRepository extends JpaRepository<DiscreteParameter, Integer>{

	@Query("Select d"+
			" From DiscreteParameter d" +
			" Where d.description = :description and d.resourceType = :resourceType" )
	public DiscreteParameter findByResourceAndName(@Param("description")String description,
			@Param("resourceType")ResourceType resourceType);

	List<DiscreteParameter> findAllByResourceType(ResourceType resourceType);

    DiscreteParameter findByDiscreteParameterId (Integer id);
}
