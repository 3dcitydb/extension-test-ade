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
			put(schemaMapper.getTableName(ADETable.BUILDINGU_TO_ADDRESS).toUpperCase(), new LinkedHashSet<String>() {{
				add("ADDRESS_ID");
				add("BUILDINGUNIT_ID");
			}});
			put(schemaMapper.getTableName(ADETable.BUILDINGUNIT).toUpperCase(), new LinkedHashSet<String>() {{
				add("ID");
				add("OBJECTCLASS_ID");
				add("BUILDING_BUILDINGUNIT_ID");
				add("BUILDINGUNIT_PARENT_ID");
				add("BUILDINGUNIT_ROOT_ID");
				add("CLASS");
				add("CLASS_CODESPACE");
				add("FUNCTION");
				add("FUNCTION_CODESPACE");
				add("USAGE");
				add("USAGE_CODESPACE");
				add("LOD1MULTISURFACE_ID");
				add("LOD1SOLID_ID");
				add("LOD2MULTICURVE");
				add("LOD2MULTISURFACE_ID");
				add("LOD2SOLID_ID");
				add("LOD3MULTICURVE");
				add("LOD3MULTISURFACE_ID");
				add("LOD3SOLID_ID");
				add("LOD4MULTICURVE");
				add("LOD4MULTISURFACE_ID");
				add("LOD4SOLID_ID");
			}});
			put(schemaMapper.getTableName(ADETable.ENERGYPERFORMANCECER).toUpperCase(), new LinkedHashSet<String>() {{
				add("ID");
				add("BUILDINGUNIT_ENERGYPERFOR_ID");
				add("CERTIFICATIONID");
				add("CERTIFICATIONNAME");
			}});
			put(schemaMapper.getTableName(ADETable.FACILITIES).toUpperCase(), new LinkedHashSet<String>() {{
				add("ID");
				add("BUILDINGUNIT_EQUIPPEDWITH_ID");
				add("OBJECTCLASS_ID");
				add("TOTALVALUE");
				add("TOTALVALUE_UOM");
			}});
			put(schemaMapper.getTableName(ADETable.INDUSTRIALBUILDING).toUpperCase(), new LinkedHashSet<String>() {{
				add("ID");
				add("REMARK");
			}});
			put(schemaMapper.getTableName(ADETable.INDUSTRIALBUILDINGPA).toUpperCase(), new LinkedHashSet<String>() {{
				add("ID");
				add("REMARK");
			}});
			put(schemaMapper.getTableName(ADETable.INDUSTRIALBUILDINGRO).toUpperCase(), new LinkedHashSet<String>() {{
				add("ID");
				add("REMARK");
			}});
			put(schemaMapper.getTableName(ADETable.OTHER_TO_THEMA_SURFA).toUpperCase(), new LinkedHashSet<String>() {{
				add("OTHERCONSTRUCTION_ID");
				add("THEMATIC_SURFACE_ID");
			}});
			put(schemaMapper.getTableName(ADETable.OTHERCONSTRUCTION).toUpperCase(), new LinkedHashSet<String>() {{
				add("ID");
			}});
		}};
	}

	public HashMap<String, Set<String>> getTablesAndColumns() {
		return tableColumns;
	}

}
