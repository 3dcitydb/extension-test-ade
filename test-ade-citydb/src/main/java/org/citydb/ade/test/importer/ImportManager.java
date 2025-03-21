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
import org.citydb.core.ade.ADEExtension;
import org.citydb.core.ade.importer.*;
import org.citydb.core.database.schema.mapping.AbstractObjectType;
import org.citydb.core.database.schema.mapping.FeatureType;
import org.citydb.core.operation.importer.CityGMLImportException;
import org.citygml.ade.test.model.*;
import org.citygml4j.model.citygml.ade.binding.ADEModelObject;
import org.citygml4j.model.citygml.building.AbstractBuilding;
import org.citygml4j.model.gml.feature.AbstractFeature;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ImportManager implements ADEImportManager {
    private final ADEExtension adeExtension;
    private final Map<Class<? extends ADEImporter>, ADEImporter> importers;
    private final SchemaMapper schemaMapper;

    private Connection connection;
    private CityGMLImportHelper helper;

    public ImportManager(ADEExtension adeExtension, SchemaMapper schemaMapper) {
        this.adeExtension = adeExtension;
        this.schemaMapper = schemaMapper;
        importers = new HashMap<>();
    }

    @Override
    public void init(Connection connection, CityGMLImportHelper helper) throws CityGMLImportException, SQLException {
        this.connection = connection;
        this.helper = helper;
    }

    @Override
    public void importObject(ADEModelObject object, long objectId, AbstractObjectType<?> objectType, ForeignKeys foreignKeys) throws CityGMLImportException, SQLException {
        if (object instanceof IndustrialBuilding)
            getImporter(IndustrialBuildingImporter.class).doImport((IndustrialBuilding) object, objectId, objectType, foreignKeys);
        else if (object instanceof IndustrialBuildingPart)
            getImporter(IndustrialBuildingPartImporter.class).doImport((IndustrialBuildingPart) object, objectId, objectType, foreignKeys);
        else if (object instanceof IndustrialBuildingRoofSurface)
            getImporter(IndustrialBuildingRoofSurfaceImporter.class).doImport((IndustrialBuildingRoofSurface) object, objectId, objectType, foreignKeys);
        else if (object instanceof AbstractBuildingUnit)
            getImporter(BuildingUnitImporter.class).doImport((AbstractBuildingUnit) object, objectId, objectType, foreignKeys);
        else if (object instanceof AbstractFacilities)
            getImporter(FacilitiesImporter.class).doImport((AbstractFacilities) object, objectId, objectType, foreignKeys);
        else if (object instanceof OtherConstruction)
            getImporter(OtherConstructionImporter.class).doImport((OtherConstruction) object, objectId, objectType);
    }

    @Override
    public void importGenericApplicationProperties(ADEPropertyCollection properties, AbstractFeature parent, long parentId, FeatureType parentType) throws CityGMLImportException, SQLException {
        if (parent instanceof AbstractBuilding)
            getImporter(BuildingPropertiesImporter.class).doImport(properties, (AbstractBuilding) parent, parentId, parentType);
    }

    @Override
    public void executeBatch(String tableName) throws CityGMLImportException, SQLException {
        ADETable adeTable = schemaMapper.fromTableName(tableName);
        if (adeTable != null) {
            ADEImporter importer = importers.get(adeTable.getImporterClass());
            if (importer != null)
                importer.executeBatch();
        } else
            throw new CityGMLImportException("The table " + tableName + " is not managed by the ADE extension for '" + adeExtension.getMetadata().getIdentifier() + "'.");
    }

    @Override
    public void close() throws CityGMLImportException, SQLException {
        for (ADEImporter importer : importers.values())
            importer.close();
    }

    protected SchemaMapper getSchemaMapper() {
        return schemaMapper;
    }

    protected <T extends ADEImporter> T getImporter(Class<T> type) throws CityGMLImportException, SQLException {
        ADEImporter importer = importers.get(type);

        if (importer == null) {
            if (type == IndustrialBuildingImporter.class)
                importer = new IndustrialBuildingImporter(connection, helper, this);
            else if (type == IndustrialBuildingPartImporter.class)
                importer = new IndustrialBuildingPartImporter(connection, helper, this);
            else if (type == IndustrialBuildingRoofSurfaceImporter.class)
                importer = new IndustrialBuildingRoofSurfaceImporter(connection, helper, this);
            else if (type == BuildingUnitImporter.class)
                importer = new BuildingUnitImporter(connection, helper, this);
            else if (type == FacilitiesImporter.class)
                importer = new FacilitiesImporter(connection, helper, this);
            else if (type == EnergyPerformanceCertificationImporter.class)
                importer = new EnergyPerformanceCertificationImporter(connection, helper, this);
            else if (type == OtherConstructionImporter.class)
                importer = new OtherConstructionImporter(connection, helper, this);
            else if (type == BuildingPropertiesImporter.class)
                importer = new BuildingPropertiesImporter(connection, helper, this);
            else if (type == BuildingUnitToAddressImporter.class)
                importer = new BuildingUnitToAddressImporter(connection, helper, this);
            else if (type == OtherConstructionToThematicSurfaceImporter.class)
                importer = new OtherConstructionToThematicSurfaceImporter(connection, helper, this);

            if (importer == null)
                throw new SQLException("Failed to build ADE importer of type " + type.getName() + ".");

            importers.put(type, importer);
        }

        return type.cast(importer);
    }

}
