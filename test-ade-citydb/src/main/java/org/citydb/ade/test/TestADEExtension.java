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

package org.citydb.ade.test;

import org.citydb.ade.test.balloon.BalloonManager;
import org.citydb.ade.test.exporter.ExportManager;
import org.citydb.ade.test.importer.ImportManager;
import org.citydb.ade.test.schema.ADETableMapper;
import org.citydb.ade.test.schema.ObjectMapper;
import org.citydb.ade.test.schema.SchemaMapper;
import org.citydb.ade.test.visExporter.VisExportManager;
import org.citydb.core.ade.ADEExtension;
import org.citydb.core.ade.ADEExtensionException;
import org.citydb.core.ade.ADEObjectMapper;
import org.citydb.core.ade.exporter.ADEExportManager;
import org.citydb.core.ade.importer.ADEImportManager;
import org.citydb.core.ade.visExporter.ADEBalloonManager;
import org.citydb.core.ade.visExporter.ADEVisExportExtension;
import org.citydb.core.ade.visExporter.ADEVisExportManager;
import org.citydb.core.database.schema.mapping.SchemaMapping;
import org.citydb.gui.ImpExpLauncher;
import org.citygml.ade.test.TestADEContext;
import org.citygml4j.model.citygml.ade.binding.ADEContext;

import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

public class TestADEExtension extends ADEExtension implements ADEVisExportExtension {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final SchemaMapper schemaMapper = new SchemaMapper();
    private final ADETableMapper tableMapper = new ADETableMapper();
    private final TestADEContext context = new TestADEContext();

    public static void main(String[] args) {
        TestADEExtension adeExtension = new TestADEExtension();
        adeExtension.setBasePath(Paths.get("resources").toAbsolutePath());
        new ImpExpLauncher().withArgs(args)
                .withADEExtension(adeExtension)
                .start();
    }

    @Override
    public void init(SchemaMapping schemaMapping) throws ADEExtensionException {
        objectMapper.populateObjectClassIds(schemaMapping);
        schemaMapper.populateSchemaNames(schemaMapping.getMetadata().getDBPrefix().toLowerCase());
        tableMapper.populateTableColumns(schemaMapper);
    }

    @Override
    public List<ADEContext> getADEContexts() {
        return Collections.singletonList(context);
    }

    @Override
    public ADEObjectMapper getADEObjectMapper() {
        return objectMapper;
    }

    @Override
    public ADEImportManager createADEImportManager() {
        return new ImportManager(this, schemaMapper);
    }

    @Override
    public ADEExportManager createADEExportManager() {
        return new ExportManager(schemaMapper);
    }

    public SchemaMapper getSchemaMapper() {
        return schemaMapper;
    }

    @Override
    public ADEVisExportManager createADEVisExportManager() {
        return new VisExportManager(schemaMapper);
    }

    @Override
    public ADEBalloonManager createBalloonManager() {
        return new BalloonManager(tableMapper, schemaMapper);
    }

}
