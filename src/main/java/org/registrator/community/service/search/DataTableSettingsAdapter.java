package org.registrator.community.service.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;



public class DataTableSettingsAdapter extends XmlAdapter<DataTableSettingsAdapter.TableSettings,Map<String,TableSetting>>  {

	@Override
	public Map<String, TableSetting> unmarshal(TableSettings tableSettings)
			throws Exception {
		Map<String, TableSetting> tables = new HashMap<String, TableSetting>();
        for(TableSetting tableSetting : tableSettings.tables) {
        	tables.put(tableSetting.getTableName(), tableSetting);
        }
		return tables;
	}

	@Override
	public TableSettings marshal(Map<String, TableSetting> tables) throws Exception {
		TableSettings tableSettings = new TableSettings();
		for(Entry<String,TableSetting> entry : tables.entrySet()){
			tableSettings.tables.add(entry.getValue());
		}
		return tableSettings;
	}
	
	static class TableSettings {
		@XmlElement(name="table")
		public List<TableSetting> tables = new ArrayList<TableSetting>();
	}

}
