package org.citydb.ade.test.balloon;

import org.citydb.modules.kml.ade.ADEBalloonHandler;

public class IndustrialBuildingBalloonHandler implements ADEBalloonHandler {

	@Override
	public String getSqlStatement(String table,
	                              String tableShortId,
	                              String aggregateColumnsClause,
	                              int lod,
	                              String schemaName) {

		String sqlStatement = null;

		if ("test_industrialbuilding".equalsIgnoreCase(table)) {
			sqlStatement = "SELECT " + aggregateColumnsClause +
					" FROM " + schemaName + "." + table + " " + tableShortId +
					" WHERE " + tableShortId + ".id = ?";
		}

		return sqlStatement;
	}

}
