package org.citydb.ade.test.balloon;

import org.citydb.ade.kmlExporter.ADEBalloonHandler;
import org.citydb.ade.test.schema.ADETable;
import org.citydb.ade.test.schema.SchemaMapper;
import org.citydb.database.schema.TableEnum;

public class OtherConstructionBalloonHandler implements ADEBalloonHandler {
	private final SchemaMapper schemaMapper;

	public OtherConstructionBalloonHandler(BalloonManager manager) {
		this.schemaMapper = manager.getSchemaMapper();
	}

	@Override
	public String getSqlStatement(String table,
	                              String tableShortId,
	                              String aggregateColumnsClause,
	                              int lod,
	                              String schemaName) {

		String sqlStatement = null;

		if (schemaMapper.getTableName(ADETable.OTHERCONSTRUCTION).equalsIgnoreCase(table)) {
			sqlStatement = "SELECT " + aggregateColumnsClause +
					" FROM " + schemaName + "." + table + " " + tableShortId +
					" WHERE " + tableShortId + ".id = ?";
		} else if (schemaMapper.getTableName(ADETable.OTHER_TO_THEMA_SURFA).equalsIgnoreCase(table)) {
			sqlStatement = "SELECT " + aggregateColumnsClause +
					" FROM " + schemaName + "." + table + " " + tableShortId +
					" WHERE " + tableShortId + ".otherconstruction_id = ?";
		} else if (TableEnum.THEMATIC_SURFACE.getName().equalsIgnoreCase(table)) {
			sqlStatement = "SELECT " + aggregateColumnsClause +
					" FROM " + schemaName + "." + table + " " + tableShortId + ", " + schemaName + ".test_otherconstruction toc, " + schemaName + ".test_other_to_thema_surfa  to2ts" +
					" WHERE toc.id = ?" +
					" AND toc.id = to2ts.otherconstruction_id" +
					" AND to2ts.thematic_surface_id=" + tableShortId + ".id";
		} else if (TableEnum.OPENING.getName().equalsIgnoreCase(table)) {
			sqlStatement = "SELECT " + aggregateColumnsClause +
					" FROM " + schemaName + "." + table + " " + tableShortId + ", " + schemaName + ".opening_to_them_surface o2tsc, " + schemaName + ".test_other_to_thema_surfa  to2ts" +
					" WHERE to2ts.otherconstruction_id = ?" +
					" AND to2ts.thematic_surface_id = o2tsc.thematic_surface_id" +
					" AND o2tsc.opening_id = " + tableShortId + ".id";
		}
		else if (TableEnum.OPENING_TO_THEM_SURFACE.getName().equalsIgnoreCase(table)) {
			sqlStatement = "SELECT " + aggregateColumnsClause +
					" FROM " + schemaName + "." + table + " " + tableShortId + ", " + schemaName + ".test_other_to_thema_surfa  to2ts" +
					" WHERE to2ts.otherconstruction_id = ?" +
					" AND to2ts.thematic_surface_id = " + tableShortId + ".thematic_surface_id";
		}

		return sqlStatement;
	}

}
