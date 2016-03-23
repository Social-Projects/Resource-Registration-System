package org.registrator.community.dao;

import java.util.List;
import java.util.Set;

import org.registrator.community.entity.Resource;
import org.registrator.community.entity.ResourceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ResourceRepository extends JpaRepository<Resource, Long> {

    @Query("Select r" +
            " From Resource r " +
            "Where r.identifier = :identifier")
    Resource findByIdentifier(@Param("identifier") String identifier);

    List<Resource> findByType(ResourceType type);

    long count();
    
    @Query("SELECT r.description FROM Resource r WHERE r.description LIKE :searchTerm%")
    Set<String> findDescriptionsLikeProposed(@Param("searchTerm") String searchTerm);

    List<Resource> findAll();

}
