package org.citydb.ade.test.kmlExporter;

import org.citydb.ade.test.schema.SchemaMapper;
import org.citydb.modules.kml.ade.ADEKmlExportHelper;
import org.citydb.modules.kml.ade.ADEKmlExporter;

public class BuildingKmlExporter implements ADEKmlExporter {
	private ADEKmlExportHelper helper;
	private final String schema;
	private final SchemaMapper schemaMapper;

	public BuildingKmlExporter(ADEKmlExportHelper helper, KMLExportManager manager) {
		this.helper = helper;
		this.schema = helper.getDatabaseAdapter().getConnectionDetails().getSchema();
		this.schemaMapper = manager.getSchemaMapper();
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
				.append("select tbu.lod").append(lod).append("multisurface_id ")
				.append("FROM ").append(schema).append(".test_buildingunit tbu ")
				.append("WHERE tbu.building_buildingunit_id=?) and sg.geometry is not null").toString();
	}

	@Override
	public String getSurfaceGeometryRefIdsQuery(int lod) {
		return new StringBuilder("select tbu.lod").append(lod).append("multisurface_id, tbu.objectclass_id, ")
				.append(helper.getSQLQueries().getImplicitGeometryNullColumns())
				.append("FROM ").append(schema).append(".test_buildingunit tbu ")
				.append("WHERE tbu.building_buildingunit_id=? ")
				.append("AND tbu.lod").append(lod).append("multisurface_id is not null").toString();
	}
}
