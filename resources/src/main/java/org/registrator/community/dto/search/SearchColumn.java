package org.registrator.community.dto.search;

public class SearchColumn {
	
	private String data;
	
	private String name;
	
	private Boolean orderable;
	
	private Boolean searchable;
	
	private Search search;

	public Search getSearch() {
		return search;
	}

	public void setSearch(Search search) {
		this.search = search;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getOrderable() {
		return orderable;
	}

	public void setOrderable(Boolean orderable) {
		this.orderable = orderable;
	}

	public Boolean getSearchable() {
		return searchable;
	}

	public void setSearchable(Boolean searchable) {
		this.searchable = searchable;
	}
}
