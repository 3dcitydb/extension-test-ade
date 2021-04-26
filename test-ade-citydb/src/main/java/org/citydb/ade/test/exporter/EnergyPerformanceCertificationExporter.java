/*
 * 3D City Database - The Open Source CityGML Database
 * https://www.3dcitydb.org/
 *
 * Copyright 2013 - 2021
 * Chair of Geoinformatics
 * Technical University of Munich, Germany
 * https://www.lrg.tum.de/gis/
 *
 * The 3D City Database is jointly developed with the following
 * cooperation partners:
 *
 * Virtual City Systems, Berlin <https://vc.systems/>
 * M.O.S.S. Computer Grafik Systeme GmbH, Taufkirchen <http://www.moss.de/>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
