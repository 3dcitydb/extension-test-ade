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

package org.citydb.ade.test.importer;

import org.citydb.ade.test.schema.ADETable;
import org.citydb.ade.test.schema.SchemaMapper;
import org.citydb.config.geometry.GeometryObject;
import org.citydb.core.ade.importer.ADEImporter;
import org.citydb.core.ade.importer.CityGMLImportHelper;
import org.citydb.core.ade.importer.ForeignKeys;
import org.citydb.core.database.schema.mapping.AbstractObjectType;
import org.citydb.core.operation.importer.CityGMLImportException;
import org.citydb.core.operation.importer.util.AttributeValueJoiner;
import org.citygml.ade.test.model.*;
import org.citygml4j.model.citygml.core.Address;
import org.citygml4j.model.citygml.core.AddressProperty;
import org.citygml4j.model.gml.basicTypes.Code;
import org.citygml4j.model.gml.geometry.aggregates.MultiCurveProperty;
import org.citygml4j.model.gml.geometry.aggregates.MultiSurfaceProperty;
import org.citygml4j.model.gml.geometry.primitives.SolidProperty;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

public class BuildingUnitImporter implements ADEImporter {
    private final Connection connection;
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final EnergyPerformanceCertificationImporter energyImporter;
    private final BuildingUnitToAddressImporter addressImporter;
    private final AttributeValueJoiner valueJoiner;
    private final PreparedStatement ps;

    private int batchCounter;

