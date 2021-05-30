package org.citydb.ade.test.balloon;

import org.citydb.ade.kmlExporter.ADEBalloonException;
import org.citydb.ade.kmlExporter.ADEBalloonHandler;
import org.citydb.ade.kmlExporter.ADEBalloonManager;
import org.citydb.ade.test.schema.ADETableMapper;
import org.citydb.ade.test.schema.SchemaMapper;
import org.citydb.util.Util;
import org.citygml.ade.test.model.IndustrialBuilding;
import org.citygml.ade.test.model.OtherConstruction;
import org.citygml4j.model.citygml.building.AbstractBuilding;
import org.citygml4j.model.gml.base.AbstractGML;
import org.citygml4j.model.module.citygml.CityGMLVersion;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class BalloonManager implements ADEBalloonManager {
	private final ADETableMapper tableMapper;
	private final SchemaMapper schemaMapper;
	private final Map<Class<? extends ADEBalloonHandler>, ADEBalloonHandler> balloonHandlers;

	public BalloonManager (ADETableMapper tableMapper, SchemaMapper schemaMapper) {
		this.tableMapper = tableMapper;
		this.schemaMapper = schemaMapper;
		this.balloonHandlers = new HashMap<>();
	}

	@Override
	public ADEBalloonHandler getBalloonHandler(int objectClassId) throws ADEBalloonException {
		AbstractGML modelObject = Util.createObject(objectClassId, CityGMLVersion.v2_0_0);
		ADEBalloonHandler balloonHandler = null;

		if (modelObject instanceof IndustrialBuilding) {
			balloonHandler = getBalloonHandler(IndustrialBuildingBalloonHandler.class);
		} else if (modelObject instanceof AbstractBuilding) {
			balloonHandler = getBalloonHandler(BuildingBalloonHandler.class);
		} else if (modelObject instanceof OtherConstruction) {
			balloonHandler = getBalloonHandler(OtherConstructionBalloonHandler.class);
		}

		return balloonHandler;
	}

	public SchemaMapper getSchemaMapper() {
		return schemaMapper;
	}

	private <T extends ADEBalloonHandler> T getBalloonHandler(Class<T> type) throws ADEBalloonException {
		ADEBalloonHandler balloonHandler = balloonHandlers.get(type);

		if (balloonHandler == null) {
			if (type == BuildingBalloonHandler.class) {
				balloonHandler = new BuildingBalloonHandler(this);
			} else if (type == IndustrialBuildingBalloonHandler.class) {
				balloonHandler = new IndustrialBuildingBalloonHandler(this);
			} else if (type == OtherConstructionBalloonHandler.class) {
				balloonHandler = new OtherConstructionBalloonHandler(this);
			}

			if (balloonHandler == null)
				throw new ADEBalloonException("Failed to build ADE Balloon handler for the type " + type.getName() + ".");

			balloonHandlers.put(type, balloonHandler);
		}

		return type.cast(balloonHandler);
	}

	@Override
	public HashMap<String, Set<String>> getTablesAndColumns() {
		return tableMapper.getTablesAndColumns();
	}

}
