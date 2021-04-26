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

import org.citydb.ade.kmlExporter.ADEKmlExportHelper;
import org.citydb.ade.kmlExporter.ADEKmlExporter;

public class OtherConstructionKmlExporter implements ADEKmlExporter {
	private ADEKmlExportHelper helper;
	private final String schema;

	public OtherConstructionKmlExporter(ADEKmlExportHelper helper) {
		this.helper = helper;
		this.schema = helper.getDatabaseAdapter().getConnectionDetails().getSchema();
	}

	@Override
	public String getPointAndCurveQuery(int lod) {
		return null;
	}

	@Override
	public String getSurfaceGeometryQuery(int lod) {
		return new StringBuilder("select sg.geometry, ")
				.append(helper.getSQLQueryHelper().getImplicitGeometryNullColumns())
				.append("from ").append(schema).append(".surface_geometry sg ")
				.append("where sg.root_id in (")
				.append("select ts.lod").append(lod).append("_multi_surface_id from thematic_surface ts ")
				.append("JOIN ").append(schema).append(".test_other_to_thema_surfa to2ts on to2ts.thematic_surface_id=ts.id ")
				.append("JOIN ").append(schema).append(".test_otherconstruction tos on tos.id=to2ts.otherconstruction_id ")
				.append("WHERE tos.id=?) and sg.geometry is not null").toString();
	}

	@Override
	public String getSurfaceGeometryRefIdsQuery(int lod) {
		return new StringBuilder("select ts.lod").append(lod).append("_multi_surface_id, ts.objectclass_id from thematic_surface ts ")
				.append("JOIN ").append(schema).append(".test_other_to_thema_surfa to2ts on to2ts.thematic_surface_id=ts.id ")
				.append("JOIN ").append(schema).append(".test_otherconstruction tos on tos.id=to2ts.otherconstruction_id ")
				.append("WHERE tos.id=?").toString();
	}

}
