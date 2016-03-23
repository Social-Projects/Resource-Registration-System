package org.registrator.community.service.search.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.registrator.community.dao.UserRepository;
import org.registrator.community.dto.search.TableSearchRequestDTO;
import org.registrator.community.dto.search.TableSearchResponseDTO;
import org.registrator.community.entity.User;
import org.registrator.community.service.search.AbstractSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userSearchService")
public class UserSearchServiceImpl extends AbstractSearchService<User,UserRepository> {
	
	@Autowired
	private UserRepository userRepository;


	@Override
	public List<Map<String, String>> fillResponseDataList(
			List<User> searchEntityList) {
		List<Map<String,String>> personList = new ArrayList<Map<String,String>>();
		for(User user : searchEntityList){
			Map<String,String> map = new HashMap<String,String>();
			map.put("firstName", user.getFirstName());
			map.put("lastName", user.getLastName());
			map.put("middleName", user.getMiddleName());
			map.put("email", user.getEmail());
			map.put("address_city", user.getAddress().get(0).getCity());
			personList.add(map);	
		}
		return personList;
	}

	@Override
	public TableSearchResponseDTO executeSearchRequest(
			TableSearchRequestDTO searchRequest) {
		return getTableSearchResponse(searchRequest, userRepository);
	}
}