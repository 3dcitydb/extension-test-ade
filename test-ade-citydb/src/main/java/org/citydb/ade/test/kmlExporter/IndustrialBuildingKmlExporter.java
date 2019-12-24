package org.citydb.ade.test.kmlExporter;

import org.citydb.config.project.kmlExporter.DisplayForm;
import org.citydb.config.project.kmlExporter.Lod0FootprintMode;
import org.citydb.modules.kml.ade.ADEKmlExportHelper;
import org.citydb.modules.kml.ade.ADEKmlExportQueries;
import org.citydb.modules.kml.ade.ADEKmlExporter;
import org.citydb.util.Util;
import org.citygml4j.model.citygml.building.Building;

public class IndustrialBuildingKmlExporter implements ADEKmlExporter {
	private ADEKmlExportHelper helper;

	public IndustrialBuildingKmlExporter(ADEKmlExportHelper helper) {
		this.helper = helper;
	}

	@Override
	public ADEKmlExportQueries getQueries() {
		return new IndustrialBuildingQueries(helper);
	}

	private class IndustrialBuildingQueries implements ADEKmlExportQueries {
		private ADEKmlExportHelper helper;
		private String schema;

		public IndustrialBuildingQueries(ADEKmlExportHelper helper) {
			this.helper = helper;
			this.schema = helper.getDatabaseAdapter().getConnectionDetails().getSchema();
		}

		@Override
		public String getPointAndCurveQuery(int lod) {
			return null;
		}

		@Override
		public String getSurfaceGeometryQuery(int lod) {
			DisplayForm displayForm = new DisplayForm(DisplayForm.FOOTPRINT, 0, -1);
			return helper.getSQLQueries().getBuildingPartQuery(lod, Lod0FootprintMode.ROOFPRINT_PRIOR_FOOTPRINT, displayForm, false, Util.getObjectClassId(Building.class));
		}

		@Override
		public String getSurfaceGeometryRefIdsQuery(int lod) {
			DisplayForm displayForm = new DisplayForm(DisplayForm.GEOMETRY, 0, -1);
			return helper.getSQLQueries().getBuildingPartQuery(lod, Lod0FootprintMode.ROOFPRINT_PRIOR_FOOTPRINT, displayForm, false, Util.getObjectClassId(Building.class));
		}

	}
}
