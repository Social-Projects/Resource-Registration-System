package org.registrator.community.dao;
import java.util.List;

import org.registrator.community.entity.TerritorialCommunity;
import org.registrator.community.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommunityRepository extends JpaRepository<TerritorialCommunity, Integer>{

    @Query("select tc FROM TerritorialCommunity tc where tc.name = :name ORDER BY tc.name asc")
    TerritorialCommunity findByName(@Param("name") String name);
    
    @Query("select tc from TerritorialCommunity tc ORDER BY tc.name asc")
    List<TerritorialCommunity> findAllByAsc();
    
}
