package org.citydb.ade.test.importer;

import org.citydb.ade.importer.ADEImporter;
import org.citydb.ade.importer.CityGMLImportHelper;
import org.citydb.ade.importer.ForeignKeys;
import org.citydb.ade.test.schema.ADETable;
import org.citydb.citygml.importer.CityGMLImportException;
import org.citydb.database.schema.mapping.AbstractObjectType;
import org.citygml.ade.test.model.IndustrialBuildingPart;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

public class IndustrialBuildingPartImporter implements ADEImporter {
	private final CityGMLImportHelper helper;
	
	private PreparedStatement ps;
	private int batchCounter;
	
	public IndustrialBuildingPartImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws SQLException {
		this.helper = helper;
		
		StringBuilder stmt = new StringBuilder("insert into ")
				.append(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.INDUSTRIALBUILDINGPA))).append(" ")
				.append("(id, remark) ")
				.append("values (?, ?)");
		ps = connection.prepareStatement(stmt.toString());
	}
	
	public void doImport(IndustrialBuildingPart buildingPart, long objectId, AbstractObjectType<?> objectType, ForeignKeys foreignKeys) throws CityGMLImportException, SQLException {
		ps.setLong(1, objectId);
		
		if (buildingPart.isSetRemark())
			ps.setString(2, buildingPart.getRemark());
		else
			ps.setNull(2, Types.VARCHAR);
		
		ps.addBatch();
		if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
			helper.executeBatch(objectType);
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
