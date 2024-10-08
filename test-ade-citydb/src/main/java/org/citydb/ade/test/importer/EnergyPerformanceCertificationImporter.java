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

import org.citydb.ade.test.schema.ADESequence;
import org.citydb.ade.test.schema.ADETable;
import org.citydb.ade.test.schema.SchemaMapper;
import org.citydb.core.ade.importer.ADEImporter;
import org.citydb.core.ade.importer.CityGMLImportHelper;
import org.citydb.core.operation.importer.CityGMLImportException;
import org.citydb.core.operation.importer.util.AttributeValueJoiner;
import org.citygml.ade.test.model.EnergyPerformanceCertification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

public class EnergyPerformanceCertificationImporter implements ADEImporter {
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final AttributeValueJoiner valueJoiner;
    private final PreparedStatement ps;

    private int batchCounter;

    public EnergyPerformanceCertificationImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.ENERGYPERFORMANCECER)) + " " +
                "(id, buildingunit_energyperfor_id, certificationname, certificationid) " +
                "values (?, ?, ?, ?)");

        valueJoiner = helper.getAttributeValueJoiner();
    }

    public void doImport(EnergyPerformanceCertification energyCertification, long parentId) throws CityGMLImportException, SQLException {
        long objectId = helper.getNextSequenceValue(schemaMapper.getSequenceName(ADESequence.ENERGYPERFORMANC_SEQ));
        ps.setLong(1, objectId);

        if (parentId != 0)
            ps.setLong(2, parentId);
        else
            ps.setNull(2, Types.NULL);

        if (energyCertification.isSetCertificationName())
            ps.setString(3, valueJoiner.join(energyCertification.getCertificationName()));
        else
            ps.setNull(3, Types.VARCHAR);

        ps.setString(4, energyCertification.getCertificationId());

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(schemaMapper.getTableName(ADETable.ENERGYPERFORMANCECER));
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
