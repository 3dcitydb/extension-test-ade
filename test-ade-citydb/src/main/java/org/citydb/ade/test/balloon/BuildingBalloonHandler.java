package org.citydb.ade.test.balloon;

import org.citydb.ade.kmlExporter.ADEBalloonHandler;

public class BuildingBalloonHandler implements ADEBalloonHandler {

	@Override
	public String getSqlStatement(String table,
	                              String tableShortId,
	                              String aggregateColumnsClause,
	                              int lod,
	                              String schemaName) {

		String sqlStatement = null;

		if ("test_buildingunit".equalsIgnoreCase(table)) {
			sqlStatement = "SELECT " + aggregateColumnsClause +
					" FROM " + schemaName + "." + table + " " + tableShortId +
					" WHERE " + tableShortId + ".building_buildingunit_id = ?";
		} else if ("test_buildingu_to_address".equalsIgnoreCase(table)) {
			sqlStatement = "SELECT " + aggregateColumnsClause +
					" FROM " + schemaName + "." + table + " " + tableShortId + ", " + schemaName + ".test_buildingunit tbu" +
					" WHERE tbu.building_buildingunit_id = ?" +
					" AND " + tableShortId + ".buildingunit_id = tbu.id";
		} else if ("test_energyperformancecer".equalsIgnoreCase(table)) {
			sqlStatement = "SELECT " + aggregateColumnsClause +
					" FROM " + schemaName + "." + table + " " + tableShortId + ", " + schemaName + ".test_buildingunit tbu" +
					" WHERE tbu.building_buildingunit_id = ?" +
					" AND " + tableShortId + ".buildingunit_energyperfor_id = tbu.id";
		} else if ("test_facilities".equalsIgnoreCase(table)) {
			sqlStatement = "SELECT " + aggregateColumnsClause +
					" FROM " + schemaName + "." + table + " " + tableShortId + ", " + schemaName + ".test_buildingunit tbu" +
					" WHERE tbu.building_buildingunit_id = ?" +
					" AND " + tableShortId + ".buildingunit_equippedwith_id = tbu.id";
		} else if ("test_industrialbuildingro".equalsIgnoreCase(table)) {
			sqlStatement = "SELECT " + aggregateColumnsClause +
					" FROM " + schemaName + "." + table + " " + tableShortId + ", " + schemaName + ".thematic_surface ts" +
					" WHERE ts.building_id = ?" +
					" AND ts.id = " + tableShortId + ".id";
		} else if ("test_industrialbuildingpa".equalsIgnoreCase(table)) {
			sqlStatement = "SELECT " + aggregateColumnsClause +
					" FROM " + schemaName + "." + table + " " + tableShortId + ", " + schemaName + ".building b" +
					" WHERE " + tableShortId + ".id = b.id" +
					" AND b.building_root_id = ?";
		}

		return sqlStatement;
	}

}
