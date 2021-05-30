package org.citydb.ade.test.schema;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;

public class ADETableMapper {
	private HashMap<String, Set<String>> tableColumns;

	public void populateTableColumns(SchemaMapper schemaMapper) {
		tableColumns = new HashMap<String, Set<String>>() {{
			put(schemaMapper.getTableName(ADETable.BUILDING).toUpperCase(), new LinkedHashSet<String>() {{
				add("ID");
				add("ENERGYPERFORM_CERTIFICATIO_1");
				add("ENERGYPERFORMA_CERTIFICATION");
				add("FLOORAREA");
				add("FLOORAREA_UOM");
				add("OWNERNAME");
			}});
			put(schemaMapper.getTableName(ADETable.INDUSTRIALBUILDING).toUpperCase(), new LinkedHashSet<String>() {{
				add("ID");
				add("REMARK");
			}});
			put(schemaMapper.getTableName(ADETable.OTHER_TO_THEMA_SURFA).toUpperCase(), new LinkedHashSet<String>() {{
				add("OTHERCONSTRUCTION_ID");
				add("THEMATIC_SURFACE_ID");
			}});
			put(schemaMapper.getTableName(ADETable.OTHERCONSTRUCTION).toUpperCase(), new LinkedHashSet<String>() {{
				add("ID");
				add("LOD2MULTICURVE");
				add("LOD2SOLID_ID");
			}});
		}};
	}

	public HashMap<String, Set<String>> getTablesAndColumns() {
		return tableColumns;
	}

}
