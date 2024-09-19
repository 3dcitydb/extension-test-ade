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

package org.citygml.ade.test.walker;

import org.citygml.ade.test.model.*;
import org.citygml4j.model.citygml.ade.binding.ADEWalker;
import org.citygml4j.model.citygml.building.AbstractBuilding;
import org.citygml4j.model.citygml.building.BoundarySurfaceProperty;
import org.citygml4j.model.citygml.building.BuildingPart;
import org.citygml4j.model.citygml.building.RoofSurface;
import org.citygml4j.model.citygml.core.AbstractCityObject;
import org.citygml4j.model.citygml.core.AbstractSite;
import org.citygml4j.model.citygml.core.AddressProperty;
import org.citygml4j.model.gml.feature.AbstractFeature;
import org.citygml4j.model.gml.feature.FeatureProperty;
import org.citygml4j.util.walker.FeatureWalker;

import java.util.ArrayList;

public class TestADEFeatureWalker implements ADEWalker<FeatureWalker> {
    private FeatureWalker walker;

    @Override
    public void setParentWalker(FeatureWalker walker) {
        this.walker = walker;
    }

    public void visit(AbstractFacilities abstractFacilities) {
        walker.visit((AbstractFeature) abstractFacilities);
    }

    public void visit(DHWFacilities dhwFacilities) {
        visit((AbstractFacilities) dhwFacilities);
    }

    public void visit(LightingFacilities lightingFacilities) {
        visit((AbstractFacilities) lightingFacilities);
    }

    public void visit(AbstractBuildingUnit abstractBuildingUnit) {
        walker.visit((AbstractCityObject) abstractBuildingUnit);

        if (abstractBuildingUnit.isSetEquippedWith()) {
            for (FacilitiesProperty property : abstractBuildingUnit.getEquippedWith())
                walker.visit((FeatureProperty<?>) property);
        }

        if (abstractBuildingUnit.isSetConsistsOf()) {
            for (BuildingUnitPartProperty property : abstractBuildingUnit.getConsistsOf())
                walker.visit((FeatureProperty<?>) property);
        }

        if (abstractBuildingUnit.isSetAddress()) {
            for (AddressProperty property : new ArrayList<AddressProperty>(abstractBuildingUnit.getAddress()))
                walker.visit((FeatureProperty<?>) property);
        }
    }

    public void visit(BuildingUnit buildingUnit) {
        visit((AbstractBuildingUnit) buildingUnit);
    }

    public void visit(BuildingUnitPart buildingUnitPart) {
        visit((AbstractBuildingUnit) buildingUnitPart);
    }

    public void visit(IndustrialBuilding industrialBuilding) {
        walker.visit((AbstractBuilding) industrialBuilding);
    }

    public void visit(BuildingUnitPropertyElement buildingUnitPropertyElement) {
        walker.visit((FeatureProperty<?>) buildingUnitPropertyElement.getValue());
    }

    public void visit(IndustrialBuildingPart industrialBuildingPart) {
        walker.visit((BuildingPart) industrialBuildingPart);
    }

    public void visit(IndustrialBuildingRoofSurface industrialBuildingRoofSurface) {
        walker.visit((RoofSurface) industrialBuildingRoofSurface);
    }

    public void visit(OtherConstruction otherConstruction) {
        walker.visit((AbstractSite) otherConstruction);

        if (otherConstruction.isSetBoundedBySurface()) {
            for (BoundarySurfaceProperty property : new ArrayList<BoundarySurfaceProperty>(otherConstruction.getBoundedBySurface()))
                walker.visit((FeatureProperty<?>) property);
        }
    }
}
