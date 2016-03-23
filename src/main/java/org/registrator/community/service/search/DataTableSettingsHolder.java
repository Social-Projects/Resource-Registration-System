package org.registrator.community.service.search;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement(name="tableSearchSetting")
@XmlAccessorType(XmlAccessType.FIELD)
public class DataTableSettingsHolder {
	
	@XmlJavaTypeAdapter(DataTableSettingsAdapter.class)
	private Map<String,TableSetting> tables = new HashMap<String,TableSetting>();

	public Map<String, TableSetting> getTables() {
		return tables;
	}

	public void setTables(Map<String, TableSetting> tables) {
		this.tables = tables;
	}
	
	public void putTable(String tableName,TableSetting table){
		tables.put(tableName, table);
	}
}