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

import org.citydb.ade.test.schema.ADETableMapper;
import org.citydb.ade.test.schema.SchemaMapper;
import org.citydb.core.ade.visExporter.ADEBalloonException;
import org.citydb.core.ade.visExporter.ADEBalloonHandler;
import org.citydb.core.ade.visExporter.ADEBalloonManager;
import org.citydb.core.util.Util;
import org.citygml.ade.test.model.IndustrialBuilding;
import org.citygml.ade.test.model.OtherConstruction;
import org.citygml4j.model.citygml.building.AbstractBuilding;
import org.citygml4j.model.gml.base.AbstractGML;
import org.citygml4j.model.module.citygml.CityGMLVersion;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class BalloonManager implements ADEBalloonManager {
    private final ADETableMapper tableMapper;
    private final SchemaMapper schemaMapper;
    private final Map<Class<? extends ADEBalloonHandler>, ADEBalloonHandler> balloonHandlers;

    public BalloonManager(ADETableMapper tableMapper, SchemaMapper schemaMapper) {
        this.tableMapper = tableMapper;
        this.schemaMapper = schemaMapper;
        this.balloonHandlers = new HashMap<>();
    }

    @Override
    public ADEBalloonHandler getBalloonHandler(int objectClassId) throws ADEBalloonException {
        AbstractGML modelObject = Util.createObject(objectClassId, CityGMLVersion.v2_0_0);
        ADEBalloonHandler balloonHandler = null;

        if (modelObject instanceof AbstractBuilding) {
            balloonHandler = getBalloonHandler(BuildingBalloonHandler.class);
        } else if (modelObject instanceof IndustrialBuilding) {
            balloonHandler = getBalloonHandler(IndustrialBuildingBalloonHandler.class);
        } else if (modelObject instanceof OtherConstruction) {
            balloonHandler = getBalloonHandler(OtherConstructionBalloonHandler.class);
        }

        return balloonHandler;
    }

    public SchemaMapper getSchemaMapper() {
        return schemaMapper;
    }

    private <T extends ADEBalloonHandler> T getBalloonHandler(Class<T> type) throws ADEBalloonException {
        ADEBalloonHandler balloonHandler = balloonHandlers.get(type);

        if (balloonHandler == null) {
            if (type == BuildingBalloonHandler.class) {
                balloonHandler = new BuildingBalloonHandler();
            } else if (type == IndustrialBuildingBalloonHandler.class) {
                balloonHandler = new IndustrialBuildingBalloonHandler();
            } else if (type == OtherConstructionBalloonHandler.class) {
                balloonHandler = new OtherConstructionBalloonHandler();
            }

            if (balloonHandler == null)
                throw new ADEBalloonException("Failed to build ADE Balloon handler for the type " + type.getName() + ".");

            balloonHandlers.put(type, balloonHandler);
        }

        return type.cast(balloonHandler);
    }

    @Override
    public HashMap<String, Set<String>> getTablesAndColumns() {
        return tableMapper.getTablesAndColumns();
    }

}
