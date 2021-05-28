package org.citydb.ade.test.schema;

import java.util.EnumMap;
import java.util.Map.Entry;

public class SchemaMapper {	
	private final EnumMap<ADETable, String> tableNames = new EnumMap<>(ADETable.class);

	public void populateSchemaNames(String prefix) {
		for (ADETable table : ADETable.values())
			tableNames.put(table, prefix + "_" + table.toString().toLowerCase());
	}

	public String getTableName(ADETable table) {
		return tableNames.get(table);
	}

	public ADETable fromTableName(String tableName) {
		tableName = tableName.toLowerCase();
		
		for (Entry<ADETable, String> entry : tableNames.entrySet()) {
			if (entry.getValue().equals(tableName))
				return entry.getKey();
		}

		return null;
	}
}
