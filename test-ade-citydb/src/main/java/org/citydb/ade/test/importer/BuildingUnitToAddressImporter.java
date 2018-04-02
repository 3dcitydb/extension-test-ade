package org.citydb.ade.test.importer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.citydb.ade.importer.ADEImporter;
import org.citydb.ade.importer.CityGMLImportHelper;
import org.citydb.ade.test.schema.ADETable;
import org.citydb.ade.test.schema.SchemaMapper;
import org.citydb.citygml.importer.CityGMLImportException;

public class BuildingUnitToAddressImporter implements ADEImporter {
	private final CityGMLImportHelper helper;
	private final SchemaMapper schemaMapper;
	
	private PreparedStatement ps;
	private int batchCounter;
	
	public BuildingUnitToAddressImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws SQLException {
		this.helper = helper;
		this.schemaMapper = manager.getSchemaMapper();
		
		StringBuilder stmt = new StringBuilder("insert into ")
				.append(helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.BUILDINGU_TO_ADDRESS))).append(" ")
				.append("(buildingunit_id, address_id) ")
				.append("values (?, ?)");
		ps = connection.prepareStatement(stmt.toString());
	}
	
	public void doImport(long buildingUnitId, long addressId) throws CityGMLImportException, SQLException {
		ps.setLong(1, buildingUnitId);
		ps.setLong(2, addressId);
		
		ps.addBatch();
		if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
			helper.executeBatch(schemaMapper.getTableName(ADETable.BUILDINGU_TO_ADDRESS));
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
