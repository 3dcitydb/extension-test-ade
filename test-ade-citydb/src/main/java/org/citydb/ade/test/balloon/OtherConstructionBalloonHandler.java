package org.citydb.ade.test.balloon;

import org.citydb.ade.kmlExporter.ADEBalloonHandler;

public class OtherConstructionBalloonHandler implements ADEBalloonHandler {

	@Override
	public String getSqlStatement(String table,
	                              String tableShortId,
	                              String aggregateColumnsClause,
	                              int lod,
	                              String schemaName) {

		String sqlStatement = null;

		if ("test_otherconstruction".equalsIgnoreCase(table)) {
			sqlStatement = "SELECT " + aggregateColumnsClause +
					" FROM " + schemaName + "." + table + " " + tableShortId +
					" WHERE " + tableShortId + ".id = ?";
		} else if ("test_other_to_thema_surfa".equalsIgnoreCase(table)) {
			sqlStatement = "SELECT " + aggregateColumnsClause +
					" FROM " + schemaName + "." + table + " " + tableShortId +
					" WHERE " + tableShortId + ".otherconstruction_id = ?";
		} else if ("thematic_surface".equalsIgnoreCase(table) || "test_industrialbuildingro".equalsIgnoreCase(table)) {
			sqlStatement = "SELECT " + aggregateColumnsClause +
					" FROM " + schemaName + "." + table + " " + tableShortId + ", " + schemaName + ".test_otherconstruction toc, " + schemaName + ".test_other_to_thema_surfa  to2ts" +
					" WHERE toc.id = ?" +
					" AND toc.id = to2ts.otherconstruction_id" +
					" AND to2ts.thematic_surface_id=" + tableShortId + ".id";
		} else if ("opening".equalsIgnoreCase(table)) {
			sqlStatement = "SELECT " + aggregateColumnsClause +
					" FROM " + schemaName + "." + table + " " + tableShortId + ", " + schemaName + ".opening_to_them_surface o2tsc, " + schemaName + ".test_other_to_thema_surfa  to2ts" +
					" WHERE to2ts.otherconstruction_id = ?" +
					" AND to2ts.thematic_surface_id = o2tsc.thematic_surface_id" +
					" AND o2tsc.opening_id = " + tableShortId + ".id";
		}
		else if ("opening_to_them_surface".equalsIgnoreCase(table)) {
			sqlStatement = "SELECT " + aggregateColumnsClause +
					" FROM " + schemaName + "." + table + " " + tableShortId + ", " + schemaName + ".test_other_to_thema_surfa  to2ts" +
					" WHERE to2ts.otherconstruction_id = ?" +
					" AND to2ts.thematic_surface_id = " + tableShortId + ".thematic_surface_id";
		}

		return sqlStatement;
	}

}
