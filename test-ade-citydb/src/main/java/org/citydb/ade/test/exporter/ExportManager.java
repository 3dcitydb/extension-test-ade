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
import org.citydb.ade.test.schema.SchemaMapper;
import org.citydb.core.ade.exporter.ADEExportManager;
import org.citydb.core.ade.exporter.ADEExporter;
import org.citydb.core.ade.exporter.CityGMLExportHelper;
import org.citydb.core.database.schema.mapping.AbstractObjectType;
import org.citydb.core.database.schema.mapping.FeatureType;
import org.citydb.core.operation.exporter.CityGMLExportException;
import org.citydb.core.query.filter.projection.ProjectionFilter;
import org.citygml.ade.test.model.IndustrialBuilding;
import org.citygml.ade.test.model.IndustrialBuildingPart;
import org.citygml.ade.test.model.IndustrialBuildingRoofSurface;
import org.citygml.ade.test.model.OtherConstruction;
import org.citygml4j.model.citygml.ade.binding.ADEModelObject;
import org.citygml4j.model.citygml.building.AbstractBuilding;
import org.citygml4j.model.gml.feature.AbstractFeature;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ExportManager implements ADEExportManager {
	private final Map<Class<? extends ADEExporter>, ADEExporter> exporters;
	private final SchemaMapper schemaMapper;
	
	private Connection connection;
	private CityGMLExportHelper helper;
	
	public ExportManager(SchemaMapper schemaMapper) {
		this.schemaMapper = schemaMapper;
		exporters = new HashMap<>();
	}
	
	@Override
	public void init(Connection connection, CityGMLExportHelper helper) throws CityGMLExportException, SQLException {
		this.connection = connection;
		this.helper = helper;
	}

	@Override
	public void exportObject(ADEModelObject object, long objectId, AbstractObjectType<?> objectType, ProjectionFilter projectionFilter) throws CityGMLExportException, SQLException {
		if (object instanceof IndustrialBuilding)
			getExporter(IndustrialBuildingExporter.class).doExport((IndustrialBuilding)object, objectId, objectType, projectionFilter);
		else if (object instanceof IndustrialBuildingPart)
			getExporter(IndustrialBuildingPartExporter.class).doExport((IndustrialBuildingPart)object, objectId, objectType, projectionFilter);
		else if (object instanceof IndustrialBuildingRoofSurface)
			getExporter(IndustrialBuildingRoofSurfaceExporter.class).doExport((IndustrialBuildingRoofSurface)object, objectId, objectType, projectionFilter);
		else if (object instanceof OtherConstruction)
			getExporter(OtherConstructionExporter.class).doExport((OtherConstruction)object, objectId, objectType, projectionFilter);
	}
	
	@Override
	public void exportGenericApplicationProperties(String adeHookTable, AbstractFeature parent, long parentId, FeatureType parentType, ProjectionFilter projectionFilter) throws CityGMLExportException, SQLException {
		if (adeHookTable.equals(schemaMapper.getTableName(ADETable.BUILDING)) && parent instanceof AbstractBuilding)
			getExporter(BuildingPropertiesExporter.class).doExport((AbstractBuilding)parent, parentId, parentType, projectionFilter);
	}

	@Override
	public void close() throws CityGMLExportException, SQLException {
		for (ADEExporter exporter : exporters.values())
			exporter.close();
	}
	
	protected SchemaMapper getSchemaMapper() {
		return schemaMapper;
	}

	protected <T extends ADEExporter> T getExporter(Class<T> type) throws CityGMLExportException, SQLException {
		ADEExporter exporter = exporters.get(type);
		
		if (exporter == null) {
			if (type == IndustrialBuildingExporter.class)
				exporter = new IndustrialBuildingExporter(connection, helper, this);
			else if (type == IndustrialBuildingPartExporter.class)
				exporter = new IndustrialBuildingPartExporter(connection, helper, this);
			else if (type == IndustrialBuildingRoofSurfaceExporter.class)
				exporter = new IndustrialBuildingRoofSurfaceExporter(connection, helper, this);
			else if (type == BuildingUnitExporter.class)
				exporter = new BuildingUnitExporter(connection, helper, this);
			else if (type == FacilitiesExporter.class)
				exporter = new FacilitiesExporter(connection, helper, this);
			else if (type == EnergyPerformanceCertificationExporter.class)
				exporter = new EnergyPerformanceCertificationExporter(connection, helper, this);
			else if (type == OtherConstructionExporter.class)
				exporter = new OtherConstructionExporter(helper);
			else if (type == BuildingPropertiesExporter.class)
				exporter = new BuildingPropertiesExporter(connection, helper, this);

			if (exporter == null)
				throw new SQLException("Failed to build ADE exporter of type " + type.getName() + ".");
			
			exporters.put(type, exporter);
		}
		
		return type.cast(exporter);
	}

}
