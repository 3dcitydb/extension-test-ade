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

package org.citygml.ade.test.model;

import org.citygml4j.builder.copy.CopyBuilder;
import org.citygml4j.model.citygml.ade.binding.ADEModelObject;
import org.citygml4j.model.gml.feature.FeatureProperty;

public class BuildingUnitPartProperty extends FeatureProperty<BuildingUnitPart> implements ADEModelObject {

    public BuildingUnitPartProperty() {
    }

    public BuildingUnitPartProperty(BuildingUnitPart buildingUnitPart) {
        super(buildingUnitPart);
    }

    public BuildingUnitPartProperty(String href) {
        super(href);
    }

    public BuildingUnitPart getBuildingUnitPart() {
        return super.getObject();
    }

    public boolean isSetBuildingUnitPart() {
        return super.isSetObject();
    }

    public void setBuildingUnitPart(BuildingUnitPart buildingUnitPart) {
        super.setObject(buildingUnitPart);
    }

    public void unsetBuildingUnitPart() {
        super.unsetObject();
    }

    @Override
    public Class<BuildingUnitPart> getAssociableClass() {
        return BuildingUnitPart.class;
    }

    @Override
    public Object copy(CopyBuilder copyBuilder) {
        return copyTo(new BuildingUnitPartProperty(), copyBuilder);
    }

    @Override
    public Object copyTo(Object target, CopyBuilder copyBuilder) {
        BuildingUnitPartProperty copy = (target == null) ? new BuildingUnitPartProperty() : (BuildingUnitPartProperty) target;
        return super.copyTo(copy, copyBuilder);
    }

}
