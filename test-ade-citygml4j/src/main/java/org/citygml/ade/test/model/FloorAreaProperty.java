package org.citygml.ade.test.model;

import org.citygml4j.builder.copy.CopyBuilder;
import org.citygml4j.model.citygml.ade.binding.ADEGenericApplicationProperty;
import org.citygml4j.model.gml.measures.Area;

public class FloorAreaProperty extends ADEGenericApplicationProperty<Area> {

	public FloorAreaProperty() {
	}
	
	public FloorAreaProperty(Area value) {
		super(value);
	}
	
	@Override
	public Object copy(CopyBuilder copyBuilder) {
		return copyTo(new FloorAreaProperty(), copyBuilder);
	}
	
}
