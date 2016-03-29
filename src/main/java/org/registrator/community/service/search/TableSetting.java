package org.registrator.community.service.search;

import java.util.Map;
import java.util.TreeMap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement(name="table")
@XmlAccessorType(XmlAccessType.FIELD)
public class TableSetting {
	
	@XmlAttribute(required=true)
	private String tableName;
	
	@XmlElement(required=true)
	private String url;
	
	@XmlElement(required=false)
	private String script;
	
	@XmlElement(required=false)
	private String tableTitle;
	
	@XmlJavaTypeAdapter(TableColumnSettingsAdapter.class)
	private Map<Integer,TableColumnSetting> columns = new TreeMap<Integer,TableColumnSetting>();

	public Map<Integer, TableColumnSetting> getColumns() {
		return columns;
	}
	
	public TableColumnSetting getColumn(int key) {
		return columns.get(key);
	}

	public void setColumns(Map<Integer, TableColumnSetting> columns) {
		this.columns = columns;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTableName() {
		return tableName;
	}

	public String getScript() {
		return script;
	}

	public void setScript(String script) {
		this.script = script;
	}

	public String getTableTitle() {
		return tableTitle;
	}

	public void setTableTitle(String tableTitle) {
		this.tableTitle = tableTitle;
	}
}