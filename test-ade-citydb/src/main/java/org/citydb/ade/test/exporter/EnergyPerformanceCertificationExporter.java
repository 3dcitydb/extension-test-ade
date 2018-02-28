package org.citydb.ade.test.exporter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.citydb.ade.exporter.ADEExporter;
import org.citydb.ade.exporter.CityGMLExportHelper;
import org.citydb.ade.test.schema.ADETables;
import org.citydb.citygml.exporter.CityGMLExportException;
import org.citydb.citygml.exporter.util.AttributeValueSplitter;
import org.citydb.citygml.exporter.util.AttributeValueSplitter.SplitValue;
import org.citygml.ade.test.model.AbstractBuildingUnit;
import org.citygml.ade.test.model.EnergyPerformanceCertification;
import org.citygml.ade.test.model.EnergyPerformanceCertificationProperty;

public class EnergyPerformanceCertificationExporter implements ADEExporter {
	private PreparedStatement ps;

	private AttributeValueSplitter valueSplitter;

	public EnergyPerformanceCertificationExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws SQLException {
		StringBuilder stmt = new StringBuilder("select certificationname, certificationid from ")
				.append(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETables.ENERGYPERFORMANCECER))).append(" ")
				.append("where buildingunit_energyperfor_id = ?");
		ps = connection.prepareStatement(stmt.toString());

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
