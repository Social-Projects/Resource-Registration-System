package org.registrator.community.service.search;

import java.util.List;
import java.util.Map;

import org.registrator.community.dto.search.TableSearchRequestDTO;
import org.registrator.community.dto.search.TableSearchResponseDTO;

public interface BaseSearchService<T>{

	public List<Map<String,String>> fillResponseDataList(List<T> searchedEntityList);
	
	public TableSearchResponseDTO executeSearchRequest(TableSearchRequestDTO searchRequest);
	
}
