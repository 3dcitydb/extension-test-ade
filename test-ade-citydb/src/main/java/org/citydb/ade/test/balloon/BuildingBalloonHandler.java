package org.citydb.ade.test.balloon;

import org.citydb.ade.kmlExporter.ADEBalloonHandler;

public class BuildingBalloonHandler implements ADEBalloonHandler {

	@Override
	public String getSqlStatement(String table,
	                              String tableShortId,
	                              String aggregateColumnsClause,
	                              int lod,
	                              String schemaName) {

		return null;
	}

}
