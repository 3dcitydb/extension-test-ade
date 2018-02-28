package org.citydb.ade.test.importer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

import org.citydb.ade.importer.ADEImporter;
import org.citydb.ade.importer.CityGMLImportHelper;
import org.citydb.ade.importer.ForeignKeys;
import org.citydb.ade.test.schema.ADETables;
import org.citydb.citygml.importer.CityGMLImportException;
import org.citydb.database.schema.mapping.AbstractObjectType;
import org.citygml.ade.test.model.AbstractFacilities;

public class FacilitiesImporter implements ADEImporter {
	private final CityGMLImportHelper helper;
	
	private PreparedStatement ps;
	private int batchCounter;
	
	public FacilitiesImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws SQLException {
		this.helper = helper;
		
		StringBuilder stmt = new StringBuilder("insert into ")
				.append(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETables.FACILITIES))).append(" ")
				.append("(id, objectclass_id, buildingunit_equippedwith_id, totalvalue, totalvalue_uom) ")
				.append("values (?, ?, ?, ?, ?)");
		ps = connection.prepareStatement(stmt.toString());
	}
	
	public void doImport(AbstractFacilities facilities, long objectId, AbstractObjectType<?> objectType, ForeignKeys foreignKeys) throws CityGMLImportException, SQLException {
		long parentId = foreignKeys.get("parentId");		
		
		ps.setLong(1, objectId);
		
		ps.setLong(2, objectType.getObjectClassId());
		
		if (parentId != 0)
			ps.setLong(3, parentId);
		else
			ps.setNull(3, Types.NULL);
		
		if (facilities.isSetTotalValue() && facilities.getTotalValue().isSetValue()) {
			ps.setDouble(4, facilities.getTotalValue().getValue());
			ps.setString(5, facilities.getTotalValue().getUom());
		} else {
			ps.setNull(4, Types.DOUBLE);
			ps.setNull(5, Types.VARCHAR);
		}
		
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
	public void close() throws CityGMLImportException, SQLException {
		ps.close();
	}

}
