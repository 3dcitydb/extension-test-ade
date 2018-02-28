package org.citydb.ade.test.schema;

import java.util.EnumMap;
import java.util.Map.Entry;

public class SchemaMapper {	
	private EnumMap<ADETables, String> tableNames = new EnumMap<>(ADETables.class);
	private EnumMap<ADESequences, String> sequenceNames = new EnumMap<>(ADESequences.class);

	public void populateSchemaNames(String prefix) {
		for (ADETables table : ADETables.values())
			tableNames.put(table, new StringBuilder(prefix).append("_").append(table.toString().toLowerCase()).toString());

		for (ADESequences sequence : ADESequences.values())
			sequenceNames.put(sequence, new StringBuilder(prefix).append("_").append(sequence.toString().toLowerCase()).toString());	
	}

	public String getTableName(ADETables table) {
		return tableNames.get(table);
	}

	public ADETables fromTableName(String tableName) {
		tableName = tableName.toLowerCase();
		
		for (Entry<ADETables, String> entry : tableNames.entrySet()) {
			if (entry.getValue().equals(tableName))
				return entry.getKey();
		}

		return null;
	}

	public String getSequenceName(ADESequences sequence) {
		return sequenceNames.get(sequence);
	}

}
