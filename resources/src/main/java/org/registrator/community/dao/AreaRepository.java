package org.registrator.community.dao;

import java.util.List;

import org.registrator.community.entity.Area;
import org.registrator.community.entity.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AreaRepository extends JpaRepository<Area, Integer>{
	
	@Query("Select a"+
			" From Area a" +
			" Where a.resource = :resource" )
	List<Area> findByResource(@Param("resource")Resource resource);

}
