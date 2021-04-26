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

package org.citydb.ade.test.importer;

import org.citydb.ade.importer.ADEImporter;
import org.citydb.ade.importer.CityGMLImportHelper;
import org.citydb.ade.importer.ForeignKeys;
import org.citydb.ade.test.schema.ADETable;
import org.citydb.citygml.importer.CityGMLImportException;
import org.citydb.database.schema.mapping.AbstractObjectType;
import org.citygml.ade.test.model.AbstractFacilities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

public class FacilitiesImporter implements ADEImporter {
	private final CityGMLImportHelper helper;
	private final PreparedStatement ps;

	private int batchCounter;
	
	public FacilitiesImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws SQLException {
		this.helper = helper;

		ps = connection.prepareStatement("insert into " +
				helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.FACILITIES)) + " " +
				"(id, objectclass_id, buildingunit_equippedwith_id, totalvalue, totalvalue_uom) " +
				"values (?, ?, ?, ?, ?)");
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
