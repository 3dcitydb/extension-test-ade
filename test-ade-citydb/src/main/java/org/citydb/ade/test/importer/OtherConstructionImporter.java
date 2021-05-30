package org.citydb.ade.test.importer;

import org.citydb.ade.importer.ADEImporter;
import org.citydb.ade.importer.CityGMLImportHelper;
import org.citydb.ade.test.schema.ADETable;
import org.citydb.ade.test.schema.SchemaMapper;
import org.citydb.citygml.importer.CityGMLImportException;
import org.citydb.config.geometry.GeometryObject;
import org.citydb.database.schema.mapping.AbstractObjectType;
import org.citygml.ade.test.model.OtherConstruction;
import org.citygml4j.model.citygml.building.AbstractBoundarySurface;
import org.citygml4j.model.citygml.building.BoundarySurfaceProperty;
import org.citygml4j.model.gml.geometry.aggregates.MultiCurveProperty;
import org.citygml4j.model.gml.geometry.primitives.SolidProperty;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

public class OtherConstructionImporter implements ADEImporter {
	private final Connection connection;
	private final CityGMLImportHelper helper;
	private final SchemaMapper schemaMapper;
	private final OtherConstructionToThematicSurfaceImporter boundarySurfaceImporter;
	private final PreparedStatement ps;

	private int batchCounter;
	
	public OtherConstructionImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
		this.connection = connection;
		this.helper = helper;
		this.schemaMapper = manager.getSchemaMapper();

		ps = connection.prepareStatement("insert into " +
				helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.OTHERCONSTRUCTION)) + " " +
				"(id, lod2multicurve, lod2solid_id) " +
				"values (?, ?, ?)");
		
		boundarySurfaceImporter = manager.getImporter(OtherConstructionToThematicSurfaceImporter.class);
	}
	
	public void doImport(OtherConstruction otherConstruction, long objectId, AbstractObjectType<?> objectType) throws CityGMLImportException, SQLException {
		ps.setLong(1, objectId);

		MultiCurveProperty multiCurveProperty = otherConstruction.getLod2MultiCurve();
		GeometryObject multiLine = null;

		if (multiCurveProperty != null) {
			multiLine = helper.getGeometryConverter().getMultiCurve(multiCurveProperty);
			multiCurveProperty.unsetMultiCurve();
		}

		if (multiLine != null) {
			Object multiLineObj = helper.getDatabaseAdapter().getGeometryConverter().getDatabaseObject(multiLine, connection);
			ps.setObject(2, multiLineObj);
		} else
			ps.setNull(2,
					helper.getDatabaseAdapter().getGeometryConverter().getNullGeometryType(),
					helper.getDatabaseAdapter().getGeometryConverter().getNullGeometryTypeName());

		SolidProperty solidProperty = otherConstruction.getLod2Solid();;
		long solidGeometryId = 0;

		if (solidProperty != null) {
			if (solidProperty.isSetSolid()) {
				solidGeometryId = helper.importSurfaceGeometry(solidProperty.getSolid(), objectId);
				solidProperty.unsetSolid();
			} else {
				String href = solidProperty.getHref();
				if (href != null && href.length() != 0)
					helper.propagateSurfaceGeometryXlink(href, objectType.getTable(), objectId, "lod2_solid_id");
			}
		}

		if (solidGeometryId != 0)
			ps.setLong(3, solidGeometryId);
		else
			ps.setNull(3, Types.NULL);

		ps.addBatch();
		if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
			helper.executeBatch(objectType);

		if (otherConstruction.isSetBoundedBySurface()) {
			for (BoundarySurfaceProperty property : otherConstruction.getBoundedBySurface()) {
				AbstractBoundarySurface boundarySurface = property.getBoundarySurface();
				
				if (boundarySurface != null) {
					long boundarySurfaceId = helper.importObject(boundarySurface);
					boundarySurfaceImporter.doImport(objectId, boundarySurfaceId);
				} else {
					String href = property.getHref();
					if (href != null && href.length() != 0)
						helper.propagateObjectXlink(schemaMapper.getTableName(ADETable.OTHER_TO_THEMA_SURFA), objectId, "otherconstruction_id", href, "thematic_surface_id");
				}
			}
		}
	}
	
	@Override
	public void executeBatch() throws CityGMLImportException, SQLException {
		if (batchCounter > 0) {
			ps.executeBatch();
			batchCounter = 0;
		}
	}
	
	@Override
	public void close() throws SQLException {
		ps.close();
	}
}
