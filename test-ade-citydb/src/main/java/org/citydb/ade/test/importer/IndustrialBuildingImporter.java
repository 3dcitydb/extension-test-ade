package org.citydb.ade.test.importer;

import org.citydb.ade.importer.ADEImporter;
import org.citydb.ade.importer.CityGMLImportHelper;
import org.citydb.ade.importer.ForeignKeys;
import org.citydb.ade.test.schema.ADETable;
import org.citydb.citygml.importer.CityGMLImportException;
import org.citydb.database.schema.mapping.AbstractObjectType;
import org.citygml.ade.test.model.IndustrialBuilding;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class IndustrialBuildingImporter implements ADEImporter {
	private final CityGMLImportHelper helper;
	private final PreparedStatement ps;

	private int batchCounter;
	
	public IndustrialBuildingImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws SQLException {
		this.helper = helper;

		ps = connection.prepareStatement("insert into " +
				helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.INDUSTRIALBUILDING)) + " " +
				"(id, remark) " +
				"values (?, ?)");
	}
	
	public void doImport(IndustrialBuilding building, long objectId, AbstractObjectType<?> objectType, ForeignKeys foreignKeys) throws CityGMLImportException, SQLException {
		ps.setLong(1, objectId);
		ps.setString(2, building.getRemark());

		ps.addBatch();
		if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize()) {
			helper.executeBatch(objectType);
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
