package org.citydb.ade.test.importer;

import org.citydb.ade.importer.ADEImporter;
import org.citydb.ade.importer.CityGMLImportHelper;
import org.citydb.ade.test.schema.ADETable;
import org.citydb.ade.test.schema.SchemaMapper;
import org.citydb.citygml.importer.CityGMLImportException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OtherConstructionToThematicSurfaceImporter implements ADEImporter {
	private final CityGMLImportHelper helper;
	private final SchemaMapper schemaMapper;
	
	private PreparedStatement ps;
	private int batchCounter;
	
	public OtherConstructionToThematicSurfaceImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws SQLException {
		this.helper = helper;
		this.schemaMapper = manager.getSchemaMapper();
		
		StringBuilder stmt = new StringBuilder("insert into ")
				.append(helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.OTHER_TO_THEMA_SURFA))).append(" ")
				.append("(otherconstruction_id, thematic_surface_id) ")
				.append("values (?, ?)");
		ps = connection.prepareStatement(stmt.toString());
	}
	
	public void doImport(long otherConstructionId, long boundarySurfaceId) throws CityGMLImportException, SQLException {
		ps.setLong(1, otherConstructionId);
		ps.setLong(2, boundarySurfaceId);
		
		ps.addBatch();
		if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
			helper.executeBatch(schemaMapper.getTableName(ADETable.OTHER_TO_THEMA_SURFA));
	}

	@Override
	public void executeBatch() throws CityGMLImportException, SQLException {
		if (batchCounter > 0) {
			ps.executeBatch();
			batchCounter = 0;
		}
	}

	@Override
	public void close() throws CityGMLImportException, SQLException {
		ps.close();
	}

}
