package org.registrator.community.dto.search;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

public class SearchColumn {
	
	/**
	 * Column's data source
	 * 
	 * @see http://datatables.net/reference/option/columns.data
	 */
	@NotBlank
	@NotNull
	private String data;
	
	/**
	 * Column's name
	 * 
	 * @see http://datatables.net/reference/option/columns.name
	 */
	private String name;
	
	/**
	 * Flag to indicate if this column is orderable (true) or not (false).
	 * 
	 * @see http://datatables.net/reference/option/columns.orderable
	 */
	@NotNull
	private Boolean orderable;
	
	/**
	 * Flag to indicate if this column is searchable (true) or not (false).
	 * 
	 * @see http://datatables.net/reference/option/columns.searchable
	 */
	@NotNull
	private Boolean searchable;
	
	/**
	 * Search value to apply to this specific column.
	 */
	@NotNull
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