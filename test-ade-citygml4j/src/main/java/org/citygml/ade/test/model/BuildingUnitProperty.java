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
