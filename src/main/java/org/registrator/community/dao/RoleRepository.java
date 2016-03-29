package org.registrator.community.dao;

import org.registrator.community.entity.Role;
import org.registrator.community.enumeration.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoleRepository extends JpaRepository<Role, String> {
	@Query("Select r From Role r where r.type = :type")
	public Role findRoleByType(@Param("type") RoleType type);
}
