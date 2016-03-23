package org.registrator.community.dto.search;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TableSearchResponseDTO {
	
	/**
	 * The draw counter that this object is a response to - from the draw
	 * parameter sent as part of the data request. Note that it is strongly
	 * recommended for security reasons that you cast this parameter to an
	 * integer, rather than simply echoing back to the client what it sent in
	 * the draw parameter, in order to prevent Cross Site Scripting (XSS)
	 * attacks.
	 */
	private int draw;
	
	/**
	 * Total records, before filtering (i.e. the total number of records in the
	 * database)
	 */
	private long recordsTotal;
	
	/**
	 * Total records, after filtering (i.e. the total number of records after
	 * filtering has been applied - not just the number of records being
	 * returned for this page of data).
	 */
	private long recordsFiltered;
	
	/**
	 * The data to be displayed in the table. 
	 * This is an map of columns data as map key and value of this column,
	 *  which will be used by DataTables.
	 */
	private List<Map<String,String>> data = new ArrayList<Map<String,String>>();

	public int getDraw() {
		return draw;
	}

	public void setDraw(int draw) {
		this.draw = draw;
	}

	public long getRecordsTotal() {
		return recordsTotal;
	}

	public void setRecordsTotal(long recordsTotal) {
		this.recordsTotal = recordsTotal;
	}

	public void setRecordsTotal(int recordsTotal) {
		this.recordsTotal = recordsTotal;
	}

	public long getRecordsFiltered() {
		return recordsFiltered;
	}

	public void setRecordsFiltered(long recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}

	public void setRecordsFiltered(int recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}

	public List<Map<String, String>> getData() {
		return data;
	}

	public void setData(List<Map<String, String>> data) {
		this.data = data;
	}
}