    public BuildingUnitImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.connection = connection;
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.BUILDINGUNIT)) + " " +
                "(id, objectclass_id, building_buildingunit_id, buildingunit_parent_id, buildingunit_root_id, class, " +
                "class_codespace, usage, usage_codespace, function, function_codespace, " +
                "lod2multicurve, lod3multicurve, lod4multicurve, " +
                "lod1multisurface_id, lod2multisurface_id, lod3multisurface_id, lod4multisurface_id, " +
                "lod1solid_id, lod2solid_id, lod3solid_id, lod4solid_id) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

        energyImporter = manager.getImporter(EnergyPerformanceCertificationImporter.class);
        addressImporter = manager.getImporter(BuildingUnitToAddressImporter.class);
        valueJoiner = helper.getAttributeValueJoiner();
    }

    public void doImport(AbstractBuildingUnit buildingUnit, long objectId, AbstractObjectType<?> objectType, ForeignKeys foreignKeys) throws CityGMLImportException, SQLException {
        long parentId = foreignKeys.get("parentId");
        long rootId = foreignKeys.get("rootId");
        long buildingId = foreignKeys.get("buildingId");

        ps.setLong(1, objectId);
        if (rootId == 0)
            rootId = objectId;

        ps.setLong(2, objectType.getObjectClassId());

        if (buildingId != 0)
            ps.setLong(3, buildingId);
        else
            ps.setNull(3, Types.NULL);

        if (parentId != 0)
            ps.setLong(4, parentId);
        else
            ps.setNull(4, Types.NULL);

        ps.setLong(5, rootId);

        if (buildingUnit.isSetClazz() && buildingUnit.getClazz().isSetValue()) {
            ps.setString(6, buildingUnit.getClazz().getValue());
            ps.setString(7, buildingUnit.getClazz().getCodeSpace());
        } else {
            ps.setNull(6, Types.VARCHAR);
            ps.setNull(7, Types.VARCHAR);
        }

        if (buildingUnit.isSetUsage()) {
            valueJoiner.join(buildingUnit.getUsage(), Code::getValue, Code::getCodeSpace);
            ps.setString(8, valueJoiner.result(0));
            ps.setString(9, valueJoiner.result(1));
        } else {
            ps.setNull(8, Types.VARCHAR);
            ps.setNull(9, Types.VARCHAR);
        }

        if (buildingUnit.isSetFunction()) {
            valueJoiner.join(buildingUnit.getFunction(), Code::getValue, Code::getCodeSpace);
            ps.setString(10, valueJoiner.result(0));
            ps.setString(11, valueJoiner.result(1));
        } else {
            ps.setNull(10, Types.VARCHAR);
            ps.setNull(11, Types.VARCHAR);
        }

        for (int i = 0; i < 3; i++) {
            MultiCurveProperty multiCurveProperty = null;
            GeometryObject multiLine = null;

            switch (i) {
                case 0:
                    multiCurveProperty = buildingUnit.getLod2MultiCurve();
                    break;
                case 1:
                    multiCurveProperty = buildingUnit.getLod3MultiCurve();
                    break;
                case 2:
                    multiCurveProperty = buildingUnit.getLod4MultiCurve();
                    break;
            }

            if (multiCurveProperty != null) {
                multiLine = helper.getGeometryConverter().getMultiCurve(multiCurveProperty);
                multiCurveProperty.unsetMultiCurve();
            }

            if (multiLine != null) {
                Object multiLineObj = helper.getDatabaseAdapter().getGeometryConverter().getDatabaseObject(multiLine, connection);
                ps.setObject(12 + i, multiLineObj);
            } else
                ps.setNull(12 + i,
                        helper.getDatabaseAdapter().getGeometryConverter().getNullGeometryType(),
                        helper.getDatabaseAdapter().getGeometryConverter().getNullGeometryTypeName());
        }

        for (int i = 0; i < 4; i++) {
            MultiSurfaceProperty multiSurfaceProperty = null;
            long multiSurfaceId = 0;

            switch (i) {
                case 0:
                    multiSurfaceProperty = buildingUnit.getLod1MultiSurface();
                    break;
                case 1:
                    multiSurfaceProperty = buildingUnit.getLod2MultiSurface();
                    break;
                case 2:
                    multiSurfaceProperty = buildingUnit.getLod3MultiSurface();
                    break;
                case 3:
                    multiSurfaceProperty = buildingUnit.getLod4MultiSurface();
                    break;
            }

            if (multiSurfaceProperty != null) {
                if (multiSurfaceProperty.isSetMultiSurface()) {
                    multiSurfaceId = helper.importSurfaceGeometry(multiSurfaceProperty.getMultiSurface(), objectId);
                    multiSurfaceProperty.unsetMultiSurface();
                } else {
                    String href = multiSurfaceProperty.getHref();
                    if (href != null && href.length() != 0)
                        helper.propagateSurfaceGeometryXlink(href, objectType.getTable(), objectId, "lod" + (i + 1) + "multisurface_id");
                }
            }

            if (multiSurfaceId != 0)
                ps.setLong(15 + i, multiSurfaceId);
            else
                ps.setNull(15 + i, Types.NULL);
        }

        for (int i = 0; i < 4; i++) {
            SolidProperty solidProperty = null;
            long solidGeometryId = 0;

            switch (i) {
                case 0:
                    solidProperty = buildingUnit.getLod1Solid();
                    break;
                case 1:
                    solidProperty = buildingUnit.getLod2Solid();
                    break;
                case 2:
                    solidProperty = buildingUnit.getLod3Solid();
                    break;
                case 3:
                    solidProperty = buildingUnit.getLod4Solid();
                    break;
            }

            if (solidProperty != null) {
                if (solidProperty.isSetSolid()) {
                    solidGeometryId = helper.importSurfaceGeometry(solidProperty.getSolid(), objectId);
                    solidProperty.unsetSolid();
                } else {
                    String href = solidProperty.getHref();
                    if (href != null && href.length() != 0)
                        helper.propagateSurfaceGeometryXlink(href, objectType.getTable(), objectId, "lod" + (i + 1) + "solid_id");
                }
            }

            if (solidGeometryId != 0)
                ps.setLong(19 + i, solidGeometryId);
            else
                ps.setNull(19 + i, Types.NULL);
        }

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(objectType);

        if (buildingUnit.isSetEnergyPerformanceCertification()) {
            for (EnergyPerformanceCertificationProperty property : buildingUnit.getEnergyPerformanceCertification()) {
                if (property.isSetEnergyPerformanceCertification()) {
                    energyImporter.doImport(property.getEnergyPerformanceCertification(), objectId);
                    property.unsetEnergyPerformanceCertification();
                }
            }
        }

        if (buildingUnit.isSetAddress()) {
            for (AddressProperty property : buildingUnit.getAddress()) {
                Address address = property.getAddress();

                if (address != null) {
                    long addressId = helper.importObject(address);
                    addressImporter.doImport(objectId, addressId);
                    property.unsetAddress();
                } else {
                    String href = property.getHref();
                    if (href != null && href.length() != 0)
                        helper.propagateObjectXlink(schemaMapper.getTableName(ADETable.BUILDINGU_TO_ADDRESS), objectId, "buildingunit_id", href, "address_id");
                }
            }
        }

        if (buildingUnit.isSetEquippedWith()) {
            for (FacilitiesProperty property : buildingUnit.getEquippedWith()) {
                AbstractFacilities facilities = property.getFacilities();
                if (facilities != null) {
                    helper.importObject(facilities, ForeignKeys.create().with("parentId", objectId));
                    property.unsetFacilities();
                } else {
                    String href = property.getHref();
                    if (href != null && href.length() != 0)
                        helper.logOrThrowUnsupportedXLinkMessage(buildingUnit, AbstractFacilities.class, href);
                }
            }
        }

        if (buildingUnit.isSetConsistsOf()) {
            for (BuildingUnitPartProperty property : buildingUnit.getConsistsOf()) {
                BuildingUnitPart buildingUnitPart = property.getBuildingUnitPart();
                if (buildingUnitPart != null) {
                    helper.importObject(buildingUnitPart, ForeignKeys.create()
                            .with("parentId", objectId)
                            .with("rootId", rootId)
                            .with("buildingId", buildingId));
                    property.unsetBuildingUnitPart();
                } else {
                    String href = property.getHref();
                    if (href != null && href.length() != 0)
                        helper.logOrThrowUnsupportedXLinkMessage(buildingUnit, BuildingUnitPart.class, href);
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
