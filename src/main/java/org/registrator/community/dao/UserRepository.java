package org.registrator.community.dao;

import java.util.List;
import org.registrator.community.entity.TerritorialCommunity;
import org.registrator.community.entity.User;
import org.registrator.community.enumeration.RoleType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;



public interface UserRepository extends JpaRepository<User,Integer>, JpaSpecificationExecutor<User>{

    Page<User> findAll(Pageable pageable);

    @Query("select u from User u where u.login = :login")
    User findUserByLogin(@Param("login")String login);

    @Query("select u from User u where u.login = :login and u.password = :password")
    User getUserByLoginAndPassword(@Param("login") String loginName, @Param("password") String password);

    @Query("select u from User u where u.email = :email")
    List<User> getUsersByEmail(@Param("email") String email);

    @Query("select u.password from User u where u.login = :login")
    String getUsersPasswordHash(@Param("login") String password);
    
    @Query("SELECT u FROM User u WHERE u.territorialCommunity = :community and u.lastName LIKE :searchTerm%")
    List<User> findOwnersLikeProposed(@Param("community") TerritorialCommunity community, @Param("searchTerm") String searchTerm);
    
    @Query("SELECT u FROM User u WHERE u.login IN (:userList)")
    List<User> findUsersByLoginList(@Param("userList") List<String> userList);
    
    
//    @Query("select u from User u where u.role.type = :roleType") 
//    List<User> getUsersByRole(@Param("roleType")RoleType roleType);
    
    @Query("select u from User u" +
    		" where u.role.type = :roleType" +
    		" and u.territorialCommunity = :territorialCommunity") 
    List<User> getUsersByRoleAndCommunity(@Param("roleType")RoleType roleType,
    		@Param("territorialCommunity") TerritorialCommunity territorialCommunity);
    
    List<User> findByTerritorialCommunity(TerritorialCommunity community);
    
    
}
