package org.citydb.ade.test.exporter;

import org.citydb.ade.exporter.ADEExporter;
import org.citydb.ade.exporter.CityGMLExportHelper;
import org.citydb.ade.test.schema.ADETable;
import org.citydb.citygml.exporter.CityGMLExportException;
import org.citydb.citygml.exporter.database.content.GMLConverter;
import org.citydb.citygml.exporter.database.content.SurfaceGeometryExporter;
import org.citydb.config.geometry.GeometryObject;
import org.citydb.database.schema.mapping.AbstractType;
import org.citydb.database.schema.mapping.FeatureProperty;
import org.citydb.query.filter.projection.ProjectionFilter;
import org.citygml.ade.test.model.OtherConstruction;
import org.citygml.ade.test.model.module.TestADEModule;
import org.citygml4j.model.citygml.building.AbstractBoundarySurface;
import org.citygml4j.model.citygml.building.BoundarySurfaceProperty;
import org.citygml4j.model.gml.geometry.aggregates.MultiCurveProperty;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OtherConstructionExporter implements ADEExporter {
	private final CityGMLExportHelper helper;
	private final PreparedStatement ps;
	private final GMLConverter gmlConverter;
	private final SurfaceGeometryExporter surfaceGeometryExporter;

	public OtherConstructionExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws SQLException, CityGMLExportException {
		this.helper = helper;
		ps = connection.prepareStatement("select " +
				helper.getGeometryColumn("lod2multicurve") + ", " +
				"lod2solid_id from " +
				helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.OTHERCONSTRUCTION)) + " " +
				"where id = ?");
		gmlConverter = helper.getGMLConverter();
		surfaceGeometryExporter = helper.getSurfaceGeometryExporter();
	}

	public void doExport(OtherConstruction otherConstruction, long objectId, AbstractType<?> objectType, ProjectionFilter projectionFilter) throws CityGMLExportException, SQLException {
		ps.setLong(1, objectId);

		try (ResultSet rs = ps.executeQuery()) {
			if (rs.next()) {
				if (projectionFilter.containsProperty("lod2MultiCurve")) {
					Object multiCurveObj = rs.getObject("lod2multicurve");
					if (!rs.wasNull()) {
						GeometryObject multiCurve = helper.getDatabaseAdapter().getGeometryConverter().getMultiCurve(multiCurveObj);
						if (multiCurve != null) {
							MultiCurveProperty multiCurveProperty = gmlConverter.getMultiCurveProperty(multiCurve, false);
							if (multiCurveProperty != null) {
								otherConstruction.setLod2MultiCurve(multiCurveProperty);
							}
						}
					}
				}

				if (projectionFilter.containsProperty("lod2Solid")) {
					long surfaceGeometryId = rs.getLong("lod2solid_id");
					if (!rs.wasNull()) {
						surfaceGeometryExporter.addBatch(surfaceGeometryId, otherConstruction::setLod2Solid);
					}
				}

			}
		}

		if (projectionFilter.containsProperty("boundedBy")) {
			FeatureProperty property = objectType.getFeatureProperty("boundedBy", TestADEModule.v1_0.getNamespaceURI(), false);
			if (property != null) {
				for (AbstractBoundarySurface surface : helper.exportNestedCityGMLObjects(property, objectId, AbstractBoundarySurface.class))
					otherConstruction.addBoundedBySurface(new BoundarySurfaceProperty(surface));
			}
		}
	}

	@Override
	public void close() throws CityGMLExportException, SQLException {
		// nothing to do
	}

}
