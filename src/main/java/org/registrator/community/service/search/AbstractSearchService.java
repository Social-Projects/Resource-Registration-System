package org.registrator.community.service.search;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.registrator.community.components.TableSettingsFactory;
import org.registrator.community.dao.specification.SpecificationsBuilder;
import org.registrator.community.dto.search.SearchColumn;
import org.registrator.community.dto.search.TableSearchRequestDTO;
import org.registrator.community.dto.search.TableSearchResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public abstract class AbstractSearchService<T,R extends JpaSpecificationExecutor<T> 
	& PagingAndSortingRepository<T,? extends Serializable>>  implements BaseSearchService<T>{
	
	@Autowired
	private TableSettingsFactory tableSettingsFactory;

	public TableSearchResponseDTO getTableSearchResponse(
			TableSearchRequestDTO searchRequest,R repository) {
		
		long count;
		List<T> entityList;
		TableSearchResponseDTO tableResponse;
		int draw=searchRequest.getDraw();
		TableSetting tableSetting = tableSettingsFactory.getTableSetting(searchRequest.getTableName());
		Pageable page = getPageRequest(searchRequest.getStart(), searchRequest.getLength(),searchRequest.getOrder().get(0).getColumn()
				,searchRequest.getOrder().get(0).getDir(),tableSetting);
		
		List<SearchColumn> columsList = tranformSearchColumnsNameToEntityColumns(searchRequest.getColumns(),tableSetting);
				
		if(checkForAllParametersSearch(columsList)){
			entityList = repository.findAll(page).getContent();
			count = repository.count();
		}
		else{
			SpecificationsBuilder<T> sb = new SpecificationsBuilder<T>();
			sb.setColums(columsList);
			Specification<T> userSpecification=sb.build();
			count = repository.count(userSpecification);
			entityList = repository.findAll(userSpecification,page).getContent();		
		}
			
		tableResponse = fillTableResponseDTO(draw,count,entityList);	
		return tableResponse;
	}

	private boolean checkForAllParametersSearch(List<SearchColumn> searchColumnList){
		for(SearchColumn searchColumn : searchColumnList){
			String searchValue=searchColumn.getSearch().getValue();
			if(searchValue!="" && searchValue!=null){
				return false;
			}
		}		
		return true;
	}
	
	private Pageable getPageRequest(int start,int length, String orderColumn, String orderType, TableSetting tableSetting){
		int pageNumber = convertStartToPageNumber(start, length);
		String entityColumn = tableSetting.getColumn(Integer.parseInt(orderColumn)).getData();
		Sort sort = getSort(entityColumn,orderType);
		return new PageRequest(pageNumber, length, sort);
	}
	
	private Sort getSort(String orderColumn, String orderType){
		if(orderType.equals("asc")){
			return new Sort(new Order(Direction.ASC, orderColumn));
		}
		return new Sort(new Order(Direction.DESC,orderColumn));
	}
	
	public abstract List<Map<String,String>> fillResponseDataList(List<T> searchEntityList);
	
	private TableSearchResponseDTO fillTableResponseDTO(int draw,long count,List<T> userList){
		
		TableSearchResponseDTO tableResponse = new TableSearchResponseDTO();
		List<Map<String,String>> personList=fillResponseDataList(userList);
		
		tableResponse.setDraw(draw);
		tableResponse.setRecordsTotal(count);
		tableResponse.setRecordsFiltered(count);
		tableResponse.setData(personList);
		
		return tableResponse;
	}
	
	private int convertStartToPageNumber(int start, int length){
		if(start!=0){
			return start/length;
		}
		else{
			return 0;
		}
	}
	
	private List<SearchColumn> tranformSearchColumnsNameToEntityColumns(List<SearchColumn> columsList,TableSetting tableSetting){
		for(SearchColumn column : columsList){
			if(column.getSearch().getValue() != null && column.getSearch().getValue()!="" ){
				String entityColumn = tableSetting.getColumn(Integer.parseInt(column.getData())).getData();
				column.setData(entityColumn);
			}
			
		}
		return columsList;
	}
}
