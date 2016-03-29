package org.registrator.community.service.search;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class TableColumnSetting {
	
	@XmlElement(required=false,defaultValue="search")
	private String type;
	
	@XmlElement(required=false)
	private String title;
	
	@XmlElement(required=false)
	private String data;
	
	@XmlElement(required=false)
	private String buttonId;
	
	@XmlElementWrapper(name = "searchOperation")
	@XmlElement(name="operationType")
	private List<String> searchType;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<String> getSearchType() {
		return searchType;
	}

	public void setSearchType(List<String> searchType) {
		this.searchType = searchType;
	}

	public String getButtonId() {
		return buttonId;
	}

	public void setButtonId(String buttonId) {
		this.buttonId = buttonId;
	}
}