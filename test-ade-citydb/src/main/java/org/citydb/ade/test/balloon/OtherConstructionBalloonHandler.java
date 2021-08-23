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

package org.citydb.ade.test.balloon;

import org.citydb.core.ade.visExporter.ADEBalloonHandler;

public class OtherConstructionBalloonHandler implements ADEBalloonHandler {

	@Override
	public String getSqlStatement(String table,
	                              String tableShortId,
	                              String aggregateColumnsClause,
	                              int lod,
	                              String schemaName) {

		String sqlStatement = null;

		if ("test_otherconstruction".equalsIgnoreCase(table)) {
			sqlStatement = "SELECT " + aggregateColumnsClause +
					" FROM " + schemaName + "." + table + " " + tableShortId +
					" WHERE " + tableShortId + ".id = ?";
		} else if ("test_other_to_thema_surfa".equalsIgnoreCase(table)) {
			sqlStatement = "SELECT " + aggregateColumnsClause +
					" FROM " + schemaName + "." + table + " " + tableShortId +
					" WHERE " + tableShortId + ".otherconstruction_id = ?";
		} else if ("thematic_surface".equalsIgnoreCase(table) || "test_industrialbuildingro".equalsIgnoreCase(table)) {
			sqlStatement = "SELECT " + aggregateColumnsClause +
					" FROM " + schemaName + "." + table + " " + tableShortId + ", " + schemaName + ".test_otherconstruction toc, " + schemaName + ".test_other_to_thema_surfa  to2ts" +
					" WHERE toc.id = ?" +
					" AND toc.id = to2ts.otherconstruction_id" +
					" AND to2ts.thematic_surface_id=" + tableShortId + ".id";
		} else if ("opening".equalsIgnoreCase(table)) {
			sqlStatement = "SELECT " + aggregateColumnsClause +
					" FROM " + schemaName + "." + table + " " + tableShortId + ", " + schemaName + ".opening_to_them_surface o2tsc, " + schemaName + ".test_other_to_thema_surfa  to2ts" +
					" WHERE to2ts.otherconstruction_id = ?" +
					" AND to2ts.thematic_surface_id = o2tsc.thematic_surface_id" +
					" AND o2tsc.opening_id = " + tableShortId + ".id";
		}
		else if ("opening_to_them_surface".equalsIgnoreCase(table)) {
			sqlStatement = "SELECT " + aggregateColumnsClause +
					" FROM " + schemaName + "." + table + " " + tableShortId + ", " + schemaName + ".test_other_to_thema_surfa  to2ts" +
					" WHERE to2ts.otherconstruction_id = ?" +
					" AND to2ts.thematic_surface_id = " + tableShortId + ".thematic_surface_id";
		}

		return sqlStatement;
	}

}
