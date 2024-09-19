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
import org.citydb.core.ade.importer.ADEPropertyCollection;
import org.citydb.core.ade.importer.CityGMLImportHelper;
import org.citydb.core.ade.importer.ForeignKeys;
import org.citydb.core.database.schema.mapping.FeatureType;
import org.citydb.core.operation.importer.CityGMLImportException;
import org.citydb.core.operation.importer.util.AttributeValueJoiner;
import org.citygml.ade.test.model.*;
import org.citygml4j.model.citygml.building.AbstractBuilding;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

public class BuildingPropertiesImporter implements ADEImporter {
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final AttributeValueJoiner valueJoiner;
    private final PreparedStatement ps;

    private int batchCounter;

    public BuildingPropertiesImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.BUILDING)) + " " +
                "(id, ownername, energyperforma_certification, energyperform_certificatio_1, floorarea, floorarea_uom) " +
                "values (?, ?, ?, ?, ?, ?)");

        valueJoiner = helper.getAttributeValueJoiner();
    }

    public void doImport(ADEPropertyCollection properties, AbstractBuilding parent, long parentId, FeatureType parentType) throws CityGMLImportException, SQLException {
        ps.setLong(1, parentId);

        OwnerNameProperty ownerName = properties.getFirst(OwnerNameProperty.class);
        if (ownerName != null && ownerName.isSetValue())
            ps.setString(2, ownerName.getValue());
        else
            ps.setNull(2, Types.VARCHAR);

        EnergyPerformanceCertificationPropertyElement energyCertificationElement = properties.getFirst(EnergyPerformanceCertificationPropertyElement.class);
        if (energyCertificationElement != null
                && energyCertificationElement.getValue().isSetEnergyPerformanceCertification()) {
            EnergyPerformanceCertification energyCertification = energyCertificationElement.getValue().getEnergyPerformanceCertification();
            ps.setString(3, valueJoiner.join(energyCertification.getCertificationName()));
            ps.setString(4, energyCertification.getCertificationId());
        } else {
            ps.setNull(3, Types.VARCHAR);
            ps.setNull(4, Types.VARCHAR);
        }

        FloorAreaProperty floorArea = properties.getFirst(FloorAreaProperty.class);
        if (floorArea != null && floorArea.getValue().isSetValue()) {
            ps.setDouble(5, floorArea.getValue().getValue());
            ps.setString(6, floorArea.getValue().getUom());
        } else {
            ps.setNull(5, Types.DOUBLE);
            ps.setNull(6, Types.VARCHAR);
        }

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(schemaMapper.getTableName(ADETable.BUILDING));

        if (properties.contains(BuildingUnitPropertyElement.class)) {
            for (BuildingUnitPropertyElement propertyElement : properties.getAll(BuildingUnitPropertyElement.class)) {
                AbstractBuildingUnit buildingUnit = propertyElement.getValue().getBuildingUnit();
                if (buildingUnit != null) {
                    helper.importObject(buildingUnit, ForeignKeys.create().with("buildingId", parentId));
                    propertyElement.getValue().unsetBuildingUnit();
                } else {
                    String href = propertyElement.getValue().getHref();
                    if (href != null && href.length() != 0)
                        helper.logOrThrowUnsupportedXLinkMessage(parent, AbstractBuildingUnit.class, href);
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
    public void close() throws CityGMLImportException, SQLException {
        ps.close();
    }

}
