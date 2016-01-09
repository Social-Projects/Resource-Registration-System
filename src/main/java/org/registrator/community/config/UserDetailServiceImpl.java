package org.registrator.community.config;

import java.util.ArrayList;
import java.util.List;

import org.registrator.community.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsService")
public class UserDetailServiceImpl implements UserDetailsService {
	@Autowired
	UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(final String email)
			throws UsernameNotFoundException {
		
		org.registrator.community.entity.User userEntity=userRepository.findUserByLogin(email);
		System.out.println(userEntity);
		
		
		if (userEntity == null){
			System.out.println("user not found");
			throw new UsernameNotFoundException("Помилка в паролі, чи емейлі");
			}

		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ROLE_"+userEntity.getRole().getType().name()));
		
	
		
		return buildUserForAuthentication(userEntity,authorities);
	}
	
	
	private User buildUserForAuthentication(org.registrator.community.entity.User userEntity, List<GrantedAuthority> authorities) {
		//return new User(userEntity.getFirstName()+"  "+userEntity.getLastName(), userEntity.getPassword(), true, true, true, true, authorities);
		return new User(userEntity.getLogin(), userEntity.getPassword(), authorities);		
	}
		
	
}
