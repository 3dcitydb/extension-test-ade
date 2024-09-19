/*
 * 3D City Database - The Open Source CityGML Database
 * https://www.3dcitydb.org/
 *
 * Copyright 2013 - 2024
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
import org.citydb.core.database.schema.mapping.AbstractType;
import org.citydb.core.operation.exporter.CityGMLExportException;
import org.citydb.core.query.filter.projection.ProjectionFilter;
import org.citygml.ade.test.model.IndustrialBuildingRoofSurface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class IndustrialBuildingRoofSurfaceExporter implements ADEExporter {
    private final PreparedStatement ps;

    public IndustrialBuildingRoofSurfaceExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws SQLException {
        ps = connection.prepareStatement("select remark from " +
                helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.INDUSTRIALBUILDINGRO)) + " " +
                "where id = ?");
    }

    public void doExport(IndustrialBuildingRoofSurface roofSurface, long objectId, AbstractType<?> objectType, ProjectionFilter projectionFilter) throws CityGMLExportException, SQLException {
        // we only have one attribute, so only issue a query if this attribute
        // shall be exported
        if (projectionFilter.containsProperty("remark")) {
            ps.setLong(1, objectId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    roofSurface.setRemark(rs.getString(1));
                }
            }
        }
    }

    @Override
    public void close() throws CityGMLExportException, SQLException {
        ps.close();
    }

}
