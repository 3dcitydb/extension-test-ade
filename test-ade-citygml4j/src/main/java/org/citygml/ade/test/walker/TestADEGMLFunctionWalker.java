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
import org.citygml4j.util.walker.GMLFunctionWalker;

import java.util.ArrayList;

public class TestADEGMLFunctionWalker<T> implements ADEWalker<GMLFunctionWalker<T>> {
    private GMLFunctionWalker<T> walker;

    @Override
    public void setParentWalker(GMLFunctionWalker<T> walker) {
        this.walker = walker;
    }

    public T apply(AbstractFacilities abstractFacilities) {
        return walker.apply((AbstractFeature) abstractFacilities);
    }

    public T apply(DHWFacilities dhwFacilities) {
        return apply((AbstractFacilities) dhwFacilities);
    }

    public T apply(LightingFacilities lightingFacilities) {
        return apply((AbstractFacilities) lightingFacilities);
    }

    public T apply(AbstractBuildingUnit abstractBuildingUnit) {
        T object = walker.apply((AbstractCityObject) abstractBuildingUnit);
        if (object != null)
            return object;

        if (abstractBuildingUnit.isSetEquippedWith()) {
            for (FacilitiesProperty property : abstractBuildingUnit.getEquippedWith()) {
                object = walker.apply((FeatureProperty<?>) property);
                if (object != null)
                    return object;
            }
        }

        if (abstractBuildingUnit.isSetConsistsOf()) {
            for (BuildingUnitPartProperty property : abstractBuildingUnit.getConsistsOf()) {
                object = walker.apply((FeatureProperty<?>) property);
                if (object != null)
                    return object;
            }
        }

        if (abstractBuildingUnit.isSetAddress()) {
            for (AddressProperty property : new ArrayList<AddressProperty>(abstractBuildingUnit.getAddress())) {
                object = walker.apply((FeatureProperty<?>) property);
                if (object != null)
                    return object;
            }
        }

        if (abstractBuildingUnit.isSetLod1Solid()) {
            object = walker.apply(abstractBuildingUnit.getLod1Solid());
            if (object != null)
                return object;
        }

        if (abstractBuildingUnit.isSetLod2Solid()) {
            object = walker.apply(abstractBuildingUnit.getLod2Solid());
            if (object != null)
                return object;
        }

        if (abstractBuildingUnit.isSetLod3Solid()) {
            object = walker.apply(abstractBuildingUnit.getLod3Solid());
            if (object != null)
                return object;
        }

        if (abstractBuildingUnit.isSetLod4Solid()) {
            object = walker.apply(abstractBuildingUnit.getLod4Solid());
            if (object != null)
                return object;
        }

        if (abstractBuildingUnit.isSetLod2MultiCurve()) {
            object = walker.apply(abstractBuildingUnit.getLod2MultiCurve());
            if (object != null)
                return object;
        }

        if (abstractBuildingUnit.isSetLod3MultiCurve()) {
            object = walker.apply(abstractBuildingUnit.getLod3MultiCurve());
            if (object != null)
                return object;
        }

        if (abstractBuildingUnit.isSetLod4MultiCurve()) {
            object = walker.apply(abstractBuildingUnit.getLod4MultiCurve());
            if (object != null)
                return object;
        }

        if (abstractBuildingUnit.isSetLod1MultiSurface()) {
            object = walker.apply(abstractBuildingUnit.getLod1MultiSurface());
            if (object != null)
                return object;
        }

        if (abstractBuildingUnit.isSetLod2MultiSurface()) {
            object = walker.apply(abstractBuildingUnit.getLod2MultiSurface());
            if (object != null)
                return object;
        }

        if (abstractBuildingUnit.isSetLod3MultiSurface()) {
            object = walker.apply(abstractBuildingUnit.getLod3MultiSurface());
            if (object != null)
                return object;
        }

        if (abstractBuildingUnit.isSetLod4MultiSurface()) {
            object = walker.apply(abstractBuildingUnit.getLod4MultiSurface());
            if (object != null)
                return object;
        }

        return null;
    }

    public T apply(BuildingUnit buildingUnit) {
        return apply((AbstractBuildingUnit) buildingUnit);
    }

    public T apply(BuildingUnitPart buildingUnitPart) {
        return apply((AbstractBuildingUnit) buildingUnitPart);
    }

    public T apply(IndustrialBuilding industrialBuilding) {
        return walker.apply((AbstractBuilding) industrialBuilding);
    }

    public T apply(BuildingUnitPropertyElement buildingUnitPropertyElement) {
        return walker.apply((FeatureProperty<?>) buildingUnitPropertyElement.getValue());
    }

    public T apply(IndustrialBuildingPart industrialBuildingPart) {
        return walker.apply((BuildingPart) industrialBuildingPart);
    }

    public T apply(IndustrialBuildingRoofSurface industrialBuildingRoofSurface) {
        return walker.apply((RoofSurface) industrialBuildingRoofSurface);
    }

    public T apply(OtherConstruction otherConstruction) {
        T object = walker.apply((AbstractSite) otherConstruction);
        if (object != null)
            return object;

        if (otherConstruction.isSetBoundedBySurface()) {
            for (BoundarySurfaceProperty property : new ArrayList<BoundarySurfaceProperty>(otherConstruction.getBoundedBySurface())) {
                object = walker.apply((FeatureProperty<?>) property);
                if (object != null)
                    return object;
            }
        }

        return null;
    }
}
