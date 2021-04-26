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

package org.citydb.ade.test.kmlExporter;

import org.citydb.ade.kmlExporter.ADEKmlExportException;
import org.citydb.ade.kmlExporter.ADEKmlExportHelper;
import org.citydb.ade.kmlExporter.ADEKmlExportManager;
import org.citydb.ade.kmlExporter.ADEKmlExporter;
import org.citydb.ade.test.schema.SchemaMapper;
import org.citydb.util.Util;
import org.citygml.ade.test.model.IndustrialBuilding;
import org.citygml.ade.test.model.OtherConstruction;
import org.citygml4j.model.citygml.building.Building;
import org.citygml4j.model.gml.base.AbstractGML;
import org.citygml4j.model.module.citygml.CityGMLVersion;

import java.util.HashMap;
import java.util.Map;

public class KMLExportManager implements ADEKmlExportManager {
	private final SchemaMapper schemaMapper;
	private final Map<Class<? extends ADEKmlExporter>, ADEKmlExporter> exporters;
	private ADEKmlExportHelper helper;

	public KMLExportManager(SchemaMapper schemaMapper) {
		this.schemaMapper = schemaMapper;
		exporters = new HashMap<>();
	}

	@Override
	public void init(ADEKmlExportHelper helper) {
		this.helper = helper;
	}

	@Override
	public ADEKmlExporter getKmlExporter(int objectClassId) throws ADEKmlExportException {
		AbstractGML modelObject = Util.createObject(objectClassId, CityGMLVersion.v2_0_0);
		ADEKmlExporter exporter = null;

		if (modelObject instanceof Building) {
			exporter = getKmlExporter(BuildingKmlExporter.class);
		} else if (modelObject instanceof OtherConstruction) {
			exporter = getKmlExporter(OtherConstructionKmlExporter.class);
		} else if (modelObject instanceof IndustrialBuilding) {
			exporter = getKmlExporter(IndustrialBuildingKmlExporter.class);
		}

		return exporter;
	}

	public SchemaMapper getSchemaMapper() {
		return schemaMapper;
	}

	private <T extends ADEKmlExporter> T getKmlExporter(Class<T> type) throws ADEKmlExportException {
		ADEKmlExporter exporter = exporters.get(type);

		if (exporter == null) {
			if (type == BuildingKmlExporter.class) {
				exporter = new BuildingKmlExporter(helper);
			} else if (type == OtherConstructionKmlExporter.class) {
				exporter = new OtherConstructionKmlExporter(helper);
			} else if (type == IndustrialBuildingKmlExporter.class) {
				exporter = new IndustrialBuildingKmlExporter(helper);
			}

			if (exporter == null)
				throw new ADEKmlExportException("Failed to build ADE KML exporter of type " + type.getName() + ".");

			exporters.put(type, exporter);
		}

		return type.cast(exporter);
	}
}
