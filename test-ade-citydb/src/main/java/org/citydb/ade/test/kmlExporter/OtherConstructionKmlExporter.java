package org.citydb.ade.test.kmlExporter;

import org.citydb.modules.kml.ade.ADEKmlExportHelper;
import org.citydb.modules.kml.ade.ADEKmlExportQueries;
import org.citydb.modules.kml.ade.ADEKmlExporter;

public class OtherConstructionKmlExporter implements ADEKmlExporter {
	private ADEKmlExportHelper helper;

	public OtherConstructionKmlExporter(ADEKmlExportHelper helper) {
		this.helper = helper;
	}

	@Override
	public ADEKmlExportQueries getQueries() {
		return new OtherConstructionQueries(helper);
	}

	private class OtherConstructionQueries implements ADEKmlExportQueries {
		private ADEKmlExportHelper helper;
		private String schema;

		public OtherConstructionQueries(ADEKmlExportHelper helper) {
			this.helper = helper;
			this.schema = helper.getDatabaseAdapter().getConnectionDetails().getSchema();
		}

		@Override
		public String getPointAndCurveQuery(int lod) {
			return null;
		}

		@Override
		public String getSurfaceGeometryQuery(int lod) {
			return new StringBuilder("select sg.geometry, ")
					.append(helper.getSQLQueries().getImplicitGeometryNullColumns())
					.append("from ").append(schema).append(".surface_geometry sg ")
					.append("where sg.root_id in (")
					.append("select ts.lod").append(lod).append("_multi_surface_id from thematic_surface ts ")
					.append("JOIN ").append(schema).append(".test_other_to_thema_surfa to2ts on to2ts.thematic_surface_id=ts.id ")
					.append("JOIN ").append(schema).append(".test_otherconstruction tos on tos.id=to2ts.otherconstruction_id ")
					.append("WHERE tos.id=?) and sg.geometry is not null").toString();
		}

		@Override
		public String getSurfaceGeometryRefIdsQuery(int lod) {
			return new StringBuilder("select ts.lod").append(lod).append("_multi_surface_id, ts.objectclass_id from thematic_surface ts ")
					.append("JOIN ").append(schema).append(".test_other_to_thema_surfa to2ts on to2ts.thematic_surface_id=ts.id ")
					.append("JOIN ").append(schema).append(".test_otherconstruction tos on tos.id=to2ts.otherconstruction_id ")
					.append("WHERE tos.id=?").toString();
		}

	}
}
