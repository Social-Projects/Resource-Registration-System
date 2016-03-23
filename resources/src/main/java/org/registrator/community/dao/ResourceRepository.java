package org.registrator.community.dao;

import java.util.List;

import org.registrator.community.entity.Resource;
import org.registrator.community.entity.ResourceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ResourceRepository extends JpaRepository<Resource, Long> {

    @Query("Select r" +
            " From Resource r " +
            "Where r.identifier = :identifier")
    public Resource findResourceByIdentifier(@Param("identifier") String identifier);

    List<Resource> findByType(ResourceType type);

    long count();

}
