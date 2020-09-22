package org.citydb.ade.test.exporter;

import org.citydb.ade.exporter.ADEExporter;
import org.citydb.ade.exporter.CityGMLExportHelper;
import org.citydb.ade.test.schema.ADETable;
import org.citydb.citygml.exporter.CityGMLExportException;
import org.citydb.citygml.exporter.util.AttributeValueSplitter;
import org.citydb.citygml.exporter.util.AttributeValueSplitter.SplitValue;
import org.citygml.ade.test.model.AbstractBuildingUnit;
import org.citygml.ade.test.model.EnergyPerformanceCertification;
import org.citygml.ade.test.model.EnergyPerformanceCertificationProperty;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EnergyPerformanceCertificationExporter implements ADEExporter {
	private final PreparedStatement ps;
	private final AttributeValueSplitter valueSplitter;

	public EnergyPerformanceCertificationExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws SQLException {
		ps = connection.prepareStatement("select certificationname, certificationid from " +
				helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.ENERGYPERFORMANCECER)) + " " +
				"where buildingunit_energyperfor_id = ?");

		valueSplitter = helper.getAttributeValueSplitter();
	}

	public void doExport(AbstractBuildingUnit parent, long parentId) throws CityGMLExportException, SQLException {
		ps.setLong(1, parentId);

		try (ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				String id = rs.getString(2);
				if (rs.wasNull())
					continue;

				String name = rs.getString(1);
				if (rs.wasNull())
					continue;

				EnergyPerformanceCertification energyCertification = new EnergyPerformanceCertification();
				energyCertification.setCertificationId(id);
				for (SplitValue splitValue : valueSplitter.split(name))
					energyCertification.addCertificationName(splitValue.result(0));

				parent.addEnergyPerformanceCertification(new EnergyPerformanceCertificationProperty(energyCertification));
			}
		}
	}

	@Override
	public void close() throws CityGMLExportException, SQLException {
		ps.close();	
	}

}
