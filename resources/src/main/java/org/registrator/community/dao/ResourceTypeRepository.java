package org.registrator.community.dao;

import java.util.List;
import org.registrator.community.entity.ResourceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ResourceTypeRepository extends JpaRepository<ResourceType, Integer>{


    @Query("select rt FROM ResourceType rt where rt.typeName = :typeName")
    ResourceType findByName(@Param("typeName") String typeName);
    
   @Query("select rt from ResourceType rt")
    List<ResourceType> getAll();

    
  
  
}
