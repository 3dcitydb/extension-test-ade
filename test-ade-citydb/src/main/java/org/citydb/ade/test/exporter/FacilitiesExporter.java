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
import org.citygml.ade.test.model.AbstractBuildingUnit;
import org.citygml.ade.test.model.AbstractFacilities;
import org.citygml.ade.test.model.FacilitiesProperty;
import org.citygml4j.model.gml.basicTypes.Measure;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FacilitiesExporter implements ADEExporter {
	private final CityGMLExportHelper helper;
	private final PreparedStatement ps;

	public FacilitiesExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws SQLException {
		this.helper = helper;

		ps = connection.prepareStatement("select id, objectclass_id, totalvalue, totalvalue_uom from " +
				helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.FACILITIES)) + " " +
				"where buildingunit_equippedwith_id = ?");
	}

	public void doExport(AbstractBuildingUnit parent, long parentId) throws CityGMLExportException, SQLException {
		ps.setLong(1, parentId);

		try (ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				double totalValue = rs.getDouble(3);
				if (rs.wasNull())
					continue;				
				
				long facilitiesId = rs.getLong(1);
				int objectClassId = rs.getInt(2);
				
				AbstractFacilities facilities = helper.createObject(facilitiesId, objectClassId, AbstractFacilities.class);
				if (facilities == null) {
					helper.logOrThrowErrorMessage("Failed to instantiate " + helper.getObjectSignature(objectClassId, facilitiesId) + " as facilities object.");
					continue;
				}

				Measure measure = new Measure(totalValue);
				measure.setUom(rs.getString(4));
				facilities.setTotalValue(measure);
				
				parent.addEquippedWith(new FacilitiesProperty(facilities));
			}
		}
	}

	@Override
	public void close() throws CityGMLExportException, SQLException {
		ps.close();	
	}

}
