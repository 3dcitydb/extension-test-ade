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

package org.citydb.ade.test.visExporter;

import org.citydb.config.project.visExporter.DisplayForm;
import org.citydb.config.project.visExporter.DisplayFormType;
import org.citydb.config.project.visExporter.Lod0FootprintMode;
import org.citydb.core.ade.visExporter.ADEVisExportHelper;
import org.citydb.core.ade.visExporter.ADEVisExporter;
import org.citydb.core.util.Util;
import org.citygml4j.model.citygml.building.Building;

public class IndustrialBuildingVisExporter implements ADEVisExporter {
    private ADEVisExportHelper helper;

    public IndustrialBuildingVisExporter(ADEVisExportHelper helper) {
        this.helper = helper;
    }

    @Override
    public String getPointAndCurveQuery(int lod) {
        return null;
    }

    @Override
    public String getSurfaceGeometryQuery(int lod) {
        DisplayForm displayForm = DisplayForm.of(DisplayFormType.FOOTPRINT);
        return helper.getSQLQueryHelper().getBuildingPartQuery(lod, Lod0FootprintMode.ROOFPRINT_PRIOR_FOOTPRINT, displayForm, false, Util.getObjectClassId(Building.class));
    }

    @Override
    public String getSurfaceGeometryRefIdsQuery(int lod) {
        DisplayForm displayForm = DisplayForm.of(DisplayFormType.GEOMETRY);
        return helper.getSQLQueryHelper().getBuildingPartQuery(lod, Lod0FootprintMode.ROOFPRINT_PRIOR_FOOTPRINT, displayForm, false, Util.getObjectClassId(Building.class));
    }
}
