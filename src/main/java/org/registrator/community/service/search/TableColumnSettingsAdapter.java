package org.registrator.community.service.search;

import java.util.ArrayList;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;

public class TableColumnSettingsAdapter extends XmlAdapter<TableColumnSettingsAdapter.TableColumnSettingHolder,Map<Integer,TableColumnSetting>>{
	
	@Override
	public Map<Integer, TableColumnSetting> unmarshal(TableColumnSettingHolder tableColumnSetting)
			throws Exception {
		
		Map<Integer, TableColumnSetting> tables = new TreeMap<Integer, TableColumnSetting>();
		for(int tableColumnIndex = 0; tableColumnIndex < tableColumnSetting.columns.size(); tableColumnIndex++){
			tables.put(tableColumnIndex, tableColumnSetting.columns.get(tableColumnIndex));
		}
		return tables;
	}

	@Override
	public TableColumnSettingHolder marshal(Map<Integer, TableColumnSetting> table)
			throws Exception {
		
		TableColumnSettingHolder tableColumnSetting = new TableColumnSettingHolder();
		for(Entry<Integer,TableColumnSetting> entry : table.entrySet()){
			tableColumnSetting.columns.add(entry.getValue());
		}
		return tableColumnSetting;
	}
	
	static class TableColumnSettingHolder {
		@XmlElement(name="column")
		public List<TableColumnSetting> columns = new ArrayList<TableColumnSetting>();
	}
}
