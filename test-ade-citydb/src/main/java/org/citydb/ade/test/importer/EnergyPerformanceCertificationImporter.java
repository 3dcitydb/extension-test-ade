package org.citydb.ade.test.importer;

import org.citydb.ade.importer.ADEImporter;
import org.citydb.ade.importer.CityGMLImportHelper;
import org.citydb.ade.test.schema.ADESequence;
import org.citydb.ade.test.schema.ADETable;
import org.citydb.ade.test.schema.SchemaMapper;
import org.citydb.citygml.importer.CityGMLImportException;
import org.citydb.citygml.importer.util.AttributeValueJoiner;
import org.citygml.ade.test.model.EnergyPerformanceCertification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

public class EnergyPerformanceCertificationImporter implements ADEImporter {
	private final CityGMLImportHelper helper;
	private final SchemaMapper schemaMapper;
	
	private AttributeValueJoiner valueJoiner;
	private PreparedStatement ps;
	private int batchCounter;
	
	public EnergyPerformanceCertificationImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws SQLException {
		this.helper = helper;
		this.schemaMapper = manager.getSchemaMapper();
		
		StringBuilder stmt = new StringBuilder("insert into ")
				.append(helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.ENERGYPERFORMANCECER))).append(" ")
				.append("(id, buildingunit_energyperfor_id, certificationname, certificationid) ")
				.append("values (?, ?, ?, ?)");
		ps = connection.prepareStatement(stmt.toString());
		
		valueJoiner = helper.getAttributeValueJoiner();
	}
	
	public void doImport(EnergyPerformanceCertification energyCertification, long parentId) throws CityGMLImportException, SQLException {
		long objectId = helper.getNextSequenceValue(schemaMapper.getSequenceName(ADESequence.ENERGYPERFORMANC_SEQ));
		ps.setLong(1, objectId);
		
		if (parentId != 0)
			ps.setLong(2, parentId);
		else
			ps.setNull(2, Types.NULL);
		
		if (energyCertification.isSetCertificationName())
			ps.setString(3, valueJoiner.join(energyCertification.getCertificationName()));
		else
			ps.setNull(3, Types.VARCHAR);
		
		ps.setString(4, energyCertification.getCertificationId());
		
		ps.addBatch();
		if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
			helper.executeBatch(schemaMapper.getTableName(ADETable.ENERGYPERFORMANCECER));
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
