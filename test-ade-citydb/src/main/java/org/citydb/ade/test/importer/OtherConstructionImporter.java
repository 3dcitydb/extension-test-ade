package org.citydb.ade.test.importer;

import org.citydb.ade.importer.ADEImporter;
import org.citydb.ade.importer.CityGMLImportHelper;
import org.citydb.ade.test.schema.ADETable;
import org.citydb.ade.test.schema.SchemaMapper;
import org.citydb.citygml.importer.CityGMLImportException;
import org.citydb.config.geometry.GeometryObject;
import org.citydb.database.schema.mapping.AbstractObjectType;
import org.citygml.ade.test.model.OtherConstruction;
import org.citygml4j.model.citygml.building.BoundarySurfaceProperty;
import org.citygml4j.model.gml.geometry.primitives.SolidProperty;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

public class OtherConstructionImporter implements ADEImporter {
    private final Connection connection;
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;
	private final OtherConstructionToThematicSurfaceImporter constructionToThematicSurfaceImporter;

    private int batchCounter;

    public OtherConstructionImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.connection = connection;
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.OTHERCONSTRUCTION)) + " " +
                "(id, lod2multicurve, lod2solid_id) " +
                "values (?, ?, ?)");

        constructionToThematicSurfaceImporter = manager.getImporter(OtherConstructionToThematicSurfaceImporter.class);
    }

    public void doImport(OtherConstruction otherConstruction, long objectId, AbstractObjectType<?> objectType) throws CityGMLImportException, SQLException {
        ps.setLong(1, objectId);

        GeometryObject multiCurve = null;
        if (otherConstruction.isSetLod2MultiCurve()) {
            multiCurve = helper.getGeometryConverter().getMultiCurve(otherConstruction.getLod2MultiCurve());
            otherConstruction.getLod2MultiCurve().unsetMultiCurve();
        }

        if (multiCurve != null) {
            ps.setObject(2, helper.getDatabaseAdapter().getGeometryConverter().getDatabaseObject(multiCurve, connection));
        } else {
            ps.setNull(2, helper.getDatabaseAdapter().getGeometryConverter().getNullGeometryType(),
                    helper.getDatabaseAdapter().getGeometryConverter().getNullGeometryTypeName());
        }

        long solidGeometryId = 0;
        if (otherConstruction.isSetLod2Solid()) {
            SolidProperty solidProperty = otherConstruction.getLod2Solid();
            if (solidProperty.isSetSolid()) {
                solidGeometryId = helper.importSurfaceGeometry(solidProperty.getSolid(), objectId);
                solidProperty.unsetSolid();
            } else if (solidProperty.isSetHref()) {
                helper.propagateSurfaceGeometryXlink(solidProperty.getHref(),
                        objectType.getTable(),
                        objectId,
                        "lod2solid_id");
            }
        }

        if (solidGeometryId != 0) {
            ps.setLong(3, solidGeometryId);
        } else {
            ps.setNull(3, Types.NULL);
        }

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(objectType);

        if (otherConstruction.isSetBoundedBySurface()) {
            for (BoundarySurfaceProperty property : otherConstruction.getBoundedBySurface()) {
                if (property.isSetBoundarySurface()) {
                    long boundarySurfaceId = helper.importObject(property.getBoundarySurface());
                    property.unsetBoundarySurface();
                    constructionToThematicSurfaceImporter.doImport(objectId, boundarySurfaceId);
                } else if (property.isSetHref()) {
                    helper.propagateObjectXlink(schemaMapper.getTableName(ADETable.OTHER_TO_THEMA_SURFA),
                            objectId,
							"otherconstruction_id",
							property.getHref(),
							"thematic_surface_id");
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
