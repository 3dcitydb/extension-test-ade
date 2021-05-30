package org.citydb.ade.test.balloon;

import org.citydb.ade.kmlExporter.ADEBalloonHandler;
import org.citydb.ade.test.schema.ADETable;
import org.citydb.ade.test.schema.SchemaMapper;

public class IndustrialBuildingBalloonHandler implements ADEBalloonHandler {
	private final SchemaMapper schemaMapper;
	private final BuildingBalloonHandler hooksBuildingBalloonHandler;

	public IndustrialBuildingBalloonHandler(BalloonManager manager) {
		this.schemaMapper = manager.getSchemaMapper();
		this.hooksBuildingBalloonHandler = new BuildingBalloonHandler(manager);
	}

	@Override
	public String getSqlStatement(String table,
	                              String tableShortId,
	                              String aggregateColumnsClause,
	                              int lod,
	                              String schemaName) {

		String sqlStatement = null;

		if (schemaMapper.getTableName(ADETable.INDUSTRIALBUILDING).equalsIgnoreCase(table)) {
			sqlStatement = "SELECT " + aggregateColumnsClause +
					" FROM " + schemaName + "." + table + " " + tableShortId +
					" WHERE " + tableShortId + ".id = ?";
		} else if (schemaMapper.getTableName(ADETable.BUILDING).equalsIgnoreCase(table)) {
			sqlStatement = hooksBuildingBalloonHandler.getSqlStatement(table, tableShortId, aggregateColumnsClause, lod, schemaName);
		}

		return sqlStatement;
	}

}
