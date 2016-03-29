package org.registrator.community.dto.search;

import javax.validation.constraints.NotNull;

public class Search {

	/**
	 * Search value. To be applied to all individual columns which have searchable
	 * as true.
	 */
	@NotNull
	private String value;
	
	/**
	 * true if the global filter should be treated as a regular expression for
	 * advanced searching, false otherwise. Note that normally server-side
	 * processing scripts will not perform regular expression searching for
	 * performance reasons on large data sets, but it is technically possible
	 * and at the discretion of your script.
	 */
	@NotNull
	private Boolean regex;
	
	/**
	 * Sign that show which type of compare we need to use. To be applied to all individual columns which have searchable
	 * as true.
	 */
	@NotNull
	private String compareSign;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Boolean getRegex() {
		return regex;
	}

	public void setRegex(Boolean regex) {
		this.regex = regex;
	}

	public String getCompareSign() {
		return compareSign;
	}

	public void setCompareSign(String compareSign) {
		this.compareSign = compareSign;
	}

}