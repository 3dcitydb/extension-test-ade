package org.citydb.ade.test.kmlExporter;

import org.citydb.ade.kmlExporter.ADEKmlExportHelper;
import org.citydb.ade.kmlExporter.ADEKmlExporter;

public class BuildingKmlExporter implements ADEKmlExporter {
	private ADEKmlExportHelper helper;
	private final String schema;

	public BuildingKmlExporter(ADEKmlExportHelper helper) {
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
				.append(helper.getSQLQueryHelper().getImplicitGeometryNullColumns())
				.append("from ").append(schema).append(".surface_geometry sg ")
				.append("where sg.root_id in (")
				.append("select tbu.lod").append(lod).append("multisurface_id ")
				.append("FROM ").append(schema).append(".test_buildingunit tbu ")
				.append("WHERE tbu.building_buildingunit_id=?) and sg.geometry is not null").toString();
	}

	@Override
	public String getSurfaceGeometryRefIdsQuery(int lod) {
		return new StringBuilder("select tbu.lod").append(lod).append("multisurface_id, tbu.objectclass_id, ")
				.append(helper.getSQLQueryHelper().getImplicitGeometryNullColumns())
				.append("FROM ").append(schema).append(".test_buildingunit tbu ")
				.append("WHERE tbu.building_buildingunit_id=? ")
				.append("AND tbu.lod").append(lod).append("multisurface_id is not null").toString();
	}
}
