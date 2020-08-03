package org.citydb.ade.test.importer;

import org.citydb.ade.importer.ADEImporter;
import org.citydb.ade.importer.CityGMLImportHelper;
import org.citydb.ade.test.schema.ADETable;
import org.citydb.ade.test.schema.SchemaMapper;
import org.citydb.citygml.importer.CityGMLImportException;
import org.citydb.database.schema.mapping.AbstractObjectType;
import org.citygml.ade.test.model.OtherConstruction;
import org.citygml4j.model.citygml.building.AbstractBoundarySurface;
import org.citygml4j.model.citygml.building.BoundarySurfaceProperty;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OtherConstructionImporter implements ADEImporter {
	private final CityGMLImportHelper helper;
	private final SchemaMapper schemaMapper;
	
	private OtherConstructionToThematicSurfaceImporter boundarySurfaceImporter;
	private PreparedStatement ps;
	private int batchCounter;
	
	public OtherConstructionImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
		this.helper = helper;
		this.schemaMapper = manager.getSchemaMapper();
		
		StringBuilder stmt = new StringBuilder("insert into ")
				.append(helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.OTHERCONSTRUCTION))).append(" ")
				.append("(id) ")
				.append("values (?)");
		ps = connection.prepareStatement(stmt.toString());
		
		boundarySurfaceImporter = manager.getImporter(OtherConstructionToThematicSurfaceImporter.class);
	}
	
	public void doImport(OtherConstruction otherConstruction, long objectId, AbstractObjectType<?> objectType) throws CityGMLImportException, SQLException {
		ps.setLong(1, objectId);
			
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
