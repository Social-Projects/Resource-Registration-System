package org.registrator.community.dto.search;

import java.util.List;

public class TableSearchRequestDTO {
	
	private int draw;
    
    private int start;
    
    private int length;
    
    private Search search;
    
    private List<SearchColumn> columns;

	public int getDraw() {
		return draw;
	}

	public void setDraw(int draw) {
		this.draw = draw;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public List<SearchColumn> getColumns() {
		return columns;
	}

	public void setColumns(List<SearchColumn> columns) {
		this.columns = columns;
	}

	public Search getSearch() {
		return search;
	}

	public void setSearch(Search search) {
		this.search = search;
	}
}
