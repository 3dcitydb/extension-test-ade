package org.citydb.ade.test.kmlExporter;

import org.citydb.ade.kmlExporter.ADEKmlExportHelper;
import org.citydb.ade.kmlExporter.ADEKmlExporter;

public class BuildingKmlExporter implements ADEKmlExporter {

	public BuildingKmlExporter(ADEKmlExportHelper helper) {

	}

	@Override
	public String getPointAndCurveQuery(int lod) {
		return null;
	}

	@Override
	public String getSurfaceGeometryQuery(int lod) {
		return null;
	}

	@Override
	public String getSurfaceGeometryRefIdsQuery(int lod) {
		return null;
	}
}
