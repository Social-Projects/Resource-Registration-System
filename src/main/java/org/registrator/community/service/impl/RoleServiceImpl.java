package org.registrator.community.service.impl;

import java.util.List;

import org.registrator.community.dao.RoleRepository;
import org.registrator.community.entity.Role;
import org.registrator.community.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoleServiceImpl implements RoleService{

	@Autowired
	RoleRepository roleRepository;
	
	@Transactional
	@Override
	public List<Role> getAllRole() {
		return roleRepository.findAll();
	}

}
