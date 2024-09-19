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

package org.citydb.ade.test.schema;

import org.citydb.core.ade.ADEExtensionException;
import org.citydb.core.ade.ADEObjectMapper;
import org.citydb.core.database.schema.mapping.AbstractObjectType;
import org.citydb.core.database.schema.mapping.SchemaMapping;
import org.citygml.ade.test.model.*;
import org.citygml4j.model.gml.base.AbstractGML;
import org.citygml4j.model.module.citygml.CityGMLVersion;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class ObjectMapper implements ADEObjectMapper {
    private final Map<Class<? extends AbstractGML>, Integer> objectClassIds = new HashMap<>();

    public void populateObjectClassIds(SchemaMapping schemaMapping) throws ADEExtensionException {
        for (AbstractObjectType<?> type : schemaMapping.getAbstractObjectTypes()) {
            int objectClassId = type.getObjectClassId();

            switch (type.getPath()) {
                case "_AbstractBuildingUnit":
                    objectClassIds.put(AbstractBuildingUnit.class, objectClassId);
                    break;
                case "BuildingUnit":
                    objectClassIds.put(BuildingUnit.class, objectClassId);
                    break;
                case "BuildingUnitPart":
                    objectClassIds.put(BuildingUnitPart.class, objectClassId);
                    break;
                case "Facilities":
                    objectClassIds.put(AbstractFacilities.class, objectClassId);
                    break;
                case "DHWFacilities":
                    objectClassIds.put(DHWFacilities.class, objectClassId);
                    break;
                case "LightingFacilities":
                    objectClassIds.put(LightingFacilities.class, objectClassId);
                    break;
                case "IndustrialBuilding":
                    objectClassIds.put(IndustrialBuilding.class, objectClassId);
                    break;
                case "IndustrialBuildingPart":
                    objectClassIds.put(IndustrialBuildingPart.class, objectClassId);
                    break;
                case "IndustrialBuildingRoofSurface":
                    objectClassIds.put(IndustrialBuildingRoofSurface.class, objectClassId);
                    break;
                case "OtherConstruction":
                    objectClassIds.put(OtherConstruction.class, objectClassId);
                    break;
            }
        }
    }

    @Override
    public AbstractGML createObject(int objectClassId, CityGMLVersion version) {
        if (version == CityGMLVersion.v2_0_0) {
            for (Entry<Class<? extends AbstractGML>, Integer> entry : objectClassIds.entrySet()) {
                if (entry.getValue() == objectClassId) {
                    try {
                        return entry.getKey().getDeclaredConstructor().newInstance();
                    } catch (Exception e) {
                        //
                    }
                }
            }
        }

        return null;
    }

    @Override
    public int getObjectClassId(Class<? extends AbstractGML> adeObjectClass) {
        Integer objectClassId = objectClassIds.get(adeObjectClass);
        return objectClassId != null ? objectClassId : 0;
    }

}
