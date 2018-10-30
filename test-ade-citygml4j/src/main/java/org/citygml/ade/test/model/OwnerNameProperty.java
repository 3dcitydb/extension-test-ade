package org.citygml.ade.test.model;

import org.citygml4j.builder.copy.CopyBuilder;
import org.citygml4j.model.citygml.ade.binding.ADEGenericApplicationProperty;

public class OwnerNameProperty extends ADEGenericApplicationProperty<String> {

	public OwnerNameProperty() {
	}
	
	public OwnerNameProperty(String value) {
		super(value);
	}
	
	@Override
	public Object copy(CopyBuilder copyBuilder) {
		return copyTo(new OwnerNameProperty(), copyBuilder);
	}
	
}
