package org.registrator.community.components;

import java.util.Map;

import org.registrator.community.service.search.DataTableSettingsHolder;
import org.registrator.community.service.search.TableSetting;
import org.registrator.community.util.XmlAnalyzerUtil;
import org.springframework.stereotype.Component;

@Component
public class TableSettingsFactory {

    private final String TABLE_SETTINGS_FILE_NAME="/searchTable.xml";

    private Map<String,TableSetting> tableSettingsMap;

    public TableSettingsFactory() {
        init();
    }

    private void init(){
        DataTableSettingsHolder tablesHolder = new XmlAnalyzerUtil().unmarshal(DataTableSettingsHolder.class, TABLE_SETTINGS_FILE_NAME);
        if(tablesHolder != null){
            tableSettingsMap =  tablesHolder.getTables();
        }

    }

    public TableSetting getTableSetting(String tableName){
        if(tableSettingsMap==null){
            init();
        }
        return tableSettingsMap.get(tableName);
    }
}