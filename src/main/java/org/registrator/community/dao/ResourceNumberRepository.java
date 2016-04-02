package org.registrator.community.dao;

import java.util.List;

import org.registrator.community.entity.ResourceNumber;
import org.registrator.community.entity.TerritorialCommunity;
import org.registrator.community.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ResourceNumberRepository extends JpaRepository<ResourceNumber, String> {
    
    @Query("select r from ResourceNumber r where r.user =:user")
    public ResourceNumber findResourceNumberByUser(@Param("user") User user);
    
    @Query("select r from ResourceNumber as r left join r.user as u where u.territorialCommunity = :community")
    public List<ResourceNumber> findResourceNumbersByCommunity(@Param("community") TerritorialCommunity community);

}
