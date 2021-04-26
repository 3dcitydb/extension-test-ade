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

package org.citygml.ade.test.model;

import org.citygml4j.builder.copy.CopyBuilder;
import org.citygml4j.model.citygml.ade.binding.ADEModelObject;
import org.citygml4j.model.gml.feature.FeatureProperty;

public class BuildingUnitProperty extends FeatureProperty<AbstractBuildingUnit> implements ADEModelObject {

	public BuildingUnitProperty() {
	}
	
	public BuildingUnitProperty(AbstractBuildingUnit buildingUnit) {
		super(buildingUnit);
	}
	
	public BuildingUnitProperty(String href) {
		super(href);
	}
	
	public AbstractBuildingUnit getBuildingUnit() {
		return super.getObject();
	}

	public boolean isSetBuildingUnit() {
		return super.isSetObject();
	}

	public void setBuildingUnit(AbstractBuildingUnit buildingUnit) {
		super.setObject(buildingUnit);
	}

	public void unsetBuildingUnit() {
		super.unsetObject();
	}

	@Override
	public Class<AbstractBuildingUnit> getAssociableClass() {
		return AbstractBuildingUnit.class;
	}

	@Override
	public Object copy(CopyBuilder copyBuilder) {
		return copyTo(new BuildingUnitProperty(), copyBuilder);
	}

	@Override
	public Object copyTo(Object target, CopyBuilder copyBuilder) {
		BuildingUnitProperty copy = (target == null) ? new BuildingUnitProperty() : (BuildingUnitProperty)target;
		return super.copyTo(copy, copyBuilder);
	}

}
