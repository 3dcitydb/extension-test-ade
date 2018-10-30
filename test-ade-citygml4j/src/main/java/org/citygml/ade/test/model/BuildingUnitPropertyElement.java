package org.citygml.ade.test.model;

import org.citygml4j.builder.copy.CopyBuilder;
import org.citygml4j.model.citygml.ade.binding.ADEGenericApplicationProperty;

public class BuildingUnitPropertyElement extends ADEGenericApplicationProperty<BuildingUnitProperty> {

	public BuildingUnitPropertyElement() {
	}
	
	public BuildingUnitPropertyElement(BuildingUnitProperty value) {
		super(value);
	}
	
	@Override
	public Object copy(CopyBuilder copyBuilder) {
		return copyTo(new BuildingUnitPropertyElement(), copyBuilder);
	}
	
}
