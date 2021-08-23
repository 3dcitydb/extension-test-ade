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

import org.citydb.ade.test.schema.ADETable;
import org.citydb.core.ade.exporter.ADEExporter;
import org.citydb.core.ade.exporter.CityGMLExportHelper;
import org.citydb.core.database.schema.mapping.FeatureType;
import org.citydb.core.operation.exporter.CityGMLExportException;
import org.citydb.core.operation.exporter.util.AttributeValueSplitter;
import org.citydb.core.operation.exporter.util.SplitValue;
import org.citydb.core.query.filter.lod.LodFilter;
import org.citydb.core.query.filter.projection.ProjectionFilter;
import org.citygml.ade.test.model.*;
import org.citygml.ade.test.model.module.TestADEModule;
import org.citygml4j.model.citygml.building.AbstractBuilding;
import org.citygml4j.model.gml.measures.Area;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BuildingPropertiesExporter implements ADEExporter {
	private final PreparedStatement ps;
	private final BuildingUnitExporter buildingUnitExporter;
	private final AttributeValueSplitter valueSplitter;
	private final LodFilter lodFilter;

	public BuildingPropertiesExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws CityGMLExportException, SQLException {
		ps = connection.prepareStatement("select ownername, floorarea, floorarea_uom, energyperforma_certification, energyperform_certificatio_1 from " +
				helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.BUILDING)) + " " +
				"where id = ?");

		buildingUnitExporter = manager.getExporter(BuildingUnitExporter.class);
		valueSplitter = helper.getAttributeValueSplitter();
		lodFilter = helper.getLodFilter();
	}

	public void doExport(AbstractBuilding parent, long parentId, FeatureType parentType, ProjectionFilter projectionFilter) throws CityGMLExportException, SQLException {
		ps.setLong(1, parentId);

		try (ResultSet rs = ps.executeQuery()) {
			if (rs.next()) {
				if (projectionFilter.containsProperty("ownerName", TestADEModule.v1_0.getNamespaceURI())) {
					String ownerName = rs.getString(1);
					if (!rs.wasNull()) {
						OwnerNameProperty property = new OwnerNameProperty(ownerName);
						parent.addGenericApplicationPropertyOfAbstractBuilding(property);
					}
				}

				if (projectionFilter.containsProperty("floorArea", TestADEModule.v1_0.getNamespaceURI())) {
					double floorArea = rs.getDouble(2);
					if (!rs.wasNull()) {
						Area area = new Area(floorArea);
						area.setUom(rs.getString(3));
						FloorAreaProperty property = new FloorAreaProperty(area);						
						parent.addGenericApplicationPropertyOfAbstractBuilding(property);
					}
				}

				if (projectionFilter.containsProperty("energyPerformanceCertification", TestADEModule.v1_0.getNamespaceURI())) {
					String id = rs.getString(5);
					String name = rs.getString(4);
					
					if (id != null && name != null) {
						EnergyPerformanceCertification energyCertification = new EnergyPerformanceCertification();
						energyCertification.setCertificationId(id);
						for (SplitValue splitValue : valueSplitter.split(name))
							energyCertification.addCertificationName(splitValue.result(0));

						EnergyPerformanceCertificationProperty property = new EnergyPerformanceCertificationProperty(energyCertification);
						EnergyPerformanceCertificationPropertyElement propertyElement = new EnergyPerformanceCertificationPropertyElement(property);

						parent.addGenericApplicationPropertyOfAbstractBuilding(propertyElement);
					}
				}

				if (projectionFilter.containsProperty("buildingUnit", TestADEModule.v1_0.getNamespaceURI())
						&& lodFilter.containsLodGreaterThanOrEuqalTo(1))
					buildingUnitExporter.doExport(parent, parentId);
			}
		}
	}

	@Override
	public void close() throws CityGMLExportException, SQLException {
		ps.close();
	}

}
