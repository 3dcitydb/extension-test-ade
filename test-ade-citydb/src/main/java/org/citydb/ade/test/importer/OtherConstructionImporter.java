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

import org.citydb.ade.test.schema.ADETable;
import org.citydb.ade.test.schema.SchemaMapper;
import org.citydb.core.ade.importer.ADEImporter;
import org.citydb.core.ade.importer.CityGMLImportHelper;
import org.citydb.core.database.schema.mapping.AbstractObjectType;
import org.citydb.core.operation.importer.CityGMLImportException;
import org.citygml.ade.test.model.OtherConstruction;
import org.citygml4j.model.citygml.building.AbstractBoundarySurface;
import org.citygml4j.model.citygml.building.BoundarySurfaceProperty;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OtherConstructionImporter implements ADEImporter {
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final OtherConstructionToThematicSurfaceImporter boundarySurfaceImporter;
    private final PreparedStatement ps;

    private int batchCounter;

    public OtherConstructionImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.OTHERCONSTRUCTION)) + " " +
                "(id) " +
                "values (?)");

        boundarySurfaceImporter = manager.getImporter(OtherConstructionToThematicSurfaceImporter.class);
    }

    public void doImport(OtherConstruction otherConstruction, long objectId, AbstractObjectType<?> objectType) throws CityGMLImportException, SQLException {
        ps.setLong(1, objectId);

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(objectType);

        if (otherConstruction.isSetBoundedBySurface()) {
            for (BoundarySurfaceProperty property : otherConstruction.getBoundedBySurface()) {
                AbstractBoundarySurface boundarySurface = property.getBoundarySurface();

                if (boundarySurface != null) {
                    long boundarySurfaceId = helper.importObject(boundarySurface);
                    boundarySurfaceImporter.doImport(objectId, boundarySurfaceId);
                } else {
                    String href = property.getHref();
                    if (href != null && href.length() != 0)
                        helper.propagateObjectXlink(schemaMapper.getTableName(ADETable.OTHER_TO_THEMA_SURFA), objectId, "otherconstruction_id", href, "thematic_surface_id");
                }
            }
        }
    }

    @Override
    public void executeBatch() throws CityGMLImportException, SQLException {
        if (batchCounter > 0) {
            ps.executeBatch();
            batchCounter = 0;
        }
    }

    @Override
    public void close() throws SQLException {
        ps.close();
    }
}
