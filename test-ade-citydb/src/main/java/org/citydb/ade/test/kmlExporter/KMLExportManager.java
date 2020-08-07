package org.citydb.ade.test.kmlExporter;

import org.citydb.ade.test.schema.SchemaMapper;
import org.citydb.modules.kml.ade.ADEKmlExportException;
import org.citydb.modules.kml.ade.ADEKmlExportHelper;
import org.citydb.modules.kml.ade.ADEKmlExportManager;
import org.citydb.modules.kml.ade.ADEKmlExporter;
import org.citydb.util.Util;
import org.citygml.ade.test.model.IndustrialBuilding;
import org.citygml.ade.test.model.OtherConstruction;
import org.citygml4j.model.citygml.building.Building;
import org.citygml4j.model.gml.base.AbstractGML;
import org.citygml4j.model.module.citygml.CityGMLVersion;

import java.util.HashMap;
import java.util.Map;

public class KMLExportManager implements ADEKmlExportManager {
	private final SchemaMapper schemaMapper;
	private final Map<Class<? extends ADEKmlExporter>, ADEKmlExporter> exporters;
	private ADEKmlExportHelper helper;

	public KMLExportManager(SchemaMapper schemaMapper) {
		this.schemaMapper = schemaMapper;
		exporters = new HashMap<>();
	}

	@Override
	public void init(ADEKmlExportHelper helper) {
		this.helper = helper;
	}

	@Override
	public ADEKmlExporter getKmlExporter(int objectClassId) throws ADEKmlExportException {
		AbstractGML modelObject = Util.createObject(objectClassId, CityGMLVersion.v2_0_0);
		ADEKmlExporter exporter = null;

		if (modelObject instanceof Building) {
			exporter = getKmlExporter(BuildingKmlExporter.class);
		} else if (modelObject instanceof OtherConstruction) {
			exporter = getKmlExporter(OtherConstructionKmlExporter.class);
		} else if (modelObject instanceof IndustrialBuilding) {
			exporter = getKmlExporter(IndustrialBuildingKmlExporter.class);
		}

		return exporter;
	}

	public SchemaMapper getSchemaMapper() {
		return schemaMapper;
	}

	private <T extends ADEKmlExporter> T getKmlExporter(Class<T> type) throws ADEKmlExportException {
		ADEKmlExporter exporter = exporters.get(type);

		if (exporter == null) {
			if (type == BuildingKmlExporter.class) {
				exporter = new BuildingKmlExporter(helper, this);
			} else if (type == OtherConstructionKmlExporter.class) {
				exporter = new OtherConstructionKmlExporter(helper, this);
			} else if (type == IndustrialBuildingKmlExporter.class) {
				exporter = new IndustrialBuildingKmlExporter(helper, this);
			}

			if (exporter == null)
				throw new ADEKmlExportException("Failed to build ADE KML exporter of type " + type.getName() + ".");

			exporters.put(type, exporter);
		}

		return type.cast(exporter);
	}
}
